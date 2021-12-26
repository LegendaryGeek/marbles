package geek.marbles.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

/**
 * Created by Robin Seifert on 12/25/2021.
 */
public class MarbleColorItemScreen extends AbstractContainerScreen<MarbleColorItemMenu>
{
    public MarbleColorItemScreen(MarbleColorItemMenu p_97741_, Inventory p_97742_, Component p_97743_)
    {
        super(p_97741_, p_97742_, p_97743_);
    }

    @Override
    protected void renderBg(PoseStack p_97787_, float p_97788_, int p_97789_, int p_97790_)
    {

    }
}
