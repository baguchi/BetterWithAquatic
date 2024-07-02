package baguchan.better_with_aquatic.entity;

import baguchan.better_ai.api.IPathGetter;
import baguchan.better_ai.util.BlockPath;
import baguchan.better_with_aquatic.api.ISwiming;
import baguchan.better_with_aquatic.entity.path.BetterSwimWalkPathFinder;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.animal.EntityAnimal;
import net.minecraft.core.entity.monster.EntitySlime;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.Vec3d;
import net.minecraft.core.world.World;
import org.useless.dragonfly.model.entity.AnimationState;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class EntityFrog extends EntityAnimal implements IPathGetter, ISwiming {
	private Entity currentTarget;
	public boolean swimming;
	private int frogJumpDelay = 0;

	public AnimationState jumpState = new AnimationState();
	public AnimationState attackState = new AnimationState();

	public EntityFrog(World world) {
		super(world);
		this.setPathFinder(this, new BetterSwimWalkPathFinder(world, this));
		this.setPathfindingMalus(this, BlockPath.WATER, 0.0F);
		this.footSize = 1f;
		this.frogJumpDelay = 20;

		this.scoreValue = 0;
		this.heightOffset = 0.0F;
		this.moveSpeed = 0.75F;
		this.setSize(0.45F, 0.35F);
		this.setPos(this.x, this.y, this.z);
	}

	@Override
	public int getMaxHealth() {
		return 6;
	}

	@Override
	public String getLivingSound() {
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

	@Override
	public String getEntityTexture() {
		return "/assets/better_with_aquatic/textures/entity/frog.png";
	}

	@Override
	public String getDefaultEntityTexture() {
		return "/assets/better_with_aquatic/textures/entity/frog.png";
	}


	@Override
	public void tick() {
		super.tick();
		setSwimming(this.isInWater());
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
		if (this.onGround) {
			this.jumpState.stop();
			this.moveForward = 0.0f;
			this.moveStrafing = 0.0f;
		}
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
			double d = this.bbWidth * 1.5f;
			double d2 = this.bbHeight * 1.5f;
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
					double d4 = x1 - this.x;
					double d5 = z1 - this.z;
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
			int i = MathHelper.floor_double(this.bb.minY + 0.5);
			boolean inWater = this.isInWater();
			boolean inLava = this.isInLava();
			this.xRot = 0.0f;
			if (this.pathToEntity == null || this.random.nextInt(100) == 0) {
				super.updatePlayerActionState();
				this.pathToEntity = null;
				return;
			}
			Vec3d coordsForNextPath = this.pathToEntity.getPos(this);
			double d = this.bbWidth * 2.0f;
			while (coordsForNextPath != null && coordsForNextPath.squareDistanceTo(this.x, coordsForNextPath.yCoord, this.z) < d * d) {
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
				if (this.onGround && this.frogJumpDelay-- <= 0) {
					float f3;
					double x1 = coordsForNextPath.xCoord - this.x;
					double z1 = coordsForNextPath.zCoord - this.z;
					double y1 = coordsForNextPath.yCoord - (double) i;
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
						double d4 = x1 - this.x;
						double d5 = z1 - this.z;
						float f5 = this.yRot;
						this.yRot = (float) (Math.atan2(d5, d4) * 180.0 / 3.1415927410125732) - 90.0f;
						float f4 = (f5 - this.yRot + 90.0f) * 3.141593f / 180.0f;
						this.moveStrafing = -MathHelper.sin(f4) * this.moveForward * 1.0f;
						this.moveForward = MathHelper.cos(f4) * this.moveForward * 1.0f;
					}
					this.isJumping = true;
					this.frogJumpDelay = this.random.nextInt(20) + 10;
					if (this.entityToAttack != null) {
						this.frogJumpDelay /= (int) 2.5F;
					}
					this.jumpState.start(this.tickCount);
				}
			}
			if (this.entityToAttack != null) {
				this.faceEntity(this.entityToAttack, 30.0f, 30.0f);
			}
			if (this.horizontalCollision && !this.hasPath()) {
				this.isJumping = true;
			}
			if (this.random.nextFloat() < 0.8f && (inWater || inLava)) {
				this.isJumping = true;
			}
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
	protected void attackEntity(Entity entity, float distance) {
		if (this.attackTime <= 0 && distance < 2.0f && entity.bb.maxY > this.bb.minY && entity.bb.minY < this.bb.maxY) {
			this.attackState.start(this.tickCount);
			this.attackTime = 20;
			entity.hurt(this, 2, DamageType.COMBAT);
		}
	}

	@Override
	protected Entity findPlayerToAttack() {
		List<Entity> entityplayer = this.world.getEntitiesWithinAABB(EntitySlime.class, this.bb.expand(8F, 8F, 8F));
		Optional<Entity> slime = entityplayer.stream().min(Comparator.comparingDouble(this::distanceToSqr));

		if (slime.isPresent() && this.canEntityBeSeen(slime.get())) {
			return slime.get();
		}
		return null;
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
		return 0F;
	}

	@Override
	protected void causeFallDamage(float f) {
		int i = (int) Math.ceil(f - 4.0f);
		if (i > 0) {
			super.causeFallDamage(f);
		}
	}
}
