package baguchan.better_with_aquatic;

import baguchan.better_with_aquatic.block.ModBlocks;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.block.model.BlockModelRenderBlocks;
import turniplabs.halplibe.util.ClientStartEntrypoint;


public class BetterWithAquaticClient implements ClientStartEntrypoint {

	@Override
	public void beforeClientStart() {
	}

	@Override
	public void afterClientStart() {
		BlockModelDispatcher dispatcher = BlockModelDispatcher.getInstance();
		dispatcher.addDispatch(ModBlocks.sea_grass, new BlockModelRenderBlocks(6));
		dispatcher.addDispatch(ModBlocks.sea_grass_flow, new BlockModelRenderBlocks(6));

	}
}
