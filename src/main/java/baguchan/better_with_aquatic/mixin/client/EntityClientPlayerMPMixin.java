package baguchan.better_with_aquatic.mixin.client;

import baguchan.better_with_aquatic.api.ISwiming;
import baguchan.better_with_aquatic.packet.SwimPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.EntityClientPlayerMP;
import net.minecraft.client.entity.player.EntityPlayerSP;
import net.minecraft.client.net.handler.NetClientHandler;
import net.minecraft.core.player.Session;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EntityClientPlayerMP.class, remap = false)
public abstract class EntityClientPlayerMPMixin extends EntityPlayerSP implements ISwiming {
	@Unique
	public boolean swimmingOld;
	@Unique
	public AABB oldAABB;
	public float oldOffset;
	public float oldWidth;
	public float oldHeight;

	public EntityClientPlayerMPMixin(Minecraft minecraft, World world, Session session, NetClientHandler netclienthandler) {
		super(minecraft, world, session, 0);
	}

	@Inject(method = "func_4056_N", at = @At("HEAD"))
	public void func_4056_N(CallbackInfo callbackInfo) {
		if (this.isSwimming() != this.swimmingOld) {
			this.swimmingOld = this.isSwimming();
			EntityClientPlayerMP clientPlayerMP = (EntityClientPlayerMP) (Object) this;
			clientPlayerMP.sendQueue.addToSendQueue(new SwimPacket(this.isSwimming()));
		}
	}

}
