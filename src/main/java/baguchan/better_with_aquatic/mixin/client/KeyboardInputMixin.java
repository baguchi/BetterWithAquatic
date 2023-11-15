package baguchan.better_with_aquatic.mixin.client;

import baguchan.better_with_aquatic.BetterWithAquatic;
import baguchan.better_with_aquatic.api.ISwiming;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.client.option.GameSettings;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = KeyboardInput.class, remap = false)
public class KeyboardInputMixin {

	private int sprintTime = 20;
	private boolean pressedSprint = false;
	@Shadow
	@Final
	private GameSettings gameSettings;
	@Shadow
	@Final
	private boolean[] keys;

	@Inject(method = "tick", at = @At(value = "TAIL"))
	public void tick(EntityPlayer entityplayer, CallbackInfo ci) {

		if (entityplayer instanceof ISwiming) {
			if (entityplayer.isUnderLiquid(Material.water) && !entityplayer.isSneaking()) {
				if (keys[0]) {
					if (this.sprintTime < 9 && !this.pressedSprint) {
						if (BetterWithAquatic.isEnableSwim()) {
							((ISwiming) entityplayer).setSwimming(true);
						}
					} else {
						this.pressedSprint = true;
					}
					this.sprintTime = 0;
				} else {
					if (this.sprintTime < 20) {
						this.sprintTime++;
					}
					this.pressedSprint = false;
				}
			}
		}
	}

}
