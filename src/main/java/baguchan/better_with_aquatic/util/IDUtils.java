package baguchan.better_with_aquatic.util;

import baguchan.better_with_aquatic.BetterWithAquatic;

public class IDUtils {
	private static int curr_item_id = BetterWithAquatic.config.getInt("starting_item_id");
	private static int curr_block_id = BetterWithAquatic.config.getInt("starting_block_id");
	public static int getCurrBlockId() {
		return curr_block_id++;
	}
	public static int getCurrItemId() {
		return curr_item_id++;
	}
}
