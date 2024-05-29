package baguchan.better_with_aquatic.entity;

import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;

public class EntityFish extends EntityBaseFish {
	public EntityFish(World world) {
		super(world);
		this.setSize(0.45F, 0.45F);
		this.setPos(this.x, this.y, this.z);
	}

	@Override
	public String getEntityTexture() {
		return "/assets/better_with_aquatic/textures/entity/fish.png";
	}

	@Override
	public String getDefaultEntityTexture() {
		return "/assets/better_with_aquatic/textures/entity/fish.png";
	}

	@Override
	protected void dropFewItems() {
		this.spawnAtLocation(new ItemStack(Item.foodFishRaw, 1, 0), 0.0f);
	}

}
