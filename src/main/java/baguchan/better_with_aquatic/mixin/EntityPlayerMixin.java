package baguchan.better_with_aquatic.mixin;

import baguchan.better_with_aquatic.api.ISwiming;
import baguchan.better_with_aquatic.util.MathUtil;
import com.mojang.nbt.CompoundTag;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EntityPlayer.class, remap = false)
public abstract class EntityPlayerMixin extends EntityLiving implements ISwiming {
	public boolean swimming;

	private float swimAmount;
	private float swimAmountO;

	public EntityPlayerMixin(World world) {
		super(world);
	}

	public void setSwimming(boolean p_20274_) {
		this.swimming = p_20274_;
		if (p_20274_) {
			this.heightOffset = 0.5f;
			this.setSize(0.6F, 0.6F);
		} else {
			this.heightOffset = 1.62f;
			this.ySlideOffset = 0.0f;
			this.setSize(0.6F, 1.8F);
		}

	}

	public boolean isSwimming() {
		return this.swimming;
	}

	private void updateSwimAmount() {
		this.swimAmountO = this.swimAmount;
		if (this.isSwimming()) {
			this.swimAmount = Math.min(1.0F, this.swimAmount + 0.09F);
		} else {
			this.swimAmount = Math.max(0.0F, this.swimAmount - 0.09F);
		}
	}

	public float getSwimAmount(float p_20999_) {
		return MathUtil.lerp(p_20999_, this.swimAmountO, this.swimAmount);
	}

	@Inject(method = "moveEntityWithHeading", at = @At("RETURN"))
	public void moveEntityWithHeading(float moveStrafing, float moveForward, CallbackInfo ci) {
		if (this.isSwimming() && this.isInWater() && !this.isPassenger()) {
			double d3 = this.getLookAngle().yCoord;
			double d4 = d3 < -0.2 ? 0.04 : 0.08;
			if (d3 <= 0.0
				|| this.isJumping
				|| this.world.getBlockMaterial((int) (this.x), (int) (this.y + 1.0 - 0.1), (int) (this.z)) == Material.water) {
				this.yd += (d3 - yd) * d4;
			}
			this.xd += (this.getLookAngle().xCoord - xd) * 0.025F;
			this.zd += (this.getLookAngle().zCoord - zd) * 0.025F;
		}
	}


	@Inject(method = "onLivingUpdate", at = @At("HEAD"))
	public void onLivingUpdate(CallbackInfo ci) {
		if (!(this.world.getBlockId((int) (this.x), (int) this.y + 1, (int) (this.z)) != 0 && this.world.getBlockMaterial((int) (this.x), (int) this.y + 1, (int) (this.z)) != Material.water) && this.isVisuallyCrawling()) {
			this.setSwimming(false);
		}

		if (this.isSwimming() && this.isInWater() && this.moveForward == 0 && this.moveStrafing == 0) {
			this.setSwimming(false);
		}
		this.updateSwimAmount();
	}

	public boolean isVisuallyCrawling() {
		return this.isSwimming() && !this.isInWater();
	}

	@Inject(method = "readAdditionalSaveData", at = @At("RETURN"))
	public void readAdditionalSaveData(CompoundTag tag, CallbackInfo ci) {
		this.setSwimming(tag.getBoolean("Swimming"));
	}

	@Inject(method = "addAdditionalSaveData", at = @At("RETURN"))
	public void addAdditionalSaveData(CompoundTag tag, CallbackInfo ci) {
		tag.putBoolean("Swimming", this.isSwimming());
	}
}
