package baguchan.better_with_aquatic.entity.render;

import baguchan.better_with_aquatic.entity.EntityDrowned;
import net.minecraft.client.render.entity.LivingRenderer;
import useless.dragonfly.model.entity.BenchEntityModel;

public class DrownedRenderer extends LivingRenderer<EntityDrowned> {
	public DrownedRenderer(BenchEntityModel orCreateEntityModel, float v) {
		super(orCreateEntityModel, v);
	}
}
