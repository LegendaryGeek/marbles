package geek.marbles.entity;

import geek.marbles.items.MarbleItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.function.Predicate;

public class MarbleEntity extends ItemEntity
{
    public MarbleEntity(EntityType<? extends MarbleEntity> entityType, Level level)
    {
        super(entityType, level);
        this.blocksBuilding = true;
        this.setUnlimitedLifetime(); //prevent despawning
    }

    @Override
    public void tick()
    {
        this.baseTick();

        final Vec3 vec3 = this.getDeltaMovement();

        //Gravity logic
        if (!this.isNoGravity())
        {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.04D, 0.0D));
        }

        //Normal movement
        if (!this.onGround || this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-5F || tickCount % 4 == 0)
        {

            //Trigger normal movement
            this.move(MoverType.SELF, this.getDeltaMovement());

            float frictionPercent = 0.99F; //Air resistance unless ground

            //Block interaction
            if (this.onGround)
            {
                final BlockPos blockPosUnder = new BlockPos(this.getX(), this.getY() - 1.0D, this.getZ());
                final BlockState blockState = this.level.getBlockState(blockPosUnder);
                final Block block = blockState.getBlock();

                //Get friction value
                frictionPercent = blockState.getFriction(level, blockPosUnder, this) * 0.98F; //Most blocks are 0.6F & ice is 0.98

                //Stair gravity logic
                if(block instanceof StairBlock && blockState.hasProperty(StairBlock.HALF) && blockState.hasProperty(StairBlock.FACING)) {
                    final Half half = blockState.getValue(StairBlock.HALF);
                    final Direction facing = blockState.getValue(StairBlock.FACING);

                    if(half == Half.BOTTOM) {
                        this.setDeltaMovement(this.getDeltaMovement().add(-facing.getStepX() * 0.05, -facing.getStepY() * 0.05, -facing.getStepZ() * 0.05));
                    }
                }
            }

            //Friction logic
            this.setDeltaMovement(this.getDeltaMovement().multiply(frictionPercent, 0.98D, frictionPercent));

            //Stuck in ground fix
            if (this.onGround)
            {
                final Vec3 vec31 = this.getDeltaMovement();
                if (vec31.y < 0.0D)
                {
                    this.setDeltaMovement(vec31.multiply(1.0D, -0.5D, 1.0D));
                }
            }
        }

        this.hasImpulse |= this.updateInWaterStateAndDoFluidPushing();
        if (!this.level.isClientSide)
        {
            double d0 = this.getDeltaMovement().subtract(vec3).lengthSqr();
            if (d0 > 0.01D)
            {
                this.hasImpulse = true;
            }
        }
    }

    @Override
    public void playerTouch(Player player)
    {
        //Empty to clear pickup of item via collision
    }

    @Override
    public boolean canBeCollidedWith()
    {
        return true;
    }


    @Override
    public boolean isPickable()
    {
        return true;
    }

    @Override
    public boolean isPushable()
    {
        return true;
    }

    public void push(Player player)
    {
        float power = (float) Math.max(1, player.getAttributeValue(Attributes.ATTACK_KNOCKBACK));
        this.push(
                -Mth.sin(player.getYRot() * ((float) Math.PI / 180F)) * power,
                0.1D,
                Mth.cos(player.getYRot() * ((float) Math.PI / 180F)) * power);
    }

    @Override
    public void move(MoverType type, Vec3 vec)
    {
        if (type == MoverType.SELF)
        {
            final boolean preHorizontalCollision = this.horizontalCollision;
            final boolean preVerticalCollision = this.verticalCollision;
            final boolean prevGround = this.onGround;
            Vec3 deltaMovement = this.getDeltaMovement();
            final Vec3 prevPosition = this.position();

            //Run normal logic, needed so we stop on walls
            super.move(type, vec);

            //Find all entities we are about to collide with
            final AABB aabb = this.getBoundingBox();
            final Predicate<Entity> predicate = EntitySelector.NO_SPECTATORS.and(this::canCollideWith);
            final List<Entity> list = this.level.getEntities(this, aabb.inflate(1.0E-7D), predicate);

            if (!list.isEmpty())
            {
                //Split out speed to other entities
                final double speed = Math.sqrt(vec.x * vec.x + vec.y * vec.y + vec.z * vec.z);

                //Apply energy over collisions
                final double transferPercent = (1.0 / (list.size() + 1.0)) * 0.95f;
                final double transferAmount = speed * transferPercent;
                list.forEach(entity -> {
                    final Vec3 deltaPos = entity.position().subtract(position());
                    entity.push(deltaPos.x * transferAmount, deltaPos.y * transferAmount, deltaPos.z * transferAmount);
                });

                //Invert self with some energy to create a bounce like effect TODO only do at high speed (low speed would stop)
                setDeltaMovement(deltaMovement.multiply(-transferPercent, 1, -transferPercent));
            }
            else
            {
                //Bounce off floor
                if (preVerticalCollision != this.verticalCollision && onGround != prevGround)
                {
                    final double bounceEnergyPercent = 0.5;
                    this.setDeltaMovement(deltaMovement.multiply(1, -bounceEnergyPercent, 1));
                }

                if (preHorizontalCollision != this.horizontalCollision)
                {
                    final Vec3 offsetFromCollision = this.position().subtract(prevPosition);
                    final boolean collidedX = !Mth.equal(vec.x, offsetFromCollision.x);
                    final boolean collidedZ = !Mth.equal(vec.z, offsetFromCollision.z);

                    final double bounceEnergyPercent = 0.9;

                    if (collidedZ)
                    {
                        this.setDeltaMovement(deltaMovement.multiply(1, 1, -bounceEnergyPercent));
                    }
                    else if (collidedX)
                    {
                        this.setDeltaMovement(deltaMovement.multiply(-bounceEnergyPercent, 1, 1));
                    }
                }
            }
        }
        else
        {
            super.move(type, vec);
        }
    }

    @Override
    public InteractionResult interactAt(Player player, Vec3 position, InteractionHand hand)
    {
        //Push
        if (!player.isSecondaryUseActive())
        {
            push(player);
            return InteractionResult.CONSUME;
        }
        //Pickup into inventory
        else if (player.getItemInHand(hand).isEmpty())
        {
            super.playerTouch(player);
            return InteractionResult.CONSUME;
        }
        //TODO pickup using a bag
        return InteractionResult.CONSUME;
    }

    @Override
    public boolean isMergable()
    {
        return false;
    }

    @Override
    public ItemEntity copy()
    {
        return (ItemEntity) MarbleItems.MARBLE.get().createEntity(getLevel(), this, getItem());
    }
}
