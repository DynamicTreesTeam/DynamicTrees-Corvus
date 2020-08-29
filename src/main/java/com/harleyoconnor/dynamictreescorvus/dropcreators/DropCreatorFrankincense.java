package com.harleyoconnor.dynamictreescorvus.dropcreators;

import com.ferreusveritas.dynamictrees.systems.dropcreators.DropCreator;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.harleyoconnor.dynamictreescorvus.DynamicTreesCorvus;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import party.lemons.corvus.init.CorvusItems;

import java.util.List;
import java.util.Random;

public class DropCreatorFrankincense extends DropCreator {

    private final Item item = CorvusItems.FRANKINCENSE_TEARS;
    private final float rarity = 1f;

    public DropCreatorFrankincense() {
        super(new ResourceLocation(DynamicTreesCorvus.MODID, CorvusItems.FRANKINCENSE_TEARS.getRegistryName().getResourceDomain()));
    }

    @Override
    public List<ItemStack> getLeavesDrop(IBlockAccess access, Species species, BlockPos breakPos, Random random, List<ItemStack> dropList, int fortune) {
        int chance = (int) (200 / this.rarity);
        if (fortune > 0) {
            chance -= 10 << fortune;
            if (chance < 40) chance = 40;
        }

        if (random.nextInt(chance) == 0) dropList.add(new ItemStack(this.item, 1, 0));

        return dropList;
    }

}
