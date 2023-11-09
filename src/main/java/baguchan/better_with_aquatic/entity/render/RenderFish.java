package baguchan.better_with_aquatic.entity.render;

import baguchan.better_with_aquatic.entity.EntityBaseFish;
import net.minecraft.client.render.entity.LivingRenderer;
import net.minecraft.client.render.model.ModelBase;

public class RenderFish extends LivingRenderer<EntityBaseFish> {
	public RenderFish(ModelBase modelbase, float shadowSize) {
		super(modelbase, shadowSize);
	}
}
