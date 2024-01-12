package baguchan.better_with_aquatic.mixin;

import baguchan.better_with_aquatic.api.ISwiming;
import baguchan.better_with_aquatic.util.MathUtil;
import com.mojang.nbt.CompoundTag;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockFluid;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EntityPlayer.class, remap = false)
public abstract class EntityPlayerMixin extends EntityLiving implements ISwiming {
	@Unique
	public boolean swimming;
	@Unique
	private float swimAmount;
	@Unique
	private float swimAmountO;


	public EntityPlayerMixin(World world) {
		super(world);
	}


	public void setSwimming(boolean p_20274_) {
		if (p_20274_ && !this.swimming) {
			this.heightOffset = 0.5f;
			this.setSize(0.6F, 0.6F);
			float center = this.bbWidth / 2.0f;
			float heightOfMob = this.bbHeight;
			this.bb.setBounds(x - (double) center, y - (double) this.heightOffset + (double) this.ySlideOffset, z - (double) center, x + (double) center, y - (double) this.heightOffset + (double) this.ySlideOffset + (double) heightOfMob, z + (double) center);

		} else if (!p_20274_ && this.swimming) {
			this.heightOffset = 1.62f;
			this.setSize(0.6F, 1.8F);
			this.setPos(this.x, this.y + 1.2F, this.z);
		}
		this.swimming = p_20274_;
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
			double d4 = d3 < -0.2 ? 0.04 : 0.1;
			if (d3 <= 0.0
				|| this.isJumping
				|| this.world.getBlockMaterial(MathHelper.floor_double(this.x), MathHelper.floor_double(this.y + 1.0 - 0.5), MathHelper.floor_double(this.z)) == Material.water) {
				this.yd += (d3 - yd) * d4;
			}
			this.xd += (this.getLookAngle().xCoord - xd) * 0.025F;
			this.zd += (this.getLookAngle().zCoord - zd) * 0.025F;
		}
	}


	@Inject(method = "onLivingUpdate", at = @At("HEAD"))
	public void onLivingUpdate(CallbackInfo ci) {
		Block block = this.world.getBlock((int) this.x, MathHelper.floor_double(this.y + 1), (int) this.z);

		if ((block instanceof BlockFluid || block == null) && this.isVisuallyCrawling()) {
			this.setSwimming(false);
		}

		if ((block instanceof BlockFluid || block == null) && this.isSwimming() && this.isInWater() && this.moveForward == 0 && this.moveStrafing == 0) {
			if (this.swimAmount >= 1F) {
				this.setSwimming(false);
			}
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
