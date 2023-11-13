package baguchan.better_with_aquatic.block;

import baguchan.better_with_aquatic.BetterWithAquatic;
import baguchan.better_with_aquatic.util.IDUtils;
import net.minecraft.client.sound.block.BlockSounds;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.block.ItemBlock;
import turniplabs.halplibe.helper.BlockBuilder;

import static turniplabs.halplibe.helper.BlockHelper.findOpenIds;

public class ModBlocks {


	public static final Block sea_grass = new BlockBuilder(BetterWithAquatic.MOD_ID)
		.setHardness(0.0f)
		.setResistance(100F)
		.setLightOpacity(1)
		.setTextures("sea_grass.png")
		.setTags(BlockTags.IS_WATER, BlockTags.PLACE_OVERWRITES, BlockTags.SHEARS_DO_SILK_TOUCH)
		.setBlockSound(BlockSounds.GRASS)
		.build(new BlockWaterPlant("sea_grass", findOpenIds(IDUtils.getCurrBlockId()), Material.water));
	public static final Block coral_blue = new BlockBuilder(BetterWithAquatic.MOD_ID)
		.setHardness(0.6f)
		.setResistance(0.65F)
		.setTextures("coral_blue.png")
		.setTags(BlockTags.MINEABLE_BY_SHEARS, BlockTags.SHEARS_DO_SILK_TOUCH)
		.setBlockSound(BlockSounds.GRASS)
		.build(new CoralBlock("coral_blue", findOpenIds(IDUtils.getCurrBlockId()), Material.vegetable));
	public static final Block coral_cyan = new BlockBuilder(BetterWithAquatic.MOD_ID)
		.setHardness(0.6f)
		.setResistance(0.65F)
		.setTextures("coral_cyan.png")
		.setTags(BlockTags.MINEABLE_BY_SHEARS, BlockTags.SHEARS_DO_SILK_TOUCH)
		.setBlockSound(BlockSounds.GRASS)
		.build(new CoralBlock("coral_cyan", findOpenIds(IDUtils.getCurrBlockId()), Material.vegetable));
	public static final Block coral_green = new BlockBuilder(BetterWithAquatic.MOD_ID)
		.setHardness(0.6f)
		.setResistance(0.65F)
		.setTextures("coral_green.png")
		.setTags(BlockTags.MINEABLE_BY_SHEARS, BlockTags.SHEARS_DO_SILK_TOUCH)
		.setBlockSound(BlockSounds.GRASS)
		.build(new CoralBlock("coral_green", findOpenIds(IDUtils.getCurrBlockId()), Material.vegetable));
	public static final Block coral_pink = new BlockBuilder(BetterWithAquatic.MOD_ID)
		.setHardness(0.6f)
		.setResistance(0.65F)
		.setTextures("coral_pink.png")
		.setTags(BlockTags.MINEABLE_BY_SHEARS, BlockTags.SHEARS_DO_SILK_TOUCH)
		.setBlockSound(BlockSounds.GRASS)
		.build(new CoralBlock("coral_pink", findOpenIds(IDUtils.getCurrBlockId()), Material.vegetable));

	public static final Block coral_purple = new BlockBuilder(BetterWithAquatic.MOD_ID)
		.setHardness(0.6f)
		.setResistance(0.65F)
		.setTextures("coral_purple.png")
		.setTags(BlockTags.MINEABLE_BY_SHEARS, BlockTags.SHEARS_DO_SILK_TOUCH)
		.setBlockSound(BlockSounds.GRASS)
		.build(new CoralBlock("coral_purple", findOpenIds(IDUtils.getCurrBlockId()), Material.vegetable));
	public static final Block coral_red = new BlockBuilder(BetterWithAquatic.MOD_ID)
		.setHardness(0.6f)
		.setResistance(0.65F)
		.setTextures("coral_red.png")
		.setTags(BlockTags.MINEABLE_BY_SHEARS, BlockTags.SHEARS_DO_SILK_TOUCH)
		.setBlockSound(BlockSounds.GRASS)
		.build(new CoralBlock("coral_red", findOpenIds(IDUtils.getCurrBlockId()), Material.vegetable));
	public static final Block coral_yellow = new BlockBuilder(BetterWithAquatic.MOD_ID)
		.setHardness(0.6f)
		.setResistance(0.65F)
		.setTextures("coral_yellow.png")
		.setTags(BlockTags.MINEABLE_BY_SHEARS, BlockTags.SHEARS_DO_SILK_TOUCH)
		.setBlockSound(BlockSounds.GRASS)
		.build(new CoralBlock("coral_yellow", findOpenIds(IDUtils.getCurrBlockId()), Material.vegetable));



	public static void createBlocks() {

	}

	static {
		Item.itemsList[sea_grass.id] = new ItemBlock(sea_grass);
		Item.itemsList[coral_blue.id] = new ItemBlock(coral_blue);
		Item.itemsList[coral_cyan.id] = new ItemBlock(coral_cyan);
		Item.itemsList[coral_green.id] = new ItemBlock(coral_green);
		Item.itemsList[coral_pink.id] = new ItemBlock(coral_pink);
		Item.itemsList[coral_purple.id] = new ItemBlock(coral_purple);
		Item.itemsList[coral_red.id] = new ItemBlock(coral_red);
		Item.itemsList[coral_yellow.id] = new ItemBlock(coral_yellow);
	}
}
