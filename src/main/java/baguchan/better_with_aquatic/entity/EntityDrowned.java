package baguchan.better_with_aquatic.entity;

import baguchan.better_ai.api.IPathGetter;
import baguchan.better_ai.path.BetterPathFinder;
import baguchan.better_ai.path.BetterSwimPathFinder;
import baguchan.better_ai.util.BlockPath;
import baguchan.better_with_aquatic.api.ISwiming;
import baguchan.better_with_aquatic.util.MathUtil;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.monster.EntityZombie;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.Vec3d;
import net.minecraft.core.world.World;

public class EntityDrowned extends EntityZombie implements IPathGetter, ISwiming {
	private Entity currentTarget;
	public boolean swimming;
	private float swimAmount;
	private float swimAmountO;
	public BetterSwimPathFinder betterSwimPathFinder;
	public BetterPathFinder betterPathFinder;

	public EntityDrowned(World world) {
		super(world);
		this.betterPathFinder = new BetterPathFinder(world);
		this.betterSwimPathFinder = new BetterSwimPathFinder(world);
		this.setPathfindingMalus(this, BlockPath.WATER, 0.0F);
		this.setPathfindingMalus(this, BlockPath.OPEN, -1.0F);
		this.footSize = 1f;
		this.highestSkinVariant = -1;
		this.skinName = "drowned";
	}

	@Override
	public String getEntityTexture() {
		return "/assets/better_with_aquatic/entity/drowned.png";
	}

	@Override
	public String getDefaultEntityTexture() {
		return "/assets/better_with_aquatic/entity/drowned.png";
	}


	@Override
	public void tick() {
		super.tick();
		if (this.isInWater()) {
			if (this.getPathFinder(this) != this.betterSwimPathFinder) {
				this.setPathFinder(this, this.betterSwimPathFinder);
			}
		} else {
			if (this.getPathFinder(this) != this.betterPathFinder) {
				this.setPathFinder(this, this.betterPathFinder);
			}
		}
		if (this.isInWater()) {
			setSwimming(true);
		} else {
			setSwimming(false);
		}
		this.updateSwimAmount();
	}

	private void updateSwimAmount() {
		this.swimAmountO = this.swimAmount;
		if (this.isSwimming()) {
			this.swimAmount = Math.min(1.0F, this.swimAmount + 0.09F);
		} else {
			this.swimAmount = Math.max(0.0F, this.swimAmount - 0.09F);
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
			this.prevLimbYaw = this.limbYaw;
			double d2 = this.x - this.xo;
			double d3 = this.z - this.zo;
			float f5 = MathHelper.sqrt_double(d2 * d2 + d3 * d3) * 4.0f;
			if (f5 > 1.0f) {
				f5 = 1.0f;
			}
			this.limbYaw += (f5 - this.limbYaw) * 0.4f;
			this.limbSwing += this.limbYaw;
		} else {
			super.moveEntityWithHeading(moveStrafing, moveForward);
		}
	}

	@Override
	protected void updatePlayerActionState() {
		if (this.isInWater()) {
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
				this.moveForward = this.moveSpeed * 0.15F;
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
				this.yd += MathHelper.clamp(y1 / d3, -0.5F, 0.5F) * 0.15F * this.moveSpeed;
			}
			if (this.entityToAttack != null) {
				this.faceEntity(this.entityToAttack, 30.0f, 30.0f);
			}
		} else {
			super.updatePlayerActionState();
			this.currentTarget = null;
		}
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
	public boolean canBreatheUnderwater() {
		return true;
	}

	@Override
	public void setSwimming(boolean swiming) {
		this.swimming = swiming;
	}

	@Override
	public boolean isSwimming() {
		return this.swimming;
	}

	@Override
	public float getSwimAmount(float p_20999_) {
		return MathUtil.lerp(p_20999_, this.swimAmountO, this.swimAmount);
	}
}
