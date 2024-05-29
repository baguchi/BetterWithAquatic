package baguchan.better_with_aquatic.client.render.model;

import net.minecraft.client.render.item.model.ItemModelBlock;
import net.minecraft.client.render.stitcher.IconCoordinate;
import net.minecraft.client.render.stitcher.TextureRegistry;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.block.ItemBlock;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SeaGrassItemModel extends ItemModelBlock {
	public SeaGrassItemModel(ItemBlock itemBlock) {
		super(itemBlock);
	}

	@Override
	public @NotNull IconCoordinate getIcon(@Nullable Entity entity, ItemStack itemStack) {
		return TextureRegistry.getTexture("better_with_aquatic:block/sea_grass");
	}
}
