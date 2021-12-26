package geek.marbles.client.gui;

import geek.marbles.items.MarbleItems;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.Nullable;

/**
 * Created by Robin Seifert on 12/25/2021.
 */
public class MarbleColorItemMenu extends AbstractContainerMenu
{
    protected final Player player;
    protected final int playerSlot;

    protected MarbleColorItemMenu(@Nullable MenuType<?> type, int id, Player player, int playerSlot)
    {
        super(type, id);
        this.player = player;
        this.playerSlot = playerSlot;
    }

    @Override
    public boolean stillValid(Player player)
    {
        return player.getInventory().getItem(playerSlot).getItem() == MarbleItems.MARBLE.get();
    }
}
