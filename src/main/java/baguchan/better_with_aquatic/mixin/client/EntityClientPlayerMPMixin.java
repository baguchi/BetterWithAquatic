package baguchan.better_with_aquatic.mixin.client;

import baguchan.better_with_aquatic.api.ISwiming;
import baguchan.better_with_aquatic.packet.SwimPacket;
import net.minecraft.client.entity.player.EntityClientPlayerMP;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EntityClientPlayerMP.class, remap = false)
public abstract class EntityClientPlayerMPMixin extends EntityPlayer implements ISwiming {
	public boolean swimmingOld;

	public EntityClientPlayerMPMixin(World world) {
		super(world);
	}


	@Inject(method = "tick", at = @At("TAIL"))
	public void tick(CallbackInfo ci) {
		if (this.isSwimming() != this.swimmingOld) {
			this.swimmingOld = this.isSwimming();
			EntityClientPlayerMP clientPlayerMP = (EntityClientPlayerMP) (Object) this;
			clientPlayerMP.sendQueue.addToSendQueue(new SwimPacket(this.isSwimming()));
		}
	}
}
