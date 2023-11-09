package baguchan.better_with_aquatic.entity;

import baguchan.better_ai.api.IPathGetter;
import baguchan.better_ai.path.BetterSwimPathFinder;
import baguchan.better_ai.util.BlockPath;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.animal.EntityWaterAnimal;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.Vec3d;
import net.minecraft.core.world.World;

public class EntityBaseFish extends EntityWaterAnimal implements IPathGetter {
	public BetterSwimPathFinder betterSwimPathFinder;
	private Entity currentTarget;

	public EntityBaseFish(World world) {
		super(world);
		this.scoreValue = 20;
		this.health = 3;
		this.heightOffset = 0.0F;
		this.footSize = 0.5F;
		this.moveSpeed = 0.1F;
		this.highestSkinVariant = -1;
		this.setSize(0.45F, 0.45F);
		this.setPos(this.x, this.y, this.z);
		this.skinName = "fish";
		this.betterSwimPathFinder = new BetterSwimPathFinder(world);
		this.setPathFinder(this, this.betterSwimPathFinder);
		this.setPathfindingMalus(this, BlockPath.WATER, 0.0F);
		this.setPathfindingMalus(this, BlockPath.OPEN, -1.0F);
	}

	@Override
	public String getEntityTexture() {
		return "/assets/better_with_aquatic/entity/fish.png";
	}

	@Override
	public String getDefaultEntityTexture() {
		return "/assets/better_with_aquatic/entity/fish.png";
	}

	@Override
	protected String getLivingSound() {
		return null;
	}

	@Override
	protected String getHurtSound() {
		return null;
	}

	@Override
	protected String getDeathSound() {
		return null;
	}

	protected boolean canDespawn() {
		return true;
	}

	@Override
	public boolean canMoveDirect() {
		return true;
	}


	@Override
	public void onLivingUpdate() {
		if (this.isInWater()) {
			this.isJumping = false;
		}
		super.onLivingUpdate();
	}

	@Override
	protected void updatePlayerActionState() {
		this.hasAttacked = this.isMovementCeased();
		float sightRadius = 16.0f;
		if (this.entityToAttack == null) {
			this.entityToAttack = this.findPlayerToAttack();
			if (this.entityToAttack != null) {
				this.pathToEntity = this.world.getPathToEntity(this, this.entityToAttack, sightRadius);
			}
		} else if (!this.entityToAttack.isAlive()) {
			this.entityToAttack = null;
		} else {
			float distanceToEntity = this.entityToAttack.distanceTo(this);
			if (this.canEntityBeSeen(this.entityToAttack)) {
				this.attackEntity(this.entityToAttack, distanceToEntity);
			} else {
				this.attackBlockedEntity(this.entityToAttack, distanceToEntity);
			}
		}
		if (!(this.hasAttacked || this.entityToAttack == null || this.pathToEntity != null && this.random.nextInt(20) != 0)) {
			this.pathToEntity = this.world.getPathToEntity(this, this.entityToAttack, sightRadius);
		} else if (!this.hasAttacked && this.closestFireflyEntity == null && (this.pathToEntity == null && this.random.nextInt(80) == 0 || this.random.nextInt(80) == 0)) {
			this.roamRandomPath();
		}

		if (this.isInWater()) {
			this.xRot = 0.0f;
			if (this.pathToEntity == null || this.random.nextInt(100) == 0) {
				this.defaultPlayerActionState();
				this.pathToEntity = null;
				return;
			}
			Vec3d coordsForNextPath = this.pathToEntity.getPos(this);
			double d = this.bbWidth * 2.0f;
			double d2 = this.bbHeight * 2.0f;
			while (coordsForNextPath != null && coordsForNextPath.squareDistanceTo(this.x, this.y, this.z) < d * d + d2 * d2) {
				this.pathToEntity.next();
				if (this.pathToEntity.isDone()) {
					this.closestFireflyEntity = null;
					coordsForNextPath = null;
					this.pathToEntity = null;
					continue;
				}
				coordsForNextPath = this.pathToEntity.getPos(this);
			}
			this.isJumping = false;
			if (coordsForNextPath != null) {
				float f3;
				double x1 = coordsForNextPath.xCoord - this.x;
				double z1 = coordsForNextPath.zCoord - this.z;
				double y1 = coordsForNextPath.yCoord - this.y;
				float f2 = (float) (Math.atan2(z1, x1) * 180.0 / 3.1415927410125732) - 90.0f;
				this.moveForward = this.moveSpeed;
				for (f3 = f2 - this.yRot; f3 < -180.0f; f3 += 360.0f) {
				}
				while (f3 >= 180.0f) {
					f3 -= 360.0f;
				}
				if (f3 > 30.0f) {
					f3 = 30.0f;
				}
				if (f3 < -30.0f) {
					f3 = -30.0f;
				}
				this.yRot += f3;
				if (this.hasAttacked && this.entityToAttack != null) {
					double d4 = this.entityToAttack.x - this.x;
					double d5 = this.entityToAttack.z - this.z;
					float f5 = this.yRot;
					this.yRot = (float) (Math.atan2(d5, d4) * 180.0 / 3.1415927410125732) - 90.0f;
					float f4 = (f5 - this.yRot + 90.0f) * 3.141593f / 180.0f;
					this.moveStrafing = -MathHelper.sin(f4) * this.moveForward * 1.0f;
					this.moveForward = MathHelper.cos(f4) * this.moveForward * 1.0f;
				}


				double d3 = Math.sqrt(x1 * x1 + y1 * y1 + z1 * z1);
				this.yd = MathHelper.clamp(y1 / d3, -0.5F, 0.5F) * this.moveSpeed;
			}
			if (this.entityToAttack != null) {
				this.faceEntity(this.entityToAttack, 30.0f, 30.0f);
			}
		} else {
			if (this.onGround) {
				this.isJumping = true;
			}
		}
	}

	@Override
	protected void roamRandomPath() {
		boolean canMoveToPoint = false;
		int x = -1;
		int y = -1;
		int z = -1;
		float bestPathWeight = -99999.0f;
		for (int l = 0; l < 10; ++l) {
			int z1;
			int y1;
			int x1 = MathHelper.floor_double(this.x + (double) this.random.nextInt(13) - 6.0);
			float currentPathWeight = this.getBlockPathWeight(x1, y1 = MathHelper.floor_double(this.y + (double) this.random.nextInt(13) - 6.0), z1 = MathHelper.floor_double(this.z + (double) this.random.nextInt(13) - 6.0));
			if (!(currentPathWeight > bestPathWeight)) continue;
			bestPathWeight = currentPathWeight;
			x = x1;
			y = y1;
			z = z1;
			canMoveToPoint = true;
		}
		if (canMoveToPoint) {
			this.pathToEntity = this.world.getEntityPathToXYZ(this, x, y, z, 10.0f);
		}
	}

	//I wonder why original isInWater method has moving entity?
	@Override
	public boolean isInWater() {
		return this.world.isMaterialInBB(this.bb.expand(0.0, 0, 0.0), Material.water);
	}

	protected void defaultPlayerActionState() {
		++this.entityAge;
		this.tryToDespawn();
		this.moveStrafing = 0.0f;
		this.moveForward = 0.0f;
		float f = 8.0f;
		if (this.random.nextFloat() < 0.02f) {
			EntityPlayer entityplayer1 = this.world.getClosestPlayerToEntity(this, f);
			if (entityplayer1 != null) {
				this.currentTarget = entityplayer1;
				this.numTicksToChaseTarget = 10 + this.random.nextInt(20);
			} else {
				this.randomYawVelocity = (this.random.nextFloat() - 0.5f) * 20.0f;
			}
		}
		if (this.currentTarget != null) {
			this.faceEntity(this.currentTarget, 10.0f, this.func_25026_x());
			if (this.numTicksToChaseTarget-- <= 0 || this.currentTarget.removed || this.currentTarget.distanceToSqr(this) > (double) (f * f)) {
				this.currentTarget = null;
			}
		} else {
			if (this.random.nextFloat() < 0.05f) {
				this.randomYawVelocity = (this.random.nextFloat() - 0.5f) * 20.0f;
			}
			this.yRot += this.randomYawVelocity;
			this.xRot = this.defaultPitch;
		}
	}

	@Override
	public void moveEntityWithHeading(float moveStrafing, float moveForward) {
		if (this.isInWater()) {
			this.moveRelative(moveStrafing, moveForward, 0.2F);
			this.move(this.xd, this.yd, this.zd);
			this.xd *= 0.8;
			this.yd *= 0.8;
			this.zd *= 0.8;
		} else {
			super.moveEntityWithHeading(moveStrafing, moveForward);
		}
	}

	@Override
	protected void dropFewItems() {
		this.spawnAtLocation(new ItemStack(Item.foodFishRaw, 1, 0), 0.0f);
	}

	@Override
	public boolean canMoveIt(BlockPath blockPath) {
		return blockPath == BlockPath.WATER;
	}
}
