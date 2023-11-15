package baguchan.better_with_aquatic.mixin.client;

import baguchan.better_with_aquatic.api.ISwimModel;
import baguchan.better_with_aquatic.api.ISwiming;
import baguchan.better_with_aquatic.util.MathUtil;
import net.minecraft.client.render.entity.LivingRenderer;
import net.minecraft.client.render.entity.PlayerRenderer;
import net.minecraft.client.render.model.ModelBase;
import net.minecraft.core.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = PlayerRenderer.class, remap = false)
public abstract class PlayerRendererMixin extends LivingRenderer<EntityPlayer> {
	public PlayerRendererMixin(ModelBase modelbase, float shadowSize) {
		super(modelbase, shadowSize);
	}

	@Inject(method = "renderPlayer", at = @At("HEAD"))
	public void renderPlayer(EntityPlayer entity, double d, double d1, double d2, float yaw, float renderPartialTicks, CallbackInfo ci) {
		if (this.mainModel instanceof ISwimModel) {
			((ISwimModel) this.mainModel).setEntity(entity);
		}
	}

	@Inject(method = "rotateModel(Lnet/minecraft/core/entity/player/EntityPlayer;FFF)V", at = @At("TAIL"))
	protected void preRenderCallback(EntityPlayer entity, float ticksExisted, float headYawOffset, float renderPartialTicks, CallbackInfo ci) {
		if (entity instanceof ISwiming) {
			float swimAmount = ((ISwiming) entity).getSwimAmount(renderPartialTicks);
			if (swimAmount > 0.0F) {
				float f3 = entity.isInWater() ? -90.0F - entity.xRot : -90.0F;
				float f5 = MathUtil.lerp(swimAmount, 0.0F, f3);
				if (((ISwiming) entity).isSwimming() && entity.isInWater()) {
					GL11.glRotatef(f5, 1F, 0, 0);
				}
				GL11.glTranslatef(0.0F, -1.0F, 0.3F);

			}
		}
	}
}
