package baguchan.better_with_aquatic.entity;

import net.minecraft.core.world.World;

public class EntityAnglerFish extends EntityBaseFish {
	public EntityAnglerFish(World world) {
		super(world);
		this.highestSkinVariant = -1;
		this.setSize(0.5F, 0.45F);
		this.setPos(this.x, this.y, this.z);

		this.skinName = "angler_fish";
	}

	@Override
	public String getEntityTexture() {
		return "/assets/better_with_aquatic/entity/angler_fish.png";
	}

	@Override
	public String getDefaultEntityTexture() {
		return "/assets/better_with_aquatic/entity/angler_fish.png";
	}


	@Override
	protected float getBlockPathWeight(int x, int y, int z) {
		return 0.5f - this.world.getLightBrightness(x, y, z);
	}
}
