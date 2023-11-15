package baguchan.better_with_aquatic.mixin.client;

import baguchan.better_with_aquatic.api.ISwiming;
import baguchan.better_with_aquatic.packet.SwimPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.EntityPlayerSP;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(value = EntityPlayerSP.class, remap = false)
public abstract class EntityPlayerSPMixin extends EntityPlayer implements ISwiming {
	public boolean swimmingOld;

	@Unique
	private final Minecraft mc = Minecraft.getMinecraft(this);

	public EntityPlayerSPMixin(World world) {
		super(world);
	}


	@Override
	public void tick() {
		super.tick();
		if (this.isSwimming() != this.swimmingOld) {
			this.swimmingOld = this.isSwimming();
			if (this.mc.getSendQueue() != null) {
				this.mc.getSendQueue().addToSendQueue(new SwimPacket(this.isSwimming()));
			}
		}
	}
}
