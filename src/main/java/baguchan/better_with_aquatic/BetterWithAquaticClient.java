package baguchan.better_with_aquatic;

import baguchan.better_with_aquatic.block.ModBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.block.model.BlockModelRenderBlocks;


public class BetterWithAquaticClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		BlockModelDispatcher dispatcher = BlockModelDispatcher.getInstance();
		dispatcher.addDispatch(ModBlocks.sea_grass, new BlockModelRenderBlocks(6));
		dispatcher.addDispatch(ModBlocks.sea_grass_flow, new BlockModelRenderBlocks(6));

	}
}
