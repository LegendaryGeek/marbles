package geek.marbles.client.render;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import geek.marbles.entity.MarbleColor;
import geek.marbles.entity.MarbleEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class MarbleModel<T extends Entity> extends EntityModel<T>
{
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("lgmarble", "marble"), "main");
    private final ModelPart center_star;
    private final ModelPart body;

    public MarbleModel(ModelPart root)
    {
        super(RenderType::itemEntityTranslucentCull);
        this.center_star = root.getChild("center_star");
        this.body = root.getChild("body");
    }

    public static LayerDefinition createBodyLayer()
    {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition center_star = partdefinition.addOrReplaceChild("center_star", CubeListBuilder.create(), PartPose.offset(-0.215F, 24.2929F, -0.1543F));

        PartDefinition star_2_r1 = center_star.addOrReplaceChild("star_2_r1", CubeListBuilder.create().texOffs(8, 10).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.9599F, -0.9599F, 1.5708F));

        PartDefinition star_1_r1 = center_star.addOrReplaceChild("star_1_r1", CubeListBuilder.create().texOffs(0, 10).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.7854F));

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 16, 16);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {

    }

    public void renderToBuffer(T entity, PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay)
    {
        if (entity instanceof MarbleEntity marble)
        {
            final MarbleColor colorB = marble.getColorB();
            body.render(poseStack, buffer, packedLight, packedOverlay, colorB.red / 255f, colorB.green / 255f, colorB.blue / 255f, 1f);
        }
        else
        {
            body.render(poseStack, buffer, packedLight, packedOverlay);
        }

        if (entity instanceof MarbleEntity marble)
        {
            final MarbleColor colorA = marble.getColorA();
            center_star.render(poseStack, buffer, packedLight, packedOverlay, colorA.red / 255f, colorA.green / 255f, colorA.blue / 255f, 1f);
        }
        else
        {
            center_star.render(poseStack, buffer, packedLight, packedOverlay);
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
    {
        center_star.render(poseStack, buffer, packedLight, packedOverlay);
        body.render(poseStack, buffer, packedLight, packedOverlay);
    }
}