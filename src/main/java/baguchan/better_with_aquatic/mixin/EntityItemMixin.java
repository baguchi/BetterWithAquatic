package baguchan.better_with_aquatic.mixin;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = EntityItem.class, remap = false)
public abstract class EntityItemMixin extends Entity {

	public EntityItemMixin(World world) {
		super(world);
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
