package baguchan.better_with_aquatic.block;

import baguchan.better_with_aquatic.BetterWithAquatic;
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
		.setHardness(100.0f)
		.setLightOpacity(1)
		.setTextures("sea_grass.png")
		.setTags(BlockTags.IS_WATER, BlockTags.PLACE_OVERWRITES)
		.setBlockSound(BlockSounds.GRASS)
		.build(new BlockFluidWithSeagrass("sea_grass", findOpenIds(561), Material.water));


	public static void createBlocks() {

	}

	static {
		Item.itemsList[sea_grass.id] = new ItemBlock(sea_grass);
	}
}
