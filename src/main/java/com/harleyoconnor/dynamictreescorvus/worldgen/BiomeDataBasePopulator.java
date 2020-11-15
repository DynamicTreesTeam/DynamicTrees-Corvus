package com.harleyoconnor.dynamictreescorvus.worldgen;

import com.ferreusveritas.dynamictrees.api.worldgen.IBiomeDataBasePopulator;
import com.ferreusveritas.dynamictrees.worldgen.BiomeDataBase;
import com.ferreusveritas.dynamictrees.worldgen.BiomeDataBasePopulatorJson;
import com.harleyoconnor.dynamictreescorvus.DynamicTreesCorvus;
import net.minecraft.util.ResourceLocation;

/**
 * @author Harley O'Connor
 */
public final class BiomeDataBasePopulator implements IBiomeDataBasePopulator {

    public static final String RESOURCEPATH = "worldgen/default.json";

    private final BiomeDataBasePopulatorJson jsonPopulator;

    public BiomeDataBasePopulator () {
        this.jsonPopulator = new BiomeDataBasePopulatorJson(new ResourceLocation(DynamicTreesCorvus.MOD_ID, RESOURCEPATH));
    }

    @Override
    public void populate (BiomeDataBase biomeDataBase) {
        this.jsonPopulator.populate(biomeDataBase);
    }

}
