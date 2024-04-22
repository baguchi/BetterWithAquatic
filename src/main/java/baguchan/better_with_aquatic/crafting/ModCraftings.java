package baguchan.better_with_aquatic.crafting;

import net.minecraft.core.block.Block;
import net.minecraft.core.data.DataLoader;
import net.minecraft.core.data.registry.Registries;
import net.minecraft.core.data.registry.recipe.RecipeGroup;
import net.minecraft.core.data.registry.recipe.RecipeNamespace;
import net.minecraft.core.data.registry.recipe.RecipeSymbol;
import net.minecraft.core.data.registry.recipe.entry.RecipeEntryCrafting;
import net.minecraft.core.item.ItemStack;
import turniplabs.halplibe.helper.RecipeBuilder;
import turniplabs.halplibe.util.RecipeEntrypoint;

import static baguchan.better_with_aquatic.BetterWithAquatic.MOD_ID;

public class ModCraftings implements RecipeEntrypoint {
	public static final RecipeNamespace AQUATIC = new RecipeNamespace();
	public static final RecipeGroup<RecipeEntryCrafting<?, ?>> WORKBENCH = new RecipeGroup<>(new RecipeSymbol(new ItemStack(Block.workbench)));

	@Override
	public void onRecipesReady() {
		AQUATIC.register("workbench", WORKBENCH);
		Registries.RECIPES.register("aquatic", AQUATIC);
		DataLoader.loadRecipesFromFile("/assets/better_with_aquatic/recipes/workbench.json");
	}

	@Override
	public void initNamespaces() {
		RecipeEntrypoint.super.initNamespaces();
		RecipeBuilder.initNameSpace(MOD_ID);
	}
}
