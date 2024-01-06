package baguchan.better_with_aquatic.mixin;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockFluid;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.util.phys.Vec3d;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Entity.class, remap = false)
public class EntityMixin {
	@Shadow
	public World world;
	@Shadow
	@Final
	public AABB bb;

	@Inject(method = "checkAndHandleWater", at = @At("HEAD"), cancellable = true)
	public void checkAndHandleWater(CallbackInfoReturnable<Boolean> cir) {
		if (this.world.isMaterialInBB(this.bb, Material.water)) {
			cir.setReturnValue(true);
		}
	}

	@Inject(method = "baseTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/Entity;checkAndHandleWater()Z", shift = At.Shift.AFTER))
	public void baseTick(CallbackInfo ci) {
		Entity entity = ((Entity) (Object) this);
		moveWithMaterialAcceleration(this.bb, Material.water, entity);

	}

	private boolean moveWithMaterialAcceleration(AABB axisalignedbb, Material material, Entity entity) {
		int j1;
		int i = MathHelper.floor_double(axisalignedbb.minX);
		int j = MathHelper.floor_double(axisalignedbb.maxX + 1.0);
		int k = MathHelper.floor_double(axisalignedbb.minY);
		int l = MathHelper.floor_double(axisalignedbb.maxY + 1.0);
		int i1 = MathHelper.floor_double(axisalignedbb.minZ);
		if (!this.world.areBlocksLoaded(i, k, i1, j, l, j1 = MathHelper.floor_double(axisalignedbb.maxZ + 1.0))) {
			return false;
		}
		boolean flag = false;
		Vec3d vec3d = Vec3d.createVector(0.0, 0.0, 0.0);
		for (int k1 = i; k1 < j; ++k1) {
			for (int l1 = k; l1 < l; ++l1) {
				for (int i2 = i1; i2 < j1; ++i2) {
					double d1;
					Block block = Block.blocksList[this.world.getBlockId(k1, l1, i2)];
					if (block == null || block.blockMaterial != material || !((double) l >= (d1 = (double) ((float) (l1 + 1) - BlockFluid.getWaterVolume(this.world.getBlockMetadata(k1, l1, i2))))))
						continue;
					flag = true;
					block.handleEntityInside(this.world, k1, l1, i2, entity, vec3d);
				}
			}
		}
		if (vec3d.lengthVector() > 0.0) {
			vec3d = vec3d.normalize();
			double d = 0.008;
			entity.xd += vec3d.xCoord * d;
			entity.yd += vec3d.yCoord * d;
			entity.zd += vec3d.zCoord * d;
		}
		return flag;
	}
}
