package baguchan.better_with_aquatic;

import baguchan.better_with_aquatic.block.ModBlocks;
import baguchan.better_with_aquatic.entity.EntityAnglerFish;
import baguchan.better_with_aquatic.entity.EntityDrowned;
import baguchan.better_with_aquatic.entity.EntityFish;
import baguchan.better_with_aquatic.item.ModItems;
import baguchan.better_with_aquatic.packet.SwimPacket;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.gui.guidebook.mobs.MobInfoRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.item.Item;
import turniplabs.halplibe.helper.EntityHelper;
import turniplabs.halplibe.helper.NetworkHelper;
import turniplabs.halplibe.util.ConfigHandler;
import turniplabs.halplibe.util.GameStartEntrypoint;

import java.util.Properties;


public class BetterWithAquatic implements GameStartEntrypoint, ModInitializer {

	public static final String MOD_ID = "better_with_aquatic";
	private static final boolean enable_drowned;
	private static final boolean enable_swim;
	public static ConfigHandler config;
	static {
		Properties prop = new Properties();
		prop.setProperty("starting_block_id", "3200");
		prop.setProperty("starting_item_id", "26000");
		prop.setProperty("starting_entity_id", "600");
		prop.setProperty("enable_swim", "true");
		prop.setProperty("enable_drowned", "true");
		config = new ConfigHandler(BetterWithAquatic.MOD_ID, prop);
		entityID = config.getInt("starting_entity_id");
		enable_swim = config.getBoolean("enable_swim");
		enable_drowned = config.getBoolean("enable_drowned");
		config.updateConfig();
	}

	public static int entityID;

	@Override
	public void onInitialize() {
		NetworkHelper.register(SwimPacket.class, true, false);
	}
	@Override
	public void beforeGameStart() {
		Block.lightBlock[Block.fluidWaterFlowing.id] = 1;
		Block.lightBlock[Block.fluidWaterStill.id] = 1;
		ModBlocks.createBlocks();
		ModItems.onInitialize();

		EntityHelper.Core.createEntity(EntityFish.class, entityID, "Fish");
		EntityHelper.Core.createEntity(EntityAnglerFish.class, entityID + 1, "AnglerFish");
		EntityHelper.Core.createEntity(EntityDrowned.class, entityID + 2, "Drowned");

		MobInfoRegistry.register(EntityFish.class, "guidebook.section.mob.better_with_aquatic.fish.name", "guidebook.section.mob.better_with_aquatic.fish.desc", 3, 20, new MobInfoRegistry.MobDrop[]{new MobInfoRegistry.MobDrop(Item.foodFishRaw.getDefaultStack(), 1.0f, 1, 1)});
		MobInfoRegistry.register(EntityAnglerFish.class, "guidebook.section.mob.better_with_aquatic.angler_fish.name", "guidebook.section.mob.better_with_aquatic.angler_fish.desc", 3, 20, new MobInfoRegistry.MobDrop[]{new MobInfoRegistry.MobDrop(ModItems.small_bulb.getDefaultStack(), 1.0f, 1, 1)});
		MobInfoRegistry.register(EntityDrowned.class, "guidebook.section.mob.better_with_aquatic.drowned.name", "guidebook.section.mob.better_with_aquatic.drowned.desc", 20, 300, new MobInfoRegistry.MobDrop[]{new MobInfoRegistry.MobDrop(Item.cloth.getDefaultStack(), 0.66f, 1, 2)});

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
}
