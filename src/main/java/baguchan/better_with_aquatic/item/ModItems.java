package baguchan.better_with_aquatic.item;

import baguchan.better_with_aquatic.BetterWithAquatic;
import baguchan.better_with_aquatic.util.IDUtils;
import net.minecraft.core.item.Item;
import turniplabs.halplibe.helper.ItemBuilder;

public class ModItems {
	public static final Item small_bulb = new ItemBuilder(BetterWithAquatic.MOD_ID).build(new Item("small_bulb", IDUtils.getCurrItemId()));


	public static void onInitialize() {
	}
}
