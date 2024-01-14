package baguchan.better_with_aquatic.entity.render;

import baguchan.better_with_aquatic.entity.EntityFrog;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.util.helper.MathHelper;
import useless.dragonfly.helper.AnimationHelper;
import useless.dragonfly.model.entity.BenchEntityModel;
import useless.dragonfly.model.entity.animation.Animation;

import static baguchan.better_with_aquatic.BetterWithAquatic.MOD_ID;

public class FrogModel extends BenchEntityModel {

	private static EntityFrog frog;

	@Override
	public void setLivingAnimations(EntityLiving entityliving, float limbSwing, float limbYaw, float renderPartialTicks) {
		super.setLivingAnimations(entityliving, limbSwing, limbYaw, renderPartialTicks);
		if (entityliving instanceof EntityFrog) {
			frog = (EntityFrog) entityliving;
		}
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbYaw, float ticksExisted, float headYaw, float headPitch, float scale) {
		this.getIndexBones().forEach((s, benchEntityBones) -> {
			benchEntityBones.resetPose();
		});
		super.setRotationAngles(limbSwing, limbYaw, ticksExisted, headYaw, headPitch, scale);
		Animation testAnimation = AnimationHelper.getOrCreateEntityAnimation(MOD_ID, "frog.animation");
		if (frog != null) {
			animate(frog.attackState, testAnimation.getAnimations().get("animation.frog.tongue"), ticksExisted, 1F);
			animate(frog.jumpState, testAnimation.getAnimations().get("animation.frog.jump"), ticksExisted, 1F);
			if (frog.isSwimming()) {
				animateWalk(testAnimation.getAnimations().get("animation.frog.swim"), limbSwing, limbYaw * 0.5F, 2.0F, 2.5F);
				animateWalk(testAnimation.getAnimations().get("animation.frog.idle.water"), ticksExisted, MathHelper.clamp(0.5F - limbYaw * 0.5F, 0, 0.5F), 2.0F, 2.5F);
			}
		}
	}

}
