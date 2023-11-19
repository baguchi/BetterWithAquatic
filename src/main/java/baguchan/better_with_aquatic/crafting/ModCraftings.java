package baguchan.better_with_aquatic.crafting;

import baguchan.better_with_aquatic.block.ModBlocks;
import baguchan.better_with_aquatic.item.ModItems;
import turniplabs.halplibe.helper.RecipeHelper;

public class ModCraftings {
	public static void register() {
		RecipeHelper.Crafting.createRecipe(ModBlocks.light_blub, 1, new Object[]{
			"TTT",
			"TTT",
			"TTT",
			'T', ModItems.small_bulb});
	}
}
