package baguchan.better_with_aquatic.world;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLeavesBase;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;

import java.util.Random;

public class WorldFeatureSeaGrass extends WorldFeature {
	private int blockId;

	public WorldFeatureSeaGrass(int i) {
		this.blockId = i;
	}

	@Override
	public boolean generate(World world, Random random, int x, int y, int z) {
		int l = 0;
		while (((l = world.getBlockId(x, y, z)) == 0 || Block.blocksList[l] instanceof BlockLeavesBase) && y > 0) {
			--y;
		}
		for (int i1 = 0; i1 < 64; ++i1) {
			int j1 = x + random.nextInt(8) - random.nextInt(8);
			int k1 = y + random.nextInt(4) - random.nextInt(4);
			int l1 = z + random.nextInt(8) - random.nextInt(8);
			int meta = 0;
			if (world.getBlock(j1, k1, l1) != Block.fluidWaterStill || !Block.blocksList[this.blockId].canBlockStay(world, j1, k1, l1))
				continue;
			world.setBlockAndMetadata(j1, k1, l1, this.blockId, meta);
		}
		return true;
	}
}

