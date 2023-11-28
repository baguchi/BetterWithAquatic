package baguchan.better_with_aquatic.entity;

import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.util.helper.MathHelper;
import useless.dragonfly.model.entity.BenchEntityModel;

public class DrownedModel extends BenchEntityModel {

	private static EntityDrowned drowned;

	@Override
	public void setLivingAnimations(EntityLiving entityliving, float limbSwing, float limbYaw, float renderPartialTicks) {
		super.setLivingAnimations(entityliving, limbSwing, limbYaw, renderPartialTicks);
		if (entityliving instanceof EntityDrowned) {
			drowned = (EntityDrowned) entityliving;
		}
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbYaw, float ticksExisted, float headYaw, float headPitch, float scale) {
		this.getIndexBones().forEach((s, benchEntityBones) -> {
			benchEntityBones.resetPose();
		});
		super.setRotationAngles(limbSwing, limbYaw, ticksExisted, headYaw, headPitch, scale);
		if (this.getIndexBones().containsKey("head")) {
			this.getIndexBones().get("head").setRotationAngle((float) Math.toRadians(headPitch), (float) Math.toRadians(headYaw), 0.0F);
		}

		if (this.getIndexBones().containsKey("rightArm")) {
			this.getIndexBones().get("rightArm").setRotationAngle(-1.5707964f, 0.0F, 0.0F);
		}

		if (this.getIndexBones().containsKey("leftArm")) {
			this.getIndexBones().get("leftArm").setRotationAngle(-1.5707964f, 0.0F, 0.0F);
		}

		if (this.getIndexBones().containsKey("rightLeg")) {
			this.getIndexBones().get("rightLeg").rotateAngleX = MathHelper.cos(limbSwing * 0.6666667F) * 1.4F * limbYaw;
		}

		if (this.getIndexBones().containsKey("leftLeg")) {
			this.getIndexBones().get("leftLeg").rotateAngleX = MathHelper.cos(limbSwing * 0.6666667F + 3.141593f) * 1.4F * limbYaw;
		}

		if (drowned != null) {

			float swimAmount = drowned.getSwimAmount(ticksExisted - drowned.tickCount);
			if (swimAmount > 0) {
				this.getIndexBones().get("rightArm").rotateAngleX = this.rotlerpRad(swimAmount, this.getIndexBones().get("rightArm").rotateAngleX, -2.5132742F) + swimAmount * 0.35F * MathHelper.sin(0.1F * ticksExisted);
				this.getIndexBones().get("leftArm").rotateAngleX = this.rotlerpRad(swimAmount, this.getIndexBones().get("leftArm").rotateAngleX, -2.5132742F) - swimAmount * 0.35F * MathHelper.sin(0.1F * ticksExisted);
				this.getIndexBones().get("rightArm").rotateAngleZ = this.rotlerpRad(swimAmount, this.getIndexBones().get("rightArm").rotateAngleZ, -0.15F);
				this.getIndexBones().get("leftArm").rotateAngleZ = this.rotlerpRad(swimAmount, this.getIndexBones().get("leftArm").rotateAngleZ, 0.15F);
				this.getIndexBones().get("leftLeg").rotateAngleX -= swimAmount * 0.55F * MathHelper.sin(0.1F * ticksExisted);
				this.getIndexBones().get("rightLeg").rotateAngleX += swimAmount * 0.55F * MathHelper.sin(0.1F * ticksExisted);
			}
		}
	}

	protected float rotlerpRad(float p_102836_, float p_102837_, float p_102838_) {
		float f = (p_102838_ - p_102837_) % (float) (Math.PI * 2);
		if (f < (float) -Math.PI) {
			f += (float) (Math.PI * 2);
		}

		if (f >= (float) Math.PI) {
			f -= (float) (Math.PI * 2);
		}

		return p_102837_ + p_102836_ * f;
	}
}
