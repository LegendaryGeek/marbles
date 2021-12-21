package geek.marbles.client;

import geek.marbles.client.render.MarbleModel;
import geek.marbles.client.render.MarbleRenderer;
import geek.marbles.entity.MarbleEntities;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/**
 * Created by Robin Seifert on 12/21/2021.
 */
public class MarbleClient
{
    public static void subscribeClientEvents()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(MarbleClient::registerEntityRenderers);
        modEventBus.addListener(MarbleClient::registerEntityModels);
    }

    public static void registerEntityModels(EntityRenderersEvent.RegisterLayerDefinitions event)
    {
        event.registerLayerDefinition(MarbleModel.LAYER_LOCATION, MarbleModel::createBodyLayer);
    }

    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers.RegisterRenderers event)
    {
        EntityRenderers.register(MarbleEntities.MARBLE.get(), MarbleRenderer::new);
    }
}
