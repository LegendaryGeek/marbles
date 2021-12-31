package geek.marbles.client.gui;

import geek.marbles.Marbles;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Created by Robin Seifert on 12/25/2021.
 */
@NoArgsConstructor(access = AccessLevel.NONE)
public final class MarbleMenus
{
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.CONTAINERS, Marbles.ID);

    public static final RegistryObject<MenuType<MarbleColorItemMenu>> MARBLE = MENUS.register("marble",
            () -> IForgeMenuType.create(MarbleMenus::createMenu));

    private static MarbleColorItemMenu createMenu(int windowId, Inventory inv, FriendlyByteBuf data) {
        final int slotId = data.readInt();
        return new MarbleColorItemMenu(MarbleMenus.MARBLE.get(), windowId, inv.player, slotId);
    }
}
