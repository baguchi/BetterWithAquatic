package baguchan.better_with_aquatic.mixin;

import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = EntityItem.class, remap = false)
public abstract class EntityItemMixin extends Entity {

	public EntityItemMixin(World world) {
		super(world);
	}

	@Inject(method = "checkAndHandleWater", at = @At("HEAD"), cancellable = true)
	public void checkAndHandleWater(boolean addVelocity, CallbackInfoReturnable<Boolean> cir) {
		Entity entity = (Entity) (Object) this;

		cir.setReturnValue(this.world.handleMaterialAcceleration(this.bb, Material.water, entity, addVelocity));
	}

	@Redirect(method = "tick", at = @At(value = "FIELD", target = "Lnet/minecraft/core/entity/EntityItem;yd:D", ordinal = 7))
	public double tick(EntityItem instance) {
		if (this.wasInWater) {
			this.yd += 0.045F;
			this.yd *= 0.95f;
		}
		return this.yd;
	}
}
