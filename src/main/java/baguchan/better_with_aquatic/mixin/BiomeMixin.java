package baguchan.better_with_aquatic.mixin;

import baguchan.better_with_aquatic.entity.EntityAnglerFish;
import baguchan.better_with_aquatic.entity.EntityFish;
import net.minecraft.core.entity.SpawnListEntry;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.biome.Biomes;
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
		Biome biome = (Biome) (Object) this;
		if (biome != Biomes.NETHER_NETHER) {
			spawnableWaterCreatureList.add(new SpawnListEntry(EntityFish.class, 15));
			spawnableWaterCreatureList.add(new SpawnListEntry(EntityAnglerFish.class, 10));
		}
	}
}
