package baguchan.better_with_aquatic.mixin;

import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = EntityItem.class, remap = false)
public abstract class EntityItemMixin extends Entity {

	public EntityItemMixin(World world) {
		super(world);
	}

	@Inject(method = "checkAndHandleWater", at = @At("HEAD"), cancellable = true)
	public void checkAndHandleWater(CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(this.world.isMaterialInBB(this.bb, Material.water));
	}

	@Inject(method = "tick", at = @At("TAIL"))
	public void tick(CallbackInfo ci) {
		if (this.wasInWater) {
			this.yd += 0.1F;
			this.yd *= (double) 0.85f;
		}
	}
}
