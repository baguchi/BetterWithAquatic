package baguchan.better_with_aquatic.item;

import baguchan.better_with_aquatic.BetterWithAquatic;
import baguchan.better_with_aquatic.util.IDUtils;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.tag.ItemTags;
import turniplabs.halplibe.helper.ItemHelper;

public class ModItems {
	public static final Item small_bulb = ItemHelper.createItem(BetterWithAquatic.MOD_ID, new Item("small_bulb", IDUtils.getCurrItemId()).withTags(ItemTags.renderFullbright), "small_bulb", "small_bulb.png");


	public static void onInitialize() {
	}
}
