package baguchan.better_with_aquatic.entity.render;// Made with Blockbench 4.8.3
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports


import net.minecraft.client.render.model.Cube;
import net.minecraft.client.render.model.ModelBase;
import net.minecraft.core.util.helper.MathHelper;

public class AnglerFishModel extends ModelBase {
	private Cube fish;
	private Cube tail;
	private Cube fin_r;
	private Cube fin_l;
	private Cube mouth_down;
	private Cube mouth_up;
	private Cube things;
	private Cube orb;

	public AnglerFishModel() {
		fish = new Cube(0, 0);
		fish.setRotationPoint(0.0F, 22.0F, 6.0F);
		fish.addBox(-3.0F, -5.0F, -9.0F, 6, 7, 9, 0.0F, false);

		tail = new Cube(10, 23);
		tail.setRotationPoint(0.0F, 22.0F, 6.0F);
		tail.addBox(-1.0F, -3.0F, 0.0F, 2, 5, 4, 0.0F, false);

		fin_r = new Cube(0, 25);
		fin_r.setRotationPoint(-3.0F, 22.5F, -3.0F);
		fin_r.addBox(-1.0F, -1.5F, 0.0F, 1, 3, 4, 0.0F, false);

		fin_l = new Cube(0, 25);
		fin_l.setRotationPoint(3.0F, 22.5F, -1.0F);
		fin_l.addBox(0.0F, -1.5F, 0.0F, 1, 3, 4, 0.0F, true);

		mouth_down = new Cube(30, 9);
		mouth_down.setRotationPoint(0.0F, 22.0F, -3.0F);
		mouth_down.addBox(-3.5F, 0.0F, -4.0F, 7, 2, 4, 0.0F, false);

		mouth_up = new Cube(21, 0);
		mouth_up.setRotationPoint(0.0F, 22.0F, -3.0F);
		mouth_up.addBox(-3.0F, -5.0F, -4.0F, 6, 5, 4, 0.0F, false);

		things = new Cube(41, 1);
		things.setRotationPoint(0.0F, 22.0F, -3.0F);

		things.setRotationPoint(0.0F, 22.0F, -3.0F);
		setRotationAngle(things, -0.48F, 0.0F, 0.0F);
		things.addBox(-0.5F, -1.0F, -6.0F, 1, 1, 5, 0.0F, false);

		orb = new Cube(48, 2);
		orb.setRotationPoint(0.0F, 22.0F, -3.0F);
		setRotationAngle(orb, -0.48F, 0.0F, 0.0F);
		orb.addBox(-1.0F, -1.0F, -8.0F, 2, 2, 2, 0.0F, false);
	}

	@Override
	public void render(float limbSwing, float limbYaw, float ticksExisted, float headYaw, float headPitch, float scale) {
		this.setRotationAngles(limbSwing, limbYaw, ticksExisted, headYaw, headPitch, scale);
		fish.render(scale);
		tail.render(scale);
		fin_r.render(scale);
		fin_l.render(scale);
		mouth_down.render(scale);
		mouth_up.render(scale);
		things.render(scale);
		orb.render(scale);
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
