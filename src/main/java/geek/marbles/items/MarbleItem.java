package geek.marbles.items;

import geek.marbles.entity.MableEntities;
import geek.marbles.entity.MarbleEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class MarbleItem extends Item {
  public MarbleItem(Item.Properties properties) {
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
    final MarbleEntity entity = MableEntities.MARBLE.get().create(world);
    if(entity != null)
    {
      entity.setItem(itemstack);
      entity.setPos(location.position());
      entity.setYRot(location.getYRot());
      entity.setXRot(location.getXRot());
      return entity;
    }
    return null;
  }
}
