package baguchan.better_with_aquatic;

import baguchan.better_with_aquatic.block.ModBlocks;
import baguchan.better_with_aquatic.entity.EntityAnglerFish;
import baguchan.better_with_aquatic.entity.EntityDrowned;
import baguchan.better_with_aquatic.entity.EntityFish;
import baguchan.better_with_aquatic.item.ModItems;
import net.minecraft.client.gui.guidebook.mobs.MobInfoRegistry;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.block.model.BlockModelRenderBlocks;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
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
		MobInfoRegistry.register(EntityFish.class, "guidebook.section.mob.better_with_aquatic.fish.name", "guidebook.section.mob.better_with_aquatic.fish.desc", 3, 20, new MobInfoRegistry.MobDrop[]{new MobInfoRegistry.MobDrop(new ItemStack(Item.foodFishRaw), 1.0f, 1, 1)});
		MobInfoRegistry.register(EntityAnglerFish.class, "guidebook.section.mob.better_with_aquatic.angler_fish.name", "guidebook.section.mob.better_with_aquatic.angler_fish.desc", 3, 20, new MobInfoRegistry.MobDrop[]{new MobInfoRegistry.MobDrop(new ItemStack(ModItems.small_bulb), 1.0f, 1, 1)});
		MobInfoRegistry.register(EntityDrowned.class, "guidebook.section.mob.better_with_aquatic.drowned.name", "guidebook.section.mob.better_with_aquatic.drowned.desc", 20, 300, new MobInfoRegistry.MobDrop[]{new MobInfoRegistry.MobDrop(new ItemStack(Item.cloth), 0.66f, 1, 2)});
	}
}
