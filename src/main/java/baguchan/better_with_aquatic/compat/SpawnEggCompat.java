package baguchan.better_with_aquatic.compat;

import baguchan.better_with_aquatic.BetterWithAquatic;
import baguchan.better_with_aquatic.util.IDUtils;
import turniplabs.halplibe.helper.ItemHelper;
import useless.spawneggs.ItemSpawnEgg;

public class SpawnEggCompat {

	public static void onInitialize() {
		ItemHelper.createItem(BetterWithAquatic.MOD_ID, new ItemSpawnEgg("spawn.egg.fish", ItemHelper.findOpenIds(IDUtils.getCurrItemId()),
			"Fish", 0x6B9F93, 0xADBEDB), "spawnegg.fish", "spawnEggDefault.png");
		ItemHelper.createItem(BetterWithAquatic.MOD_ID, new ItemSpawnEgg("spawn.egg.angler_fish", ItemHelper.findOpenIds(IDUtils.getCurrItemId()),
			"AnglerFish", 0x7B3E3E, 0x3D1818), "spawnegg.angler_fish", "spawnEggDefault.png");
	}
}
