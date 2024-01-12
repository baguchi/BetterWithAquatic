package baguchan.better_with_aquatic.packet;

import net.minecraft.core.net.handler.NetHandler;
import net.minecraft.core.net.packet.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class SwimPacket extends Packet {
	public boolean swim;

	public SwimPacket() {
	}

	public SwimPacket(boolean swim) {
		this.swim = swim;
	}

	@Override
	public void readPacketData(DataInputStream dataInputStream) throws IOException {
		this.swim = dataInputStream.readBoolean();
	}

	@Override
	public void writePacketData(DataOutputStream dataOutputStream) throws IOException {
		dataOutputStream.writeBoolean(this.swim);
	}

	@Override
	public void processPacket(NetHandler netHandler) {
		((ISwimPacket) netHandler).betterWithAquatic$handleSwim(this);
	}

	@Override
	public int getPacketSize() {
		return 1;
	}
}
