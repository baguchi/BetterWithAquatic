package baguchan.better_with_aquatic;

import baguchan.better_with_aquatic.block.ModBlocks;
import baguchan.better_with_aquatic.entity.EntityBaseFish;
import baguchan.better_with_aquatic.entity.render.FishModel;
import baguchan.better_with_aquatic.entity.render.RenderFish;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.block.Block;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.helper.EntityHelper;


public class BetterWithAquatic implements ModInitializer {

	public static final String MOD_ID = "better_with_aquatic";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		Block.lightOpacity[Block.fluidWaterFlowing.id] = 1;
		Block.lightOpacity[Block.fluidWaterStill.id] = 1;
		ModBlocks.createBlocks();
		EntityHelper.createEntity(EntityBaseFish.class, new RenderFish(new FishModel(), 0.3F), 600, "Fish");
	}
}
