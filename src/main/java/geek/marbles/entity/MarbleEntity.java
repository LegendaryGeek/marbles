package geek.marbles.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class MarbleEntity extends ItemEntity
{
    public MarbleEntity(EntityType<? extends MarbleEntity> p_31991_, Level p_31992_)
    {
        super(p_31991_, p_31992_);
        this.setPickUpDelay(20);
    }

    @Override
    public void playerTouch(Player p_32040_)
    {
        super.playerTouch(p_32040_); //TODO require marble bag to pick up or at least shifting
    }

    //TODO prevent merging
}
