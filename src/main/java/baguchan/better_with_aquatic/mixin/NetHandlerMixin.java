package baguchan.better_with_aquatic.mixin;

import baguchan.better_with_aquatic.packet.ISwimPacket;
import baguchan.better_with_aquatic.packet.SwimPacket;
import net.minecraft.core.net.handler.NetHandler;
import net.minecraft.core.net.packet.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = NetHandler.class, remap = false)
public class NetHandlerMixin implements ISwimPacket {

	public void betterWithAquatic$handleSwim(SwimPacket packet) {
		this.handleInvalidPacket(packet);
	}

	@Shadow
	public void handleInvalidPacket(Packet packet) {
	}
}
