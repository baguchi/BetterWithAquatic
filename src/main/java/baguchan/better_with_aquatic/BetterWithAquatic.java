package baguchan.better_with_aquatic;

import baguchan.better_with_aquatic.crafting.ModCraftings;
import baguchan.better_with_aquatic.entity.DrownedModel;
import baguchan.better_with_aquatic.entity.EntityAnglerFish;
import baguchan.better_with_aquatic.entity.EntityDrowned;
import baguchan.better_with_aquatic.entity.EntityFish;
import baguchan.better_with_aquatic.entity.render.*;
import baguchan.better_with_aquatic.packet.SwimPacket;
import net.fabricmc.api.ModInitializer;
import turniplabs.halplibe.helper.EntityHelper;
import turniplabs.halplibe.helper.NetworkHelper;
import useless.dragonfly.helper.ModelHelper;


public class BetterWithAquatic implements ModInitializer {

	public static final String MOD_ID = "better_with_aquatic";

	@Override
	public void onInitialize() {
		ModCraftings.register();
		EntityHelper.createEntity(EntityFish.class, new RenderFish(new FishModel(), 0.3F), BetterWithAquaticPreLaunch.entityID, "Fish");
		EntityHelper.createEntity(EntityAnglerFish.class, new RenderAnglerFish(new AnglerFishModel(), 0.4F), BetterWithAquaticPreLaunch.entityID + 1, "AnglerFish");

		EntityHelper.createEntity(EntityDrowned.class, new DrownedRenderer(ModelHelper.getOrCreateEntityModel(MOD_ID, "entity/drowned.geo.json", DrownedModel.class), 0.5f), BetterWithAquaticPreLaunch.entityID + 2, "Drowned");


		NetworkHelper.register(SwimPacket.class, true, false);

	}
}
