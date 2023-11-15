package baguchan.better_with_aquatic;

import baguchan.better_with_aquatic.block.ModBlocks;
import baguchan.better_with_aquatic.entity.EntityBaseFish;
import baguchan.better_with_aquatic.entity.render.FishModel;
import baguchan.better_with_aquatic.entity.render.RenderFish;
import baguchan.better_with_aquatic.packet.SwimPacket;
import baguchan.better_with_aquatic.util.IDUtils;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.block.Block;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.helper.EntityHelper;
import turniplabs.halplibe.helper.NetworkHelper;
import turniplabs.halplibe.util.ConfigHandler;

import java.util.Properties;


public class BetterWithAquatic implements ModInitializer {

	public static final String MOD_ID = "better_with_aquatic";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	private boolean enable_swim;

	private void handleConfig() {
		Properties prop = new Properties();
		prop.setProperty("starting_block_id", "561");
		prop.setProperty("starting_item_id", "2000");
		prop.setProperty("enable_swim", "true");
		ConfigHandler config = new ConfigHandler(MOD_ID, prop);
		IDUtils.initIds(
			config.getInt("starting_block_id"),
			config.getInt("starting_item_id"));
		this.enable_swim = config.getBoolean("enable_swim");
		config.updateConfig();
	}

	@Override
	public void onInitialize() {
		this.handleConfig();
		Block.lightOpacity[Block.fluidWaterFlowing.id] = 1;
		Block.lightOpacity[Block.fluidWaterStill.id] = 1;
		ModBlocks.createBlocks();
		EntityHelper.createEntity(EntityBaseFish.class, new RenderFish(new FishModel(), 0.3F), 600, "Fish");
		NetworkHelper.register(SwimPacket.class, true, false);
	}

	public boolean isEnableSwim() {
		return enable_swim;
	}
}
