package baguchan.better_with_aquatic.mixin;

import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
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
}
