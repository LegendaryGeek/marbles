package geek.marbles.items;

import geek.marbles.Marbles;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Created by Robin Seifert on 12/21/2021.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MarbleItems
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Marbles.ID);

    public static final RegistryObject<Item> MARBLE = ITEMS.register("marble", () -> new MarbleItem(
            new Item.Properties().stacksTo(16).tab(CreativeModeTab.TAB_MISC)
    ));
}
