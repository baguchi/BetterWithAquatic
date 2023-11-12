package baguchan.better_with_aquatic.mixin;

import baguchan.better_with_aquatic.block.ModBlocks;
import baguchan.better_with_aquatic.world.WorldFeatureSeaGrass;
import net.minecraft.core.world.World;
import net.minecraft.core.world.chunk.Chunk;
import net.minecraft.core.world.generate.chunk.perlin.overworld.ChunkDecoratorOverworld;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(value = ChunkDecoratorOverworld.class, remap = false)
public class ChunkDecoratorOverworldMixin {

	@Shadow
	@Final
	private World world;

	@Inject(method = "decorate", at = @At("TAIL"))
	public void decorate(Chunk chunk, CallbackInfo callbackInfo) {
		int chunkX = chunk.xPosition;
		int chunkZ = chunk.zPosition;
		int minY = this.world.getWorldType().getMinY();
		int maxY = this.world.getWorldType().getMaxY();
		int rangeY = maxY + 1 - minY;
		int x = chunkX * 16;
		int z = chunkZ * 16;
		int y = this.world.getHeightValue(x + 16, z + 16);
		Random rand = new Random(this.world.getRandomSeed());
		int byte1 = 2;
		for (int l14 = 0; l14 < byte1; ++l14) {
			int type = ModBlocks.sea_grass.id;
			int l19 = x + rand.nextInt(16) + 8;
			int k22 = minY + rand.nextInt(rangeY);
			int j24 = z + rand.nextInt(16) + 8;
			new WorldFeatureSeaGrass(type).generate(this.world, rand, l19, k22 + 1, j24);
		}
	}
}
