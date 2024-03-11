package baguchan.better_with_aquatic.packet;

import net.minecraft.core.net.handler.NetHandler;
import net.minecraft.core.net.packet.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class SizePacket extends Packet {
	public float width;
	public float height;

	public SizePacket() {
	}

	public SizePacket(float width, float height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public void readPacketData(DataInputStream dataInputStream) throws IOException {
		this.width = dataInputStream.readFloat();
		this.height = dataInputStream.readFloat();
	}

	@Override
	public void writePacketData(DataOutputStream dataOutputStream) throws IOException {
		dataOutputStream.writeFloat(this.width);
		dataOutputStream.writeFloat(this.height);
	}

	@Override
	public void processPacket(NetHandler netHandler) {
		((ISwimPacket) netHandler).betterWithAquatic$handleSize(this);
	}

	@Override
	public int getPacketSize() {
		return 2;
	}
}
