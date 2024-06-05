package baguchan.better_with_aquatic.mixin;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = Entity.class, remap = false)
public class EntityMixin {
	@Shadow
	public World world;
	@Shadow
	@Final
	public AABB bb;
}
