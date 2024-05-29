package baguchan.better_with_aquatic.block;

import baguchan.better_with_aquatic.BetterWithAquatic;
import baguchan.better_with_aquatic.client.render.model.BlockModelSeaGrass;
import baguchan.better_with_aquatic.client.render.model.SeaGrassItemModel;
import baguchan.better_with_aquatic.util.IDUtils;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.sound.BlockSounds;
import turniplabs.halplibe.helper.BlockBuilder;

public class ModBlocks {


	public static final Block sea_grass = new BlockBuilder(BetterWithAquatic.MOD_ID)
		.setHardness(0.0f)
		.setResistance(100F)
		.setLightOpacity(1)
		.setTags(BlockTags.IS_WATER, BlockTags.PLACE_OVERWRITES, BlockTags.SHEARS_DO_SILK_TOUCH)
		.setBlockSound(BlockSounds.GRASS)
		.setBlockModel(block -> {
			return new BlockModelSeaGrass<>(block).withTextures("better_with_aquatic:block/sea_grass");
		})
		.setItemModel(itemBlock -> {
			return new SeaGrassItemModel(itemBlock);
		})
		.build(new BlockWaterPlantStill("sea_grass", IDUtils.getCurrBlockId(), Material.water).withLitInteriorSurface(true));
	public static final Block sea_grass_flow = new BlockBuilder(BetterWithAquatic.MOD_ID)
		.setHardness(0.0f)
		.setResistance(100F)
		.setLightOpacity(1)
		.setBlockModel(block -> {
			return new BlockModelSeaGrass<>(block).withTextures("better_with_aquatic:block/sea_grass");
		})
		.setItemModel(itemBlock -> {
			return new SeaGrassItemModel(itemBlock);
		})
		.setBlockDrop(sea_grass)
		.setTags(BlockTags.IS_WATER, BlockTags.PLACE_OVERWRITES, BlockTags.SHEARS_DO_SILK_TOUCH, BlockTags.NOT_IN_CREATIVE_MENU)
		.setBlockSound(BlockSounds.GRASS)
		.build(new BlockWaterPlantFlow("sea_grass_flow", IDUtils.getCurrBlockId(), Material.water));

	public static final Block coral_blue = new BlockBuilder(BetterWithAquatic.MOD_ID)
		.setHardness(0.6f)
		.setResistance(0.65F)
		.setTextures("better_with_aquatic:block/coral_blue")
		.setTags(BlockTags.MINEABLE_BY_SHEARS, BlockTags.SHEARS_DO_SILK_TOUCH)
		.setBlockSound(BlockSounds.GRASS)
		.build(new CoralBlock("coral_blue", IDUtils.getCurrBlockId(), Material.vegetable));
	public static final Block coral_cyan = new BlockBuilder(BetterWithAquatic.MOD_ID)
		.setHardness(0.6f)
		.setResistance(0.65F)
		.setTextures("better_with_aquatic:block/coral_cyan")
		.setTags(BlockTags.MINEABLE_BY_SHEARS, BlockTags.SHEARS_DO_SILK_TOUCH)
		.setBlockSound(BlockSounds.GRASS)
		.build(new CoralBlock("coral_cyan", IDUtils.getCurrBlockId(), Material.vegetable));
	public static final Block coral_green = new BlockBuilder(BetterWithAquatic.MOD_ID)
		.setHardness(0.6f)
		.setResistance(0.65F)
		.setTextures("better_with_aquatic:block/coral_green")
		.setTags(BlockTags.MINEABLE_BY_SHEARS, BlockTags.SHEARS_DO_SILK_TOUCH)
		.setBlockSound(BlockSounds.GRASS)
		.build(new CoralBlock("coral_green", IDUtils.getCurrBlockId(), Material.vegetable));
	public static final Block coral_pink = new BlockBuilder(BetterWithAquatic.MOD_ID)
		.setHardness(0.6f)
		.setResistance(0.65F)
		.setTextures("better_with_aquatic:block/coral_pink")
		.setTags(BlockTags.MINEABLE_BY_SHEARS, BlockTags.SHEARS_DO_SILK_TOUCH)
		.setBlockSound(BlockSounds.GRASS)
		.build(new CoralBlock("coral_pink", IDUtils.getCurrBlockId(), Material.vegetable));

	public static final Block coral_purple = new BlockBuilder(BetterWithAquatic.MOD_ID)
		.setHardness(0.6f)
		.setResistance(0.65F)
		.setTextures("better_with_aquatic:block/coral_purple")
		.setTags(BlockTags.MINEABLE_BY_SHEARS, BlockTags.SHEARS_DO_SILK_TOUCH)
		.setBlockSound(BlockSounds.GRASS)
		.build(new CoralBlock("coral_purple", IDUtils.getCurrBlockId(), Material.vegetable));
	public static final Block coral_red = new BlockBuilder(BetterWithAquatic.MOD_ID)
		.setHardness(0.6f)
		.setResistance(0.65F)
		.setTextures("better_with_aquatic:block/coral_red")
		.setTags(BlockTags.MINEABLE_BY_SHEARS, BlockTags.SHEARS_DO_SILK_TOUCH)
		.setBlockSound(BlockSounds.GRASS)
		.build(new CoralBlock("coral_red", IDUtils.getCurrBlockId(), Material.vegetable));
	public static final Block coral_yellow = new BlockBuilder(BetterWithAquatic.MOD_ID)
		.setHardness(0.6f)
		.setResistance(0.65F)
		.setTextures("better_with_aquatic:block/coral_yellow")
		.setTags(BlockTags.MINEABLE_BY_SHEARS, BlockTags.SHEARS_DO_SILK_TOUCH)
		.setBlockSound(BlockSounds.GRASS)
		.build(new CoralBlock("coral_yellow", IDUtils.getCurrBlockId(), Material.vegetable));
	public static final Block light_blub = new BlockBuilder(BetterWithAquatic.MOD_ID)
		.setHardness(0.5f)
		.setResistance(1.5F)
		.setLuminance(13)
		.setTextures("better_with_aquatic:block/light_bulb")
		.setTags(BlockTags.MINEABLE_BY_PICKAXE)
		.setBlockSound(BlockSounds.METAL)
		.build(new Block("light_bulb", IDUtils.getCurrBlockId(), Material.metal));



	public static void createBlocks() {

	}

	static {
	}
}
