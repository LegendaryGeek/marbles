package geek.marbles.items;

import geek.marbles.entity.MarbleEntities;
import geek.marbles.entity.MarbleEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class MarbleItem extends Item
{
    public MarbleItem(Item.Properties properties)
    {
        super(properties);
    }

    @Override
    public boolean hasCustomEntity(ItemStack stack)
    {
        return true;
    }

    @Override
    public Entity createEntity(Level world, Entity location, ItemStack itemstack)
    {
        final MarbleEntity entity = MarbleEntities.MARBLE.get().create(world);
        if (entity != null)
        {
            entity.setItem(itemstack);
            entity.setPos(location.position());
            entity.setYRot(location.getYRot());
            entity.setXRot(location.getXRot());
            entity.setDeltaMovement(location.getDeltaMovement());
            if(location instanceof ItemEntity itemEntity) {
                entity.setOwner(itemEntity.getOwner());
                entity.setThrower(itemEntity.getThrower());
            }
            return entity;
        }
        return null;
    }
}
