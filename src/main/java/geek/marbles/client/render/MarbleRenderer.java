package geek.marbles.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import geek.marbles.Marbles;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

/**
 * Created by Robin Seifert on 12/21/2021.
 */
public class MarbleRenderer<E extends Entity> extends EntityRenderer<E>
{
    private static final ResourceLocation SKIN = new ResourceLocation(Marbles.ID, "textures/entity/marble.png");
    protected final MarbleModel<E> model;

    public MarbleRenderer(EntityRendererProvider.Context context)
    {
        super(context);
        model = new MarbleModel<E>(context.bakeLayer(MarbleModel.LAYER_LOCATION));
    }

    @Override
    public void render(E pEntity, float pEntityYaw, float pPartialTicks, PoseStack stack, MultiBufferSource pBuffer, int pPackedLight) {
        stack.pushPose();

        //Scale down
        stack.scale(0.5f, 0.5f, 0.5f);

        //Move model to slightly in the ground by a pixel
        stack.translate(0, -1.4, 0);

        //render
        final VertexConsumer vertexconsumer = pBuffer.getBuffer(this.model.renderType(this.getTextureLocation(pEntity)));
        this.model.renderToBuffer(stack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        stack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(E entity)
    {
        return SKIN;
    }
}
