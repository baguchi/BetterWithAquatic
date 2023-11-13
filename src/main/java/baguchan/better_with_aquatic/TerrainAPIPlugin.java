package baguchan.better_with_aquatic;

import baguchan.better_with_aquatic.block.ModBlocks;
import baguchan.better_with_aquatic.world.WorldFeatureCoral;
import baguchan.better_with_aquatic.world.WorldFeatureSeaGrass;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.biome.Biomes;
import useless.terrainapi.api.TerrainAPI;
import useless.terrainapi.generation.overworld.api.ChunkDecoratorOverworldAPI;

public class TerrainAPIPlugin implements TerrainAPI {
	@Override
	public String getModID() {
		return BetterWithAquatic.MOD_ID;
	}

	@Override
	public void onInitialize() {
		ChunkDecoratorOverworldAPI.randomFeatures.addFeature(new WorldFeatureSeaGrass(ModBlocks.sea_grass.id), 1, -1, 6,
			new Biome[]{Biomes.OVERWORLD_SWAMPLAND, Biomes.OVERWORLD_SWAMPLAND_MUDDY, Biomes.OVERWORLD_BIRCH_FOREST, Biomes.OVERWORLD_RETRO, Biomes.OVERWORLD_MEADOW, Biomes.OVERWORLD_FOREST, Biomes.OVERWORLD_RAINFOREST, Biomes.OVERWORLD_BOREAL_FOREST, Biomes.OVERWORLD_SEASONAL_FOREST, Biomes.OVERWORLD_TAIGA, Biomes.OVERWORLD_SHRUBLAND});
		ChunkDecoratorOverworldAPI.randomFeatures.addFeature(new WorldFeatureCoral(), 5, -1, 4,
			new Biome[]{Biomes.OVERWORLD_BIRCH_FOREST, Biomes.OVERWORLD_RETRO, Biomes.OVERWORLD_MEADOW, Biomes.OVERWORLD_FOREST, Biomes.OVERWORLD_BOREAL_FOREST, Biomes.OVERWORLD_SEASONAL_FOREST, Biomes.OVERWORLD_SHRUBLAND, Biomes.OVERWORLD_RAINFOREST});
	}
}
