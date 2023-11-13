package baguchan.better_with_aquatic.world;

import baguchan.better_with_aquatic.block.ModBlocks;
import net.minecraft.core.block.Block;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;

import java.util.Random;

public class WorldFeatureCoral extends WorldFeature {
	public WorldFeatureCoral() {
	}

	@Override
	public boolean generate(World world, Random random, int x, int y, int z) {
		if (world.getBlockId(x, y - 1, z) != Block.fluidWaterStill.id) {
			return false;
		}
		int id = ModBlocks.coral_blue.id + random.nextInt(6);

		for (int l = 0; l < 4; ++l) {
			int i1 = x + random.nextInt(8) - random.nextInt(8);
			int k1 = z + random.nextInt(8) - random.nextInt(8);
			while (world.getBlockId(i1, y - 1, k1) == Block.fluidWaterStill.id) {
				--y;
			}
			if (world.getBlockId(i1, y - 1, k1) == Block.sand.id || world.getBlockId(i1, y - 1, k1) == Block.dirt.id) {
				world.setBlockRaw(i1, y, k1, id);
				this.generateCoralGroup(world, random, i1, y, k1, id);
			}
		}
		return false;
	}

	private void generateCoralGroup(World world, Random random, int x, int y, int z, int id) {
		for (int l = 0; l < 9; ++l) {
			int i1 = x + random.nextInt(3) - random.nextInt(3);
			int j1 = y + random.nextInt(3) - random.nextInt(3);
			int k1 = z + random.nextInt(3) - random.nextInt(3);
			if (world.getBlockId(i1, j1, k1) == Block.fluidWaterStill.id) {
				world.setBlockRaw(i1, j1, k1, id);
			}
		}
	}
}

