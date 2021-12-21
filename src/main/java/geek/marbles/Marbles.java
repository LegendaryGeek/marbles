package geek.marbles;

import geek.marbles.entity.MableEntities;
import geek.marbles.items.MarbleItems;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Marbles.ID)
public final class Marbles
{
    public static final String ID = "lgmarbles";

    public Marbles() {
        MarbleItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        MableEntities.ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
