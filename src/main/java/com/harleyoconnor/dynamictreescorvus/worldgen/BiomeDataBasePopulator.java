package com.harleyoconnor.dynamictreescorvus.worldgen;

import com.ferreusveritas.dynamictrees.api.TreeRegistry;
import com.ferreusveritas.dynamictrees.api.worldgen.BiomePropertySelectors;
import com.ferreusveritas.dynamictrees.api.worldgen.IBiomeDataBasePopulator;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.worldgen.BiomeDataBase;
import com.harleyoconnor.dynamictreescorvus.DynamicTreesCorvus;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

public class BiomeDataBasePopulator implements IBiomeDataBasePopulator {

    public void populate(BiomeDataBase dbase) {
        Species frankincense = TreeRegistry.findSpecies(new ResourceLocation(DynamicTreesCorvus.MODID, "frankincense"));

        Biome.REGISTRY.forEach(biome -> {
            if (!BiomeDictionary.hasType(biome, BiomeDictionary.Type.SAVANNA)) return;

            BiomePropertySelectors.RandomSpeciesSelector selector = new BiomePropertySelectors.RandomSpeciesSelector().add(10).add(frankincense, 1);
            dbase.setSpeciesSelector(biome, selector, BiomeDataBase.Operation.SPLICE_BEFORE);

            DynamicTreesCorvus.logger.info("Added Frankincense species to biome database for Savannah.");
        });
    }
    
}

