package baguchan.better_with_aquatic.entity;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.world.World;

public class EntityAnglerFish extends EntityBaseFish {
	public EntityAnglerFish(World world) {
		super(world);
		this.highestSkinVariant = -1;
		this.setSize(0.5F, 0.45F);
		this.setPos(this.x, this.y, this.z);
		this.health = 5;
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

	@Override
	protected Entity findPlayerToAttack() {
		EntityPlayer entityplayer = this.world.getClosestPlayerToEntity(this, 16.0);
		if (entityplayer != null && this.canEntityBeSeen(entityplayer) && entityplayer.getGamemode().areMobsHostile) {
			return entityplayer;
		}
		return null;
	}

	@Override
	public boolean hurt(Entity entity, int i, DamageType type) {
		if (super.hurt(entity, i, type)) {
			if (this.passenger == entity || this.vehicle == entity) {
				return true;
			}
			if (entity != this) {
				this.entityToAttack = entity;
			}
			return true;
		}
		return false;
	}
}
