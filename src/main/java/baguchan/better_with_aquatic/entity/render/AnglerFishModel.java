package baguchan.better_with_aquatic.entity.render;// Made with Blockbench 4.8.3
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports


import baguchan.better_with_aquatic.entity.EntityAnglerFish;
import net.minecraft.core.entity.EntityLiving;
import org.useless.dragonfly.helper.AnimationHelper;
import org.useless.dragonfly.model.entity.BenchEntityModel;
import org.useless.dragonfly.model.entity.animation.Animation;

import static baguchan.better_with_aquatic.BetterWithAquatic.MOD_ID;

public class AnglerFishModel extends BenchEntityModel {

	private static EntityAnglerFish anglerFish;

	@Override
	public void setLivingAnimations(EntityLiving entityliving, float limbSwing, float limbYaw, float renderPartialTicks) {
		super.setLivingAnimations(entityliving, limbSwing, limbYaw, renderPartialTicks);
		if (entityliving instanceof EntityAnglerFish) {
			anglerFish = (EntityAnglerFish) entityliving;
		}
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbYaw, float ticksExisted, float headYaw, float headPitch, float scale) {
		this.getIndexBones().forEach((s, benchEntityBones) -> {
			benchEntityBones.resetPose();
		});
		super.setRotationAngles(limbSwing, limbYaw, ticksExisted, headYaw, headPitch, scale);
		Animation testAnimation = AnimationHelper.getOrCreateEntityAnimation(MOD_ID, "angler_fish.animation");
		if (anglerFish != null) {
			animate(anglerFish.attackState, testAnimation.getAnimations().get("attack"), ticksExisted, 1F);
			animateWalk(testAnimation.getAnimations().get("swim"), limbSwing, limbYaw, 2.0F, 2.5F);
		}
	}

}
