package baguchan.better_with_aquatic.entity.render;

import baguchan.better_with_aquatic.entity.EntityAnglerFish;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.entity.LivingRenderer;
import net.minecraft.client.render.model.ModelBase;
import org.lwjgl.opengl.GL11;

public class RenderAnglerFish extends LivingRenderer<EntityAnglerFish> {
	public RenderAnglerFish(ModelBase modelbase, float shadowSize) {
		super(modelbase, shadowSize);
		this.setRenderPassModel(new AnglerFishModel());
	}


	protected boolean setAnglerBrightness(EntityAnglerFish spider, int i, float f) {
		if (i == 0) {
			this.loadTexture("/assets/better_with_aquatic/entity/angler_fish_overlay.png");
			float brightness = spider.getBrightness(1.0f);
			if (Minecraft.getMinecraft((Object) this).fullbright) {
				brightness = 1.0f;
			}
			float f1 = (1.0f - brightness) * 0.5f;
			GL11.glEnable(3042);
			GL11.glDisable(3008);
			GL11.glBlendFunc(770, 771);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, f1);
			return true;
		}
		return false;
	}

	@Override
	protected boolean shouldRenderPass(EntityAnglerFish entity, int renderPass, float renderPartialTicks) {
		return this.setAnglerBrightness(entity, renderPass, renderPartialTicks);
	}
}
