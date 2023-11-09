package baguchan.better_with_aquatic.mixin;

import baguchan.better_with_aquatic.entity.EntityBaseFish;
import net.minecraft.core.entity.SpawnListEntry;
import net.minecraft.core.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(value = Biome.class, remap = false)
public class BiomeMixin {

	@Shadow
	protected List<SpawnListEntry> spawnableWaterCreatureList;

	@Inject(method = "<init>", remap = false, at = @At("TAIL"))
	private void addMobs(CallbackInfo ci) {
		spawnableWaterCreatureList.add(new SpawnListEntry(EntityBaseFish.class, 15));
	}
}
