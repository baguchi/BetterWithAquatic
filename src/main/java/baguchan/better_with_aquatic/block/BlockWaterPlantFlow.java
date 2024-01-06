package baguchan.better_with_aquatic.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockFluid;
import net.minecraft.core.block.BlockFluidFlowing;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.World;

import java.util.Random;

public class BlockWaterPlantFlow extends BlockFluidFlowing {
	int numAdjacentSources = 0;
	boolean[] isOptimalFlowDirection = new boolean[4];
	int[] flowCost = new int[4];

	public BlockWaterPlantFlow(String name, int openIds, Material water) {
		super(name, openIds, water);
		float f = 0.4f;
		this.setBlockBounds(0.5f - f, 0.0f, 0.5f - f, 0.5f + f, 0.8f, 0.5f + f);
	}

	private void setFluidStill(World world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		world.setBlockAndMetadata(x, y, z, ModBlocks.sea_grass.id, meta);
		world.markBlocksDirty(x, y, z, x, y, z);
		world.markBlockNeedsUpdate(x, y, z);
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		int localFlowDecay = this.getDepth(world, x, y, z);
		int flowDecayMod = 1;
		if (this.blockMaterial == Material.lava && world.dimension != Dimension.nether) {
			flowDecayMod = 2;
		}
		if (localFlowDecay > 0) {
			int surroundingFlowDecay = -100;
			this.numAdjacentSources = 0;
			surroundingFlowDecay = this.getSmallestFlowDecay(world, x - 1, y, z, surroundingFlowDecay);
			surroundingFlowDecay = this.getSmallestFlowDecay(world, x + 1, y, z, surroundingFlowDecay);
			surroundingFlowDecay = this.getSmallestFlowDecay(world, x, y, z - 1, surroundingFlowDecay);
			int newFlowDecay = (surroundingFlowDecay = this.getSmallestFlowDecay(world, x, y, z + 1, surroundingFlowDecay)) + flowDecayMod;
			if (newFlowDecay >= 8 || surroundingFlowDecay < 0) {
				newFlowDecay = -1;
			}
			if (this.getDepth(world, x, y + 1, z) >= 0) {
				int flowDecayAbove = this.getDepth(world, x, y + 1, z);
				newFlowDecay = flowDecayAbove >= 8 ? flowDecayAbove : flowDecayAbove + 8;
			}
			if (this.numAdjacentSources >= 2 && this.blockMaterial == Material.water) {
				if (world.getBlockMaterial(x, y - 1, z).isSolid()) {
					newFlowDecay = 0;
				} else if (world.getBlockMaterial(x, y - 1, z) == this.blockMaterial && world.getBlockMetadata(x, y - 1, z) == 0) {
					newFlowDecay = 0;
				}
			}
			if (newFlowDecay != localFlowDecay) {
				localFlowDecay = newFlowDecay;
				if (localFlowDecay < 0) {
					world.setBlockWithNotify(x, y, z, 0);
				} else {
					world.setBlockMetadataWithNotify(x, y, z, localFlowDecay);
					world.scheduleBlockUpdate(x, y, z, this.id, this.tickRate());
					world.notifyBlocksOfNeighborChange(x, y, z, this.id);
				}
			} else {
				this.setFluidStill(world, x, y, z);
			}
		} else {
			this.setFluidStill(world, x, y, z);
		}
		if (this.liquidCanDisplaceBlock(world, x, y - 1, z)) {
			int id = world.getBlockId(x, y - 1, z);
			int meta = world.getBlockMetadata(x, y - 1, z);
			if (id > 0) {
				Block.blocksList[id].dropBlockWithCause(world, EnumDropCause.WORLD, x, y - 1, z, meta, null);
			}
			if (localFlowDecay >= 8) {
				world.setBlockAndMetadataWithNotify(x, y - 1, z, Block.fluidWaterFlowing.id, localFlowDecay);
			} else {
				world.setBlockAndMetadataWithNotify(x, y - 1, z, Block.fluidWaterFlowing.id, localFlowDecay + 8);
			}
		} else if (localFlowDecay >= 0 && (localFlowDecay == 0 || this.blockBlocksFlow(world, x, y - 1, z))) {
			boolean[] aflag = this.getOptimalFlowDirections(world, x, y, z);
			int k1 = localFlowDecay + flowDecayMod;
			if (localFlowDecay >= 8) {
				k1 = 1;
			}
			if (k1 >= 8) {
				return;
			}
			if (aflag[0]) {
				this.flowIntoBlock(world, x - 1, y, z, k1);
			}
			if (aflag[1]) {
				this.flowIntoBlock(world, x + 1, y, z, k1);
			}
			if (aflag[2]) {
				this.flowIntoBlock(world, x, y, z - 1, k1);
			}
			if (aflag[3]) {
				this.flowIntoBlock(world, x, y, z + 1, k1);
			}
		}
	}

	private void flowIntoBlock(World world, int i, int j, int k, int l) {
		if (this.liquidCanDisplaceBlock(world, i, j, k)) {
			int i1 = world.getBlockId(i, j, k);
			if (i1 > 0) {
				if (this.blockMaterial == Material.lava) {
					this.fizz(world, i, j, k);
				} else {
					Block.blocksList[i1].dropBlockWithCause(world, EnumDropCause.WORLD, i, j, k, world.getBlockMetadata(i, j, k), null);
				}
			}
			world.setBlockAndMetadataWithNotify(i, j, k, Block.fluidWaterFlowing.id, l);
		}
	}

	private int calculateFlowCost(World world, int i, int j, int k, int l, int i1) {
		int j1 = 1000;
		for (int k1 = 0; k1 < 4; ++k1) {
			int k2;
			if (k1 == 0 && i1 == 1 || k1 == 1 && i1 == 0 || k1 == 2 && i1 == 3 || k1 == 3 && i1 == 2) continue;
			int l1 = i;
			int i2 = j;
			int j2 = k;
			if (k1 == 0) {
				--l1;
			}
			if (k1 == 1) {
				++l1;
			}
			if (k1 == 2) {
				--j2;
			}
			if (k1 == 3) {
				++j2;
			}
			if (this.blockBlocksFlow(world, l1, i2, j2) || world.getBlockMaterial(l1, i2, j2) == this.blockMaterial && world.getBlockMetadata(l1, i2, j2) == 0)
				continue;
			if (!this.blockBlocksFlow(world, l1, i2 - 1, j2)) {
				return l;
			}
			if (l >= 4 || (k2 = this.calculateFlowCost(world, l1, i2, j2, l + 1, k1)) >= j1) continue;
			j1 = k2;
		}
		return j1;
	}

	private boolean[] getOptimalFlowDirections(World world, int i, int j, int k) {
		for (int l = 0; l < 4; ++l) {
			this.flowCost[l] = 1000;
			int j1 = i;
			int i2 = j;
			int j2 = k;
			if (l == 0) {
				--j1;
			}
			if (l == 1) {
				++j1;
			}
			if (l == 2) {
				--j2;
			}
			if (l == 3) {
				++j2;
			}
			if (this.blockBlocksFlow(world, j1, i2, j2) || world.getBlockMaterial(j1, i2, j2) == this.blockMaterial && world.getBlockMetadata(j1, i2, j2) == 0)
				continue;
			this.flowCost[l] = !this.blockBlocksFlow(world, j1, i2 - 1, j2) ? 0 : this.calculateFlowCost(world, j1, i2, j2, 1, l);
		}
		int i1 = this.flowCost[0];
		for (int k1 = 1; k1 < 4; ++k1) {
			if (this.flowCost[k1] >= i1) continue;
			i1 = this.flowCost[k1];
		}
		for (int l1 = 0; l1 < 4; ++l1) {
			this.isOptimalFlowDirection[l1] = this.flowCost[l1] == i1;
		}
		return this.isOptimalFlowDirection;
	}

	private boolean blockBlocksFlow(World world, int x, int y, int z) {
		int id = world.getBlockId(x, y, z);
		return Block.blocksList[id] != null && !(Block.blocksList[id] instanceof BlockFluid) && !Block.blocksList[id].hasTag(BlockTags.BROKEN_BY_FLUIDS);
	}

	private boolean liquidCanDisplaceBlock(World world, int i, int j, int k) {
		Material material = world.getBlockMaterial(i, j, k);
		if (material == this.blockMaterial) {
			return false;
		}
		if (material == Material.lava) {
			return false;
		}
		return !this.blockBlocksFlow(world, i, j, k);
	}


	public boolean canThisPlantGrowOnThisBlockID(int i) {
		return i == Block.sand.id || i == Block.dirtScorched.id || i == Block.dirt.id || i == Block.mud.id;
	}

	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		return super.canPlaceBlockAt(world, x, y, z) && world.getBlockId(x, y, z) == Block.fluidWaterStill.id && world.getBlockMetadata(x, y, z) == 0 && this.canThisPlantGrowOnThisBlockID(world.getBlockId(x, y - 1, z));
	}

	@Override
	public void onBlockRemoved(World world, int x, int y, int z, int data) {
		super.onBlockRemoved(world, x, y, z, data);
		if (world.getBlockId(x, y, z) == 0) {
			world.setBlockAndMetadataWithNotify(x, y, z, fluidWaterFlowing.id, world.getBlockMetadata(x, y, z));
		}
	}

	public boolean canCollideCheck(int meta, boolean shouldCollideWithFluids) {
		return this.isCollidable();
	}

	@Override
	public boolean isCollidable() {
		return super.isCollidable();
	}

	@Override
	public int getRenderBlockPass() {
		return 0;
	}


	@Override
	public ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int x, int y, int z, int meta, TileEntity tileEntity) {
		switch (dropCause) {
			case PICK_BLOCK:
			case SILK_TOUCH: {
				return new ItemStack[]{new ItemStack(this)};
			}
		}
		return new ItemStack[]{};
	}

	@Override
	public boolean canBlockStay(World world, int x, int y, int z) {
		return (this.canThisPlantGrowOnThisBlockID(world.getBlockId(x, y - 1, z))) && world.getBlock(x, y, z) != null && world.getBlock(x, y, z).blockMaterial == Material.water;
	}
}
