package baguchan.better_with_aquatic.mixin;

import baguchan.better_with_aquatic.api.ISwiming;
import baguchan.better_with_aquatic.packet.ISwimPacket;
import baguchan.better_with_aquatic.packet.SwimPacket;
import net.minecraft.server.entity.player.EntityPlayerMP;
import net.minecraft.server.net.handler.NetServerHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = NetServerHandler.class, remap = false)
public class NetServerHandlerMixin implements ISwimPacket {
	@Shadow
	private EntityPlayerMP playerEntity;

	@Override
	public void handleSwim(SwimPacket packet) {
		if (playerEntity instanceof ISwiming) {
			((ISwiming) playerEntity).setSwimming(packet.swim);
		}


	}
}
