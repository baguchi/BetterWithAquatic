package baguchan.better_with_aquatic;

import baguchan.better_with_aquatic.block.ModBlocks;
import baguchan.better_with_aquatic.item.ModItems;
import baguchan.better_with_aquatic.util.IDUtils;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import net.minecraft.core.block.Block;
import turniplabs.halplibe.util.ConfigHandler;

import java.util.Properties;

public class BetterWithAquaticPreLaunch implements PreLaunchEntrypoint {
	static {
		// DO NOT TOUCH THIS! It's an error prevention method. Thanks Useless!
		try {
			Class.forName("turniplabs.halplibe.HalpLibe");
			Class.forName("net.minecraft.core.block.Block");
			Class.forName("net.minecraft.core.item.Item");
		} catch (ClassNotFoundException ignored) {
		}
	}

	public static int entityID;
	private static boolean enable_swim;
	private static boolean enable_drowned;

	private void handleConfig() {
		Properties prop = new Properties();
		prop.setProperty("starting_block_id", "3200");
		prop.setProperty("starting_item_id", "2600");
		prop.setProperty("starting_entity_id", "600");
		prop.setProperty("enable_swim", "true");
		prop.setProperty("enable_drowned", "true");
		ConfigHandler config = new ConfigHandler(BetterWithAquatic.MOD_ID, prop);
		IDUtils.initIds(
			config.getInt("starting_block_id"),
			config.getInt("starting_item_id"));

		entityID = config.getInt("starting_entity_id");
		enable_swim = config.getBoolean("enable_swim");
		enable_drowned = config.getBoolean("enable_drowned");
		config.updateConfig();
	}

	@Override
	public void onPreLaunch() {
		this.handleConfig();
		Block.lightOpacity[Block.fluidWaterFlowing.id] = 1;
		Block.lightOpacity[Block.fluidWaterStill.id] = 1;
		ModBlocks.createBlocks();
		ModItems.onInitialize();
	}


	public static boolean isEnableSwim() {
		return enable_swim;
	}

	public static boolean isEnableDrowned() {
		return enable_drowned;
	}
}
