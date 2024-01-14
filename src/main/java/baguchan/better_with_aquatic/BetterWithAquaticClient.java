package baguchan.better_with_aquatic;

import baguchan.better_with_aquatic.block.ModBlocks;
import baguchan.better_with_aquatic.entity.EntityAnglerFish;
import baguchan.better_with_aquatic.entity.EntityDrowned;
import baguchan.better_with_aquatic.entity.EntityFish;
import baguchan.better_with_aquatic.entity.EntityFrog;
import baguchan.better_with_aquatic.entity.render.*;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.block.model.BlockModelRenderBlocks;
import turniplabs.halplibe.helper.EntityHelper;
import turniplabs.halplibe.util.ClientStartEntrypoint;
import useless.dragonfly.helper.ModelHelper;


public class BetterWithAquaticClient implements ClientStartEntrypoint {

	@Override
	public void beforeClientStart() {
		EntityHelper.Client.assignEntityRenderer(EntityFish.class, new RenderFish(new FishModel(), 0.3F));
		EntityHelper.Client.assignEntityRenderer(EntityAnglerFish.class, new RenderAnglerFish(new AnglerFishModel(), 0.4F));
		EntityHelper.Client.assignEntityRenderer(EntityDrowned.class, new DrownedRenderer(ModelHelper.getOrCreateEntityModel(BetterWithAquatic.MOD_ID, "entity/drowned.geo.json", DrownedModel.class), 0.5f));
		EntityHelper.Client.assignEntityRenderer(EntityFrog.class, new FrogRenderer(ModelHelper.getOrCreateEntityModel(BetterWithAquatic.MOD_ID, "entity/frog.geo.json", FrogModel.class), 0.3f));

	}

	@Override
	public void afterClientStart() {
		BlockModelDispatcher dispatcher = BlockModelDispatcher.getInstance();
		dispatcher.addDispatch(ModBlocks.sea_grass, new BlockModelRenderBlocks(6));
		dispatcher.addDispatch(ModBlocks.sea_grass_flow, new BlockModelRenderBlocks(6));
	}
}
