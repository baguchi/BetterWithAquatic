package baguchan.better_with_aquatic.entity.render;// Made with Blockbench 4.8.3
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports


import net.minecraft.client.render.model.Cube;
import net.minecraft.client.render.model.ModelBase;
import net.minecraft.core.util.helper.MathHelper;

public class FishModel extends ModelBase {
	private final Cube fish;
	private final Cube tail;
	private final Cube fin_r;
	private final Cube fin_l;

	public FishModel() {

		fish = new Cube(0, 0, 32, 32);
		fish.setRotationPoint(0.0F, 22.0F, 3.0F);
		fish.addBox(-2.0F, -3.0F, -9.0F, 4, 5, 9, 0.0F, false);

		tail = new Cube(10, 23, 32, 32);
		tail.setRotationPoint(0.0F, 22.0F, 3.0F);
		tail.addBox(-1.0F, -3.0F, 0.0F, 2, 5, 4, 0.0F, false);

		fin_r = new Cube(0, 25, 32, 32);
		fin_r.setRotationPoint(-2.0F, 22.5F, -3.0F);
		fin_r.addBox(-1.0F, -1.5F, 0.0F, 1, 2, 4, 0.0F, false);

		fin_l = new Cube(0, 25, 32, 32);
		fin_l.setRotationPoint(2.0F, 22.5F, -3.0F);
		fin_l.mirror = true;
		fin_l.addBox(0.0F, -1.5F, 0.0F, 1, 2, 4, 0.0F, false);
	}

	@Override
	public void render(float limbSwing, float limbYaw, float ticksExisted, float headYaw, float headPitch, float scale) {
		this.setRotationAngles(limbSwing, limbYaw, ticksExisted, headYaw, headPitch, scale);
		fish.render(scale);
		tail.render(scale);
		fin_r.render(scale);
		fin_l.render(scale);
	}

	public void setRotationAngles(float limbSwing, float limbYaw, float ticksExisted, float headYaw, float headPitch, float scale) {
		this.fin_r.rotateAngleY = -0.6F + MathHelper.cos(ticksExisted * 0.5F) * -0.15F;
		this.fin_l.rotateAngleY = 0.6F + MathHelper.cos(ticksExisted * 0.5F) * 0.15F;
		this.tail.rotateAngleY = MathHelper.cos(ticksExisted * 0.5F) * -0.3F;
	}

	public void setRotationAngle(Cube modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
