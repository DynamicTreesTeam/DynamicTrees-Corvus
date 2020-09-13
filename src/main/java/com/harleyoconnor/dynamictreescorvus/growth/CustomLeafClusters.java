package com.harleyoconnor.dynamictreescorvus.growth;

import com.ferreusveritas.dynamictrees.util.SimpleVoxmap;
import net.minecraft.util.math.BlockPos;

public final class CustomLeafClusters {

    public static final SimpleVoxmap frankincense = new SimpleVoxmap(7, 1, 7, new byte[] {

            //Layer 0(Bottom)
            0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 1, 0, 0, 0,
            0, 0, 1, 2, 1, 0, 0,
            0, 1, 2, 0, 2, 1, 0,
            0, 0, 1, 2, 1, 0, 0,
            0, 0, 0, 1, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0,

            //Layer 1 (Top)
            0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 1, 0, 0, 0,
            0, 0, 1, 1, 1, 0, 0,
            0, 0, 0, 1, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0

    }).setCenter(new BlockPos(2, 0, 2));

}
