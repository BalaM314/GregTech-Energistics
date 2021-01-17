package com.soliddowant.gregtechenergistics;

import java.util.function.BiFunction;

import gregtech.api.GTValues;
import gregtech.api.cover.CoverBehavior;
import gregtech.api.cover.CoverDefinition;
import gregtech.api.cover.ICoverable;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.common.items.behaviors.CoverPlaceBehavior;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

public class CoverBehaviors {
	public static final int startingCoverId = 40;
	public static void init() {
		GregTechEnergisticsMod.logger.info("Registering cover behaviors...");
		registerStockerCover(0, "ae2.interface.lv", MetaItems.AE2_STOCKER_LV, GTValues.LV, 1280);
		registerStockerCover(1, "ae2.interface.mv", MetaItems.AE2_STOCKER_MV, GTValues.MV, 5120);
		registerStockerCover(2, "ae2.interface.hv", MetaItems.AE2_STOCKER_HV, GTValues.HV, 20480);
		registerStockerCover(3, "ae2.interface.ev", MetaItems.AE2_STOCKER_EV, GTValues.EV, 81920);
		registerStockerCover(4, "ae2.interface.iv", MetaItems.AE2_STOCKER_IV, GTValues.IV, 327680);
		registerStockerCover(5, "ae2.interface.luv", MetaItems.AE2_STOCKER_LUV, GTValues.LuV, 1310720);
		registerStockerCover(6, "ae2.interface.zpm", MetaItems.AE2_STOCKER_ZPM, GTValues.ZPM, 5242880);
		registerStockerCover(7, "ae2.interface.uv", MetaItems.AE2_STOCKER_UV, GTValues.UV, 20971520);

		registerBehavior(8, new ResourceLocation(GregTechEnergisticsMod.MODID, "machine.status"), MetaItems.MACHINE_STATUS,
				CoverMachineStatus::new);
	}

	protected static void registerStockerCover(int idOffset, String resourcePath, MetaItem<?>.MetaValueItem placerItem,
											   int tier, int maxStockCount) {
		registerBehavior(idOffset, new ResourceLocation(GregTechEnergisticsMod.MODID, resourcePath), placerItem,
				(tile, side) -> new CoverAE2Stocker(tile, side, tier, maxStockCount));
	}

	public static void registerBehavior(int idOffset, ResourceLocation coverId,
			MetaItem<?>.MetaValueItem placerItem, BiFunction<ICoverable, EnumFacing, CoverBehavior> behaviorCreator) {
		CoverDefinition coverDefinition = new CoverDefinition(coverId, behaviorCreator, placerItem.getStackForm());
		CoverDefinition.registerCover(startingCoverId + idOffset, coverDefinition);
		//noinspection deprecation // Using deprecation fields allows for compatibility with older GTCE versions
		placerItem.addStats(new CoverPlaceBehavior(coverDefinition));
	}
}
