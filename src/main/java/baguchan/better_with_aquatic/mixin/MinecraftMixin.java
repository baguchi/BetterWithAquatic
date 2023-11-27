package baguchan.better_with_aquatic.mixin;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Minecraft.class, remap = false, priority = 42069000)
public class MinecraftMixin {
	@Shadow
	private static Minecraft theMinecraft;

	@Inject(method = "getMinecraft(Ljava/lang/Class;)Lnet/minecraft/client/Minecraft;", remap = false, at = @At("HEAD"), cancellable = true)
	private static void getMinecraft(Class<?> caller, CallbackInfoReturnable<Minecraft> cir) {
		cir.setReturnValue(theMinecraft);
	}
}
