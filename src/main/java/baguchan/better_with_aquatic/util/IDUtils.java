package baguchan.better_with_aquatic.util;

public class IDUtils {
	private static int curr_item_id = 0;
	private static int curr_block_id = 0;

	public static void initIds(int blockId, int itemId) {
		curr_item_id = itemId;
		curr_block_id = blockId;
	}

	public static int getCurrBlockId() {
		return curr_block_id++;
	}

	public static int getCurrItemId() {
		return curr_item_id++;
	}
}
