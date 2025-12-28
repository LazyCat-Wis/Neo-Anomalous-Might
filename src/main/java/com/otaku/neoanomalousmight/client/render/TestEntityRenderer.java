package com.otaku.neoanomalousmight.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.otaku.neoanomalousmight.entity.TestEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import com.otaku.neoanomalousmight.Neo_Anomalous_Might;
import net.minecraft.client.model.geom.ModelLayers;

// 使用默认的人类模型，没有自定义纹理
public class TestEntityRenderer extends MobRenderer<TestEntity, HumanoidModel<TestEntity>> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Neo_Anomalous_Might.MOD_ID, "textures/entity/test_entity.png");
    
    public TestEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER)), 0.5F);
    }
    
    @Override
    public void render(TestEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        // 可以在这里添加自定义渲染逻辑
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }
    
    @Override
    public ResourceLocation getTextureLocation(TestEntity entity) {
        return TEXTURE;
    }
}