package com.harleyoconnor.dynamictreescorvus.trees;

import com.ferreusveritas.dynamictrees.systems.GrowSignal;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.trees.TreeFamily;
import com.harleyoconnor.dynamictreescorvus.DynamicTreesCorvus;
import com.harleyoconnor.dynamictreescorvus.ModContent;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

public class TreeFrankincense extends TreeFamily {

    public static Block logBlock = Block.getBlockFromName("corvus:frankinsence_log");
    public static Block leavesBlock = Block.getBlockFromName("corvus:frankinsence_leaves");

    public class SpeciesFrankincense extends Species {

        private final double maxBranchHeight = (Math.random() * (13 - 9 + 1) + 9);

        public SpeciesFrankincense(TreeFamily treeFamily) {
            super(treeFamily.getName(), treeFamily, ModContent.frankincenseLeavesProperties);

            this.setBasicGrowingParameters(0.15f, 12.0f, 10, 1, 0.7f);

            this.setupStandardSeedDropping();
            this.generateSeed();
        }

        @Override
        public int maxBranchRadius() {
            return 7;
        }

        @Override
        public boolean isBiomePerfect(Biome biome) {
            return BiomeDictionary.hasType(biome, BiomeDictionary.Type.SAVANNA);
        }

        /** Define custom growth logic for Frankincense tree.
         * @param probMap - Defines probabilities for the signal for each face of the block called upon.
         * **/
        @Override
        protected int[] customDirectionManipulation(World world, BlockPos pos, int radius, GrowSignal signal, int[] probMap) {
            // Disallow branches growing downwards.
            probMap[EnumFacing.DOWN.getIndex()] = 0;

            // TODO: Remake logic.

            /*
            //Amplify cardinal directions to encourage spread the higher we get
            float energyRatio = signal.delta.getY() / this.getEnergy(world, pos);
            float spreadPush = energyRatio * 2;
            spreadPush = spreadPush < 1.0f ? 1.0f : spreadPush;

            for (EnumFacing dir: EnumFacing.HORIZONTALS) probMap[dir.ordinal()] *= spreadPush;

            // Ensure that the branch gets out of the trunk at least two blocks so it won't interfere with new side branches at the same level
            if (signal.numTurns == 1 && signal.delta.distanceSq(0, signal.delta.getY(), 0) == 1.0 ) for (EnumFacing dir: EnumFacing.HORIZONTALS) if (signal.dir != dir) probMap[dir.ordinal()] = 0;

            // Disallow too much turning on branches not on trunk.
            if (signal.numSteps > 2 && !signal.isInTrunk()) for (EnumFacing dir : EnumFacing.HORIZONTALS) probMap[dir.getIndex()] = 0;

            // Disallow creation of new branches if steps is more than 3 and less than 9.
            if (((signal.numSteps == (Math.random() * (3 - 1 + 1) + 1) || signal.numSteps == (Math.random() * (5 - 3 + 1) + 3)) || (signal.numSteps > 5 && signal.numSteps < this.maxBranchHeight)) && signal.isInTrunk()) {
                probMap[EnumFacing.UP.getIndex()] = 1;
                for (EnumFacing dir : EnumFacing.VALUES) probMap[dir.getIndex()] = 0;
            }

            // Disallow branches growing upwards after having turned out of the trunk.
            if (!signal.isInTrunk()) {
                if (signal.numSteps < this.maxBranchHeight - 2) {
                    probMap[EnumFacing.UP.getIndex()] = 0;
                }
                probMap[signal.dir.ordinal()] *= 0.35; // Help create "zags" in horizontal branches.
            }

            if (signal.isInTrunk()) probMap[EnumFacing.UP.getIndex()] = 1;*/

            return probMap;
        }

    }

    public TreeFrankincense() {
        super(new ResourceLocation(DynamicTreesCorvus.MODID, "frankincense"));

        this.setPrimitiveLog(logBlock.getDefaultState(), new ItemStack(logBlock, 1, 0));
        ModContent.frankincenseLeavesProperties.setTree(this);

        this.addConnectableVanillaLeaves((state) -> state.getBlock() == leavesBlock);
    }

    @Override
    public void createSpecies() {
        this.setCommonSpecies(new SpeciesFrankincense(this));
    }

    @Override
    public boolean autoCreateBranch() {
        return true;
    }

}
