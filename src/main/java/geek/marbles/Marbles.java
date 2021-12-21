package geek.marbles;

import geek.marbles.client.MarbleClient;
import geek.marbles.entity.MarbleEntities;
import geek.marbles.items.MarbleItems;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Marbles.ID)
public final class Marbles
{
    public static final String ID = "lgmarbles";

    public Marbles() {
        MarbleItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        MarbleEntities.ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());

        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> MarbleClient::subscribeClientEvents);
    }
}
