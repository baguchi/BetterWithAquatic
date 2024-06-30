package baguchan.better_with_aquatic.mixin.client;

import baguchan.better_with_aquatic.api.ISwimModel;
import baguchan.better_with_aquatic.util.MathUtil;
import net.minecraft.client.render.model.Cube;
import net.minecraft.client.render.model.ModelBiped;
import net.minecraft.client.render.model.ModelPlayer;
import net.minecraft.core.util.helper.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ModelPlayer.class, remap = false)
public class ModelPlayerMixin extends ModelBiped implements ISwimModel {
	@Shadow
	public Cube bipedLeftArmOverlay;
	@Shadow
	public Cube bipedRightArmOverlay;
	@Shadow
	public Cube bipedLeftLegOverlay;
	@Shadow
	public Cube bipedRightLegOverlay;
	@Shadow
	public Cube bipedBodyOverlay;

	private float swimAmount;

	@Inject(method = "setRotationAngles", at = @At("TAIL"))
	public void setRotationAngles(float limbSwing, float limbYaw, float ticksExisted, float headYaw, float headPitch, float scale, CallbackInfo ci) {
		if (swimAmount > 0.0F) {
			float f5 = limbSwing % 26.0F;
			float f1 = swimAmount;
			float f2 = swimAmount;

			if (f5 < 14.0F) {
				this.bipedLeftArm.rotateAngleX = this.rotlerpRad(f2, this.bipedLeftArm.rotateAngleX, 0.0F);
				this.bipedRightArm.rotateAngleX = MathUtil.lerp(f1, this.bipedRightArm.rotateAngleX, 0.0F);
				this.bipedLeftArm.rotateAngleY = this.rotlerpRad(f2, this.bipedLeftArm.rotateAngleY, (float) Math.PI);
				this.bipedRightArm.rotateAngleY = MathUtil.lerp(f1, this.bipedRightArm.rotateAngleY, (float) Math.PI);
				this.bipedLeftArm.rotateAngleZ = this.rotlerpRad(
					f2, this.bipedLeftArm.rotateAngleZ, (float) Math.PI + 1.8707964F * this.quadraticArmUpdate(f5) / this.quadraticArmUpdate(14.0F)
				);
				this.bipedRightArm.rotateAngleZ = MathUtil.lerp(
					f1, this.bipedRightArm.rotateAngleZ, (float) Math.PI - 1.8707964F * this.quadraticArmUpdate(f5) / this.quadraticArmUpdate(14.0F)
				);
			} else if (f5 >= 14.0F && f5 < 22.0F) {
				float f6 = (f5 - 14.0F) / 8.0F;
				this.bipedLeftArm.rotateAngleX = this.rotlerpRad(f2, this.bipedLeftArm.rotateAngleX, (float) (Math.PI / 2) * f6);
				this.bipedRightArm.rotateAngleX = MathUtil.lerp(f1, this.bipedRightArm.rotateAngleX, (float) (Math.PI / 2) * f6);
				this.bipedLeftArm.rotateAngleY = this.rotlerpRad(f2, this.bipedLeftArm.rotateAngleY, (float) Math.PI);
				this.bipedRightArm.rotateAngleY = MathUtil.lerp(f1, this.bipedRightArm.rotateAngleY, (float) Math.PI);
				this.bipedLeftArm.rotateAngleZ = this.rotlerpRad(f2, this.bipedLeftArm.rotateAngleZ, 5.012389F - 1.8707964F * f6);
				this.bipedRightArm.rotateAngleZ = MathUtil.lerp(f1, this.bipedRightArm.rotateAngleZ, 1.2707963F + 1.8707964F * f6);
			} else if (f5 >= 22.0F && f5 < 26.0F) {
				float f3 = (f5 - 22.0F) / 4.0F;
				this.bipedLeftArm.rotateAngleX = this.rotlerpRad(f2, this.bipedLeftArm.rotateAngleX, (float) (Math.PI / 2) - (float) (Math.PI / 2) * f3);
				this.bipedRightArm.rotateAngleX = MathUtil.lerp(f1, this.bipedRightArm.rotateAngleX, (float) (Math.PI / 2) - (float) (Math.PI / 2) * f3);
				this.bipedLeftArm.rotateAngleY = this.rotlerpRad(f2, this.bipedLeftArm.rotateAngleY, (float) Math.PI);
				this.bipedRightArm.rotateAngleY = MathUtil.lerp(f1, this.bipedRightArm.rotateAngleY, (float) Math.PI);
				this.bipedLeftArm.rotateAngleZ = this.rotlerpRad(f2, this.bipedLeftArm.rotateAngleZ, (float) Math.PI);
				this.bipedRightArm.rotateAngleZ = MathUtil.lerp(f1, this.bipedRightArm.rotateAngleZ, (float) Math.PI);
			}
			float f7 = 0.3F;
			float f4 = 0.33333334F;
			this.bipedLeftLeg.rotateAngleX = MathUtil.lerp(swimAmount, this.bipedLeftLeg.rotateAngleX, 0.3F * MathHelper.cos(limbSwing * 0.33333334F + (float) Math.PI));
			this.bipedRightLeg.rotateAngleX = MathUtil.lerp(swimAmount, this.bipedRightLeg.rotateAngleX, 0.3F * MathHelper.cos(limbSwing * 0.33333334F));
		}



		ModelPlayer.func_178685_a(this.bipedLeftLeg, this.bipedLeftLegOverlay);
		ModelPlayer.func_178685_a(this.bipedRightLeg, this.bipedRightLegOverlay);
		ModelPlayer.func_178685_a(this.bipedLeftArm, this.bipedLeftArmOverlay);
		ModelPlayer.func_178685_a(this.bipedRightArm, this.bipedRightArmOverlay);
		ModelPlayer.func_178685_a(this.bipedBody, this.bipedBodyOverlay);
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


	private float quadraticArmUpdate(float p_102834_) {
		return -65.0F * p_102834_ + p_102834_ * p_102834_;
	}


	@Override
	public void setSwimAmount(float swimAmount) {
		this.swimAmount = swimAmount;
	}
}
