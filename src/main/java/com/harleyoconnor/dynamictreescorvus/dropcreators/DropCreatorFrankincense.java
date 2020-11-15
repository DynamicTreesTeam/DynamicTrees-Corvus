package com.harleyoconnor.dynamictreescorvus.dropcreators;

import com.ferreusveritas.dynamictrees.systems.dropcreators.DropCreator;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.harleyoconnor.dynamictreescorvus.DynamicTreesCorvus;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import party.lemons.corvus.init.CorvusItems;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public final class DropCreatorFrankincense extends DropCreator {

    public DropCreatorFrankincense() {
        super(new ResourceLocation(DynamicTreesCorvus.MOD_ID, CorvusItems.FRANKINCENSE_TEARS.getRegistryName().getResourceDomain()));
    }

    @Override
    public List<ItemStack> getHarvestDrop(World world, Species species, BlockPos leafPos, Random random, List<ItemStack> dropList, int soilLife, int fortune) {
        return this.getDrops(world, species, leafPos, random, dropList, fortune);
    }

    @Override
    public List<ItemStack> getLeavesDrop(IBlockAccess access, Species species, BlockPos breakPos, Random random, List<ItemStack> dropList, int fortune) {
        return this.getDrops(access, species, breakPos, random, dropList, fortune);
    }

    private List<ItemStack> getDrops (IBlockAccess access, Species species, BlockPos leafPos, Random random, List<ItemStack> dropList, int fortune) {
        final float rarity = 1f;

        int chance = (int) (200 / rarity);
        if (fortune > 0) {
            chance -= 10 << fortune;
            if (chance < 40) chance = 40;
        }

        if (random.nextInt(chance) == 0) dropList.add(new ItemStack(Objects.requireNonNull(Item.getByNameOrId("corvus:frankincense_tears"))));

        return dropList;
    }

}
