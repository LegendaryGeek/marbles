package geek.marbles;

import geek.marbles.items.MarbleItem;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Created by Robin Seifert on 12/21/2021.
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = "lgmarbles")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RegistryEvents
{
    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event)
    {
        event.getRegistry().register(
                new MarbleItem(
                        new Item.Properties()
                                .stacksTo(16)
                                .tab(CreativeModeTab.TAB_MISC)
                ).setRegistryName("marble")
        );
    }
}
