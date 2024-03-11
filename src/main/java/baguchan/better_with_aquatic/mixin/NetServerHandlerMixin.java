package baguchan.better_with_aquatic.mixin;

import baguchan.better_with_aquatic.api.ISwiming;
import baguchan.better_with_aquatic.packet.AABBPacket;
import baguchan.better_with_aquatic.packet.ISwimPacket;
import baguchan.better_with_aquatic.packet.SizePacket;
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
	public void betterWithAquatic$handleSwim(SwimPacket packet) {
		if (playerEntity instanceof ISwiming) {
			((ISwiming) playerEntity).setSwimming(packet.swim);
		}

	}

	@Override
	public void betterWithAquatic$handleAABB(AABBPacket packet) {
		playerEntity.bb.setBounds(packet.aabbMinX, packet.aabbMinY, packet.aabbMinZ, packet.aabbMaxX, packet.aabbMaxY, packet.aabbMaxZ);
		playerEntity.heightOffset = packet.offset;
	}

	@Override
	public void betterWithAquatic$handleSize(SizePacket packet) {
		playerEntity.bbWidth = packet.width;
		playerEntity.bbHeight = packet.height;
	}
}
