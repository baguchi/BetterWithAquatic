package baguchan.better_with_aquatic.entity;

import baguchan.better_with_aquatic.item.ModItems;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.enums.LightLayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;

public class EntityAnglerFish extends EntityBaseFish {
	public EntityAnglerFish(World world) {
		super(world);
		this.setSize(0.5F, 0.45F);
		this.setPos(this.x, this.y, this.z);
		this.skinName = "angler_fish";
	}

	@Override
	public int getMaxHealth() {
		return 5;
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
	public boolean getCanSpawnHere() {
		int k;
		int j;
		int i = MathHelper.floor_double(this.x);
		if (this.world.getSavedLightValue(LightLayer.Block, i, j = MathHelper.floor_double(this.bb.minY), k = MathHelper.floor_double(this.z)) > 0) {
			return false;
		}
		if (this.world.getSavedLightValue(LightLayer.Sky, i, j, k) > this.random.nextInt(32)) {
			return false;
		}
		int blockLight = this.world.getBlockLightValue(i, j, k);
		if (this.world.getCurrentWeather() != null && this.world.getCurrentWeather().doMobsSpawnInDaylight) {
			blockLight /= 2;
		}
		return blockLight <= 4 && super.getCanSpawnHere();
	}

	@Override
	protected Entity findPlayerToAttack() {
		EntityPlayer entityplayer = this.world.getClosestPlayerToEntity(this, 16.0);
		if (entityplayer != null && this.canEntityBeSeen(entityplayer) && entityplayer.getGamemode().areMobsHostile()) {
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

	protected void attackEntity(Entity entity, float distance) {
		if (this.attackTime <= 0 && distance < 1.5f && entity.bb.maxY > this.bb.minY && entity.bb.minY < this.bb.maxY) {
			this.attackTime = 20;
			entity.hurt(this, 1, DamageType.COMBAT);
		}
	}

	@Override
	protected void dropFewItems() {
		this.spawnAtLocation(new ItemStack(ModItems.small_bulb, 1, 0), 0.0f);
	}

}
