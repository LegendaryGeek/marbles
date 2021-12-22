package geek.marbles.entity;

import geek.marbles.items.MarbleItems;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class MarbleEntity extends ItemEntity
{
    public MarbleEntity(EntityType<? extends MarbleEntity> entityType, Level level)
    {
        super(entityType, level);
        this.blocksBuilding = true;
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
        return false; //TODO enable for interaction
    }

    @Override
    public boolean isPushable()
    {
        return true;
    }

    @Override
    public InteractionResult interactAt(Player player, Vec3 position, InteractionHand hand)
    {
        //Push
        if (!player.isSecondaryUseActive())
        {
            float power = (float) player.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
            this.push(
                    -Mth.sin(this.getYRot() * ((float) Math.PI / 180F)) * power * 0.5F,
                    0.1D,
                    Mth.cos(this.getYRot() * ((float) Math.PI / 180F)) * power * 0.5F);

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
