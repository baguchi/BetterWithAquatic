package baguchan.better_with_aquatic.mixin.client;

import baguchan.better_with_aquatic.BetterWithAquatic;
import baguchan.better_with_aquatic.api.ISwiming;
import net.minecraft.client.Minecraft;
import net.minecraft.client.input.PlayerInput;
import net.minecraft.client.option.GameSettings;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = PlayerInput.class, remap = false)
public class PlayerInputMixin {

	protected int sprintTimer;
	private boolean pressedSprint = false;
	@Shadow
	@Final
	private GameSettings gameSettings;
	@Shadow
	private boolean pressedForward;
	@Shadow
	@Final
	public Minecraft mc;

	@Shadow
	public float moveForward;

	@Inject(method = "tick", at = @At(value = "HEAD"))
	public void tickHead(EntityPlayer entityplayer, CallbackInfo ci) {
		pressedSprint = moveForward >= 0.8F;
	}

	@Inject(method = "tick", at = @At(value = "TAIL"))
	public void tick(EntityPlayer entityplayer, CallbackInfo ci) {
		if (this.sprintTimer > 0) {
			--this.sprintTimer;
		}
		if (entityplayer instanceof ISwiming) {
			if (BetterWithAquatic.isEnableSwim() && entityplayer.isUnderLiquid(Material.water) && !entityplayer.isSneaking()) {
				boolean canSprint = !entityplayer.noPhysics;
				if (entityplayer.onGround && !pressedSprint && moveForward >= 0.8F && !((ISwiming) entityplayer).isSwimming() && canSprint) {
					if (this.sprintTimer == 0) {
						this.sprintTimer = 7;
					} else {
						((ISwiming) entityplayer).setSwimming(true);
						this.sprintTimer = 0;
					}
				}

				if (mc.gameSettings.keySprint.isPressed()) {
					if (this.pressedSprint) {
						((ISwiming) entityplayer).setSwimming(true);
					}
				} else {
					if (this.pressedSprint) {
						//this.setSwimming(false);
						this.pressedSprint = false;
					}
				}
			}
		}
	}
}
