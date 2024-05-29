package baguchan.better_with_aquatic.entity.render;

import baguchan.better_with_aquatic.entity.EntityFrog;
import net.minecraft.client.render.entity.LivingRenderer;
import org.useless.dragonfly.model.entity.BenchEntityModel;

public class FrogRenderer extends LivingRenderer<EntityFrog> {
	public FrogRenderer(BenchEntityModel orCreateEntityModel, float v) {
		super(orCreateEntityModel, v);
	}
}
