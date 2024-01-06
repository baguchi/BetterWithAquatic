package baguchan.better_with_aquatic;

import baguchan.better_with_aquatic.block.ModBlocks;
import baguchan.better_with_aquatic.crafting.ModCraftings;
import baguchan.better_with_aquatic.entity.DrownedModel;
import baguchan.better_with_aquatic.entity.EntityAnglerFish;
import baguchan.better_with_aquatic.entity.EntityDrowned;
import baguchan.better_with_aquatic.entity.EntityFish;
import baguchan.better_with_aquatic.entity.render.*;
import baguchan.better_with_aquatic.item.ModItems;
import baguchan.better_with_aquatic.packet.SwimPacket;
import baguchan.better_with_aquatic.util.IDUtils;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.block.Block;
import turniplabs.halplibe.helper.EntityHelper;
import turniplabs.halplibe.helper.NetworkHelper;
import turniplabs.halplibe.util.ConfigHandler;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;
import useless.dragonfly.helper.ModelHelper;

import java.util.Properties;


public class BetterWithAquatic implements GameStartEntrypoint, RecipeEntrypoint, ModInitializer {

	public static final String MOD_ID = "better_with_aquatic";


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
	private static boolean enable_drowned;
	private static boolean enable_swim;

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
	public void beforeGameStart() {
		this.handleConfig();
		Block.lightBlock[Block.fluidWaterFlowing.id] = 1;
		Block.lightBlock[Block.fluidWaterStill.id] = 1;
		ModBlocks.createBlocks();
		ModItems.onInitialize();

		EntityHelper.createEntity(EntityFish.class, new RenderFish(new FishModel(), 0.3F), entityID, "Fish");
		EntityHelper.createEntity(EntityAnglerFish.class, new RenderAnglerFish(new AnglerFishModel(), 0.4F), entityID + 1, "AnglerFish");

		EntityHelper.createEntity(EntityDrowned.class, new DrownedRenderer(ModelHelper.getOrCreateEntityModel(MOD_ID, "entity/drowned.geo.json", DrownedModel.class), 0.5f), entityID + 2, "Drowned");


	}

	@Override
	public void afterGameStart() {
	}


	public static boolean isEnableSwim() {
		return enable_swim;
	}

	public static boolean isEnableDrowned() {
		return enable_drowned;
	}

	@Override
	public void onRecipesReady() {
		ModCraftings.register();

	}

	@Override
	public void onInitialize() {
		NetworkHelper.register(SwimPacket.class, true, false);
	}
}
