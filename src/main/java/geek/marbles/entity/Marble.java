package geek.marbles.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class Marble extends ThrowableItemProjectile {

  public Marble(
    EntityType<? extends ThrowableItemProjectile> p_37432_,
    double p_37433_,
    double p_37434_,
    double p_37435_,
    Level p_37436_
  ) {
    super(p_37432_, p_37433_, p_37434_, p_37435_, p_37436_);
    //TODO Auto-generated constructor stub
  }

  @Override
  protected Item getDefaultItem() {
    // TODO Auto-generated method stub
    return null;
  }
}
