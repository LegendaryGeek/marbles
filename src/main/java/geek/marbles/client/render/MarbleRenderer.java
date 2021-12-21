package geek.marbles.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import geek.marbles.Marbles;
import geek.marbles.entity.MarbleEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

/**
 * Created by Robin Seifert on 12/21/2021.
 */
public class MarbleRenderer extends EntityRenderer<MarbleEntity>
{
    private static final ResourceLocation SKIN = new ResourceLocation(Marbles.ID, "textures/entity/marble.png");
    protected final MarbleModel<MarbleEntity> model;

    public MarbleRenderer(EntityRendererProvider.Context context)
    {
        super(context);
        model = new MarbleModel<>(context.bakeLayer(MarbleModel.LAYER_LOCATION));
    }

    @Override
    public void render(MarbleEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack stack, MultiBufferSource pBuffer, int pPackedLight) {
        stack.pushPose();
        VertexConsumer vertexconsumer = pBuffer.getBuffer(this.model.renderType(this.getTextureLocation(pEntity)));
        this.model.renderToBuffer(stack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        stack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(MarbleEntity entity)
    {
        return SKIN;
    }
}
