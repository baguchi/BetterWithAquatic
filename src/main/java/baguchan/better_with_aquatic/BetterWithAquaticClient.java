package baguchan.better_with_aquatic;

import baguchan.better_with_aquatic.block.ModBlocks;
import baguchan.better_with_aquatic.entity.render.DrownedModel;
import baguchan.better_with_aquatic.entity.render.FrogModel;
import net.minecraft.client.render.block.color.BlockColorCustom;
import net.minecraft.client.render.block.color.BlockColorDispatcher;
import net.minecraft.client.render.colorizer.Colorizers;
import org.useless.dragonfly.helper.ModelHelper;
import org.useless.dragonfly.model.entity.BenchEntityModel;
import turniplabs.halplibe.helper.SoundHelper;
import turniplabs.halplibe.util.ClientStartEntrypoint;

import static baguchan.better_with_aquatic.BetterWithAquatic.MOD_ID;


public class BetterWithAquaticClient implements ClientStartEntrypoint {

	public static final BenchEntityModel modelDrowned = ModelHelper.getOrCreateEntityModel(MOD_ID, "entity/drowned.geo.json", DrownedModel.class);

	public static final BenchEntityModel modelFrog = ModelHelper.getOrCreateEntityModel(MOD_ID, "entity/frog.geo.json", FrogModel.class);


	@Override
	public void beforeClientStart() {
		SoundHelper.addSound(MOD_ID, "mob/drowned/drowned_idle.wav");
		SoundHelper.addSound(MOD_ID, "mob/drowned/drowned_death.wav");
		SoundHelper.addSound(MOD_ID, "mob/drowned/drowned_hurt.wav");
		SoundHelper.addSound(MOD_ID, "mob/drowned/drowned_hurt_extra.wav");

	}

	@Override
	public void afterClientStart() {
		BlockColorDispatcher.getInstance().addDispatch(ModBlocks.sea_grass, new BlockColorCustom(Colorizers.water));
		BlockColorDispatcher.getInstance().addDispatch(ModBlocks.sea_grass_flow, new BlockColorCustom(Colorizers.water));
	}
}
