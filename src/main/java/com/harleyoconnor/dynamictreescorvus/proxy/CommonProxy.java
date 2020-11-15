package com.harleyoconnor.dynamictreescorvus.proxy;

import com.ferreusveritas.dynamictrees.ModConfigs;
import com.ferreusveritas.dynamictrees.api.TreeRegistry;
import com.harleyoconnor.dynamictreescorvus.DynamicTreesCorvus;
import com.harleyoconnor.dynamictreescorvus.growth.CustomCellKits;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import party.lemons.corvus.gen.CorvusTreeGen;
import party.lemons.corvus.init.CorvusBlocks;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Random;

public class CommonProxy {
	
	public void preInit() {
		// Initialise custom cell kits.
		new CustomCellKits();
	}
	
	public void init() {
		// Disable Corvus tree generation.
		if (ModConfigs.worldGen) disableRegularTreeGen();

		// Register sapling replacements.
		registerSaplingReplacement(CorvusBlocks.FRANKINSENCE_SAPLING, "frankincense");
	}

	private static void registerSaplingReplacement(final Block saplingBlock, final String speciesName) {
		TreeRegistry.registerSaplingReplacer(saplingBlock.getDefaultState(), TreeRegistry.findSpecies(new ResourceLocation(DynamicTreesCorvus.MOD_ID, speciesName)));
	}

	public void postInit() {
	}

	private static void disableRegularTreeGen () {
		try {
			// Get field which stored tree generator for frankincense.
			final Field field = CorvusTreeGen.class.getDeclaredField("FRANK_TREE_GEN");

			// Allow access to 'final' field.
			Field modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

			field.setAccessible(true); // Allow access to private field.

			// Set field as new generator that does nothing when called.
			field.set(CorvusTreeGen.class, new WorldGenerator() {
				@Override
				public boolean generate(World worldIn, Random rand, BlockPos position) {
					return true;
				}

				public boolean func_180709_b(Object world, Object rand, Object pos) {
					return true;
				}
			});
		} catch (NoSuchFieldException | IllegalAccessException e) {
			// Log error - just in case.
			DynamicTreesCorvus.logger.error("An unexpected error occurred whilst trying to disable regular frankincense tree generation. Below is a detailed stacktrace, please report this on our issue tracker: https://github.com/Harleyoc1/DynamicTreesCorvus/issues.");
			e.printStackTrace();
		}
	}
	
}
