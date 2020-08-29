package com.harleyoconnor.dynamictreescorvus.proxy;

import com.ferreusveritas.dynamictrees.api.TreeRegistry;
import com.harleyoconnor.dynamictreescorvus.DynamicTreesCorvus;
import com.harleyoconnor.dynamictreescorvus.dropcreators.DropCreatorFrankincense;
import com.harleyoconnor.dynamictreescorvus.growth.CustomCellKits;
import net.minecraft.util.ResourceLocation;

public class CommonProxy {
	
	public void preInit() {
		// Initialise custom cell kits.
		new CustomCellKits();
	}
	
	public void init() {
		// Register frankincense tears drop creator.
		TreeRegistry.findSpecies(new ResourceLocation(DynamicTreesCorvus.MODID, "frankincense")).addDropCreator(new DropCreatorFrankincense());
	}
	
	public void postInit() {
	}
	
}
