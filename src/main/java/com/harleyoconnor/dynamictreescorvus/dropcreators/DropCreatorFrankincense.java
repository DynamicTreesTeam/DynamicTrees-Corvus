package com.harleyoconnor.dynamictreescorvus.dropcreators;

import com.ferreusveritas.dynamictrees.systems.dropcreators.DropCreator;
import com.ferreusveritas.dynamictrees.trees.Species;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import party.lemons.corvus.init.CorvusItems;

import java.util.List;
import java.util.Random;

public class DropCreatorFrankincense extends DropCreator {

    private final Item item = CorvusItems.FRANKINCENSE_TEARS;
    private final float rarity = .5f;

    public DropCreatorFrankincense () {
        super(CorvusItems.FRANKINCENSE_TEARS.getRegistryName());
    }

    @Override
    public List<ItemStack> getLeavesDrop(IBlockAccess access, Species species, BlockPos breakPos, Random random, List<ItemStack> dropList, int fortune) {
        return getLeafDrops(access, species, breakPos, random, dropList, fortune, 4);
    }

    @Override
    public List<ItemStack> getHarvestDrop(World world, Species species, BlockPos leafPos, Random random, List<ItemStack> dropList, int soilLife, int fortune) {
        return getLeafDrops(world, species, leafPos, random, dropList, fortune, 2);
    }

    protected List<ItemStack> getLeafDrops(IBlockAccess access, Species species, BlockPos leafPos, Random random, List<ItemStack> dropList, int fortune, int baseChance) {
        int chance = (int) (baseChance / this.rarity);

        if (fortune > 0) {
            chance -= 10 << fortune;
            if (chance < 40) chance = 40;
        }

        if (random.nextInt(chance) == 0) dropList.add(new ItemStack(this.item, 1, 0));

        return dropList;
    }

}
