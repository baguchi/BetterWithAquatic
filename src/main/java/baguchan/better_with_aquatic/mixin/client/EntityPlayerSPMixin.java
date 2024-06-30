package baguchan.better_with_aquatic.mixin.client;

import baguchan.better_with_aquatic.api.ISwiming;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.EntityPlayerSP;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = EntityPlayerSP.class, remap = false)
public abstract class EntityPlayerSPMixin extends EntityPlayer implements ISwiming {


	@Shadow
	protected Minecraft mc;

	public EntityPlayerSPMixin(World world) {
		super(world);
	}


	@Shadow
	private boolean isBlockTranslucent(int i, int j, int k) {
		return false;
	}

	@Inject(method = "checkInTile", at = @At(value = "HEAD"), cancellable = true)
	protected void checkInTile(double d, double d1, double d2, CallbackInfoReturnable<Boolean> cir) {
		if (!this.noPhysics && this.isSwimming()) {

			int i = MathHelper.floor_double(d);
			int j = MathHelper.floor_double(d1);
			int k = MathHelper.floor_double(d2);
			double d3 = d - (double) i;
			double d4 = d2 - (double) k;
			if (this.isBlockTranslucent(i, j, k)) {
				boolean flag = !this.isBlockTranslucent(i - 1, j, k);
				boolean flag1 = !this.isBlockTranslucent(i + 1, j, k);
				boolean flag2 = !this.isBlockTranslucent(i, j, k - 1);
				boolean flag3 = !this.isBlockTranslucent(i, j, k + 1);
				int byte0 = -1;
				double d5 = 9999.0;
				if (flag && d3 < d5) {
					d5 = d3;
					byte0 = 0;
				}
				if (flag1 && 1.0 - d3 < d5) {
					d5 = 1.0 - d3;
					byte0 = 1;
				}
				if (flag2 && d4 < d5) {
					d5 = d4;
					byte0 = 4;
				}
				if (flag3 && 1.0 - d4 < d5) {
					double d6 = 1.0 - d4;
					byte0 = 5;
				}
				float f = 0.1f;
				if (byte0 == 0) {
					this.xd = -f;
				}
				if (byte0 == 1) {
					this.xd = f;
				}
				if (byte0 == 4) {
					this.zd = -f;
				}
				if (byte0 == 5) {
					this.zd = f;
				}
			}
			cir.setReturnValue(false);
		}
	}
}
