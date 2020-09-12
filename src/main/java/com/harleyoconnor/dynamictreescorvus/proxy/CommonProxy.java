package com.harleyoconnor.dynamictreescorvus.proxy;

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
		disableVanillaTreeGen();

		// Register sapling replacements.
		registerSaplingReplacement(CorvusBlocks.FRANKINSENCE_SAPLING, "frankincense");
	}

	private static void registerSaplingReplacement(final Block saplingBlock, final String speciesName) {
		TreeRegistry.registerSaplingReplacer(saplingBlock.getDefaultState(), TreeRegistry.findSpecies(new ResourceLocation(DynamicTreesCorvus.MODID, speciesName)));
	}

	public void postInit() {
	}

	private static void disableVanillaTreeGen () {
		try {
			final Field field = CorvusTreeGen.class.getDeclaredField("FRANK_TREE_GEN");

			Field modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

			field.setAccessible(true);
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
			e.printStackTrace();
		}
	}
	
}
