package baguchan.better_with_aquatic.mixin;

import baguchan.better_with_aquatic.BetterWithAquatic;
import baguchan.better_with_aquatic.entity.EntityAnglerFish;
import baguchan.better_with_aquatic.entity.EntityDrowned;
import baguchan.better_with_aquatic.entity.EntityFish;
import baguchan.better_with_aquatic.entity.EntityFrog;
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
	@Shadow
	protected List<SpawnListEntry> spawnableCreatureList;

	@Shadow
	protected List<SpawnListEntry> spawnableMonsterList;

	@Inject(method = "<init>", remap = false, at = @At("TAIL"))
	private void addMobs(CallbackInfo ci) {
		Biome biome = (Biome) (Object) this;
		if (biome != Biomes.NETHER_NETHER) {
			if (BetterWithAquatic.isEnableDrowned()) {
				if (biome == Biomes.OVERWORLD_SWAMPLAND_MUDDY || biome == Biomes.OVERWORLD_SWAMPLAND || biome == Biomes.OVERWORLD_RAINFOREST) {
					spawnableMonsterList.add(new SpawnListEntry(EntityDrowned.class, 8));
				}
			}
			if (biome == Biomes.OVERWORLD_SWAMPLAND_MUDDY || biome == Biomes.OVERWORLD_SWAMPLAND || biome == Biomes.OVERWORLD_RAINFOREST) {
				spawnableCreatureList.add(new SpawnListEntry(EntityFrog.class, 50));
			}
			spawnableWaterCreatureList.add(new SpawnListEntry(EntityFish.class, 15));
			spawnableWaterCreatureList.add(new SpawnListEntry(EntityAnglerFish.class, 10));
		}
	}
}
