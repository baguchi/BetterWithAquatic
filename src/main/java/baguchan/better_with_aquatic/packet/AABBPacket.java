package baguchan.better_with_aquatic.packet;

import net.minecraft.core.net.handler.NetHandler;
import net.minecraft.core.net.packet.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class AABBPacket extends Packet {
	public double aabbMinX;
	public double aabbMinY;
	public double aabbMinZ;
	public double aabbMaxX;
	public double aabbMaxY;
	public double aabbMaxZ;
	public float offset;

	public AABBPacket() {
	}

	public AABBPacket(double minX, double minY, double minZ, double maxX, double maxY, double maxZ, float offset) {
		this.aabbMinX = minX;
		this.aabbMinY = minY;
		this.aabbMinZ = minZ;
		this.aabbMaxX = maxX;
		this.aabbMaxY = maxY;
		this.aabbMaxZ = maxZ;
		this.offset = offset;
	}

	@Override
	public void readPacketData(DataInputStream dataInputStream) throws IOException {
		this.aabbMinX = dataInputStream.readDouble();
		this.aabbMinY = dataInputStream.readDouble();
		this.aabbMinZ = dataInputStream.readDouble();
		this.aabbMaxX = dataInputStream.readDouble();
		this.aabbMaxY = dataInputStream.readDouble();
		this.aabbMaxZ = dataInputStream.readDouble();
		this.offset = dataInputStream.readFloat();
	}

	@Override
	public void writePacketData(DataOutputStream dataOutputStream) throws IOException {
		dataOutputStream.writeDouble(this.aabbMinX);
		dataOutputStream.writeDouble(this.aabbMinY);
		dataOutputStream.writeDouble(this.aabbMinZ);
		dataOutputStream.writeDouble(this.aabbMaxX);
		dataOutputStream.writeDouble(this.aabbMaxY);
		dataOutputStream.writeDouble(this.aabbMaxZ);
		dataOutputStream.writeFloat(this.offset);
	}

	@Override
	public void processPacket(NetHandler netHandler) {
		((ISwimPacket) netHandler).betterWithAquatic$handleAABB(this);
	}

	@Override
	public int getPacketSize() {
		return 7;
	}
}
