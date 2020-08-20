package com.harleyoconnor.dynamictreescorvus.trees;

import com.ferreusveritas.dynamictrees.api.treedata.ITreePart;
import com.ferreusveritas.dynamictrees.blocks.BlockRooty;
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
import party.lemons.corvus.block.effectcandle.BlockEffectCandle;
import party.lemons.corvus.block.effectcandle.CandleEffect;

import java.util.Random;

public class TreeFrankincense extends TreeFamily {

    public static Block logBlock = Block.getBlockFromName("corvus:frankinsence_log");
    public static Block leavesBlock = Block.getBlockFromName("corvus:frankinsence_leaves");

    public class SpeciesFrankincense extends Species {

        private boolean growCandleSignal = false;

        public SpeciesFrankincense(TreeFamily treeFamily) {
            super(treeFamily.getName(), treeFamily, ModContent.frankincenseLeavesProperties);

            // Set growing parameters.
            this.setBasicGrowingParameters(0.15f, 10.0f, 10, 1, 0.7f);

            // Setup seed.
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

        @Override
        public boolean grow(World world, BlockRooty rootyDirt, BlockPos rootPos, int soilLife, ITreePart treeBase, BlockPos treePos, Random random, boolean natural) {
            // Only grow tree if there is a growth candle in range.
            final int range = 6 + 1;

            for (int x = rootPos.getX() - range; x < rootPos.getX() + range; x++) {
                for (int y = rootPos.getY() - range; y < rootPos.getY() + range; y++) {
                    for (int z = rootPos.getZ() - range; z < rootPos.getZ() + range; z++) {
                        Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();

                        if (block instanceof BlockEffectCandle) {
                            if (((BlockEffectCandle) block).getEffect() instanceof CandleEffect.EffectGrowth) {
                                return super.grow(world, rootyDirt, rootPos, soilLife, treeBase, treePos, random, natural);
                            }
                        }
                    }
                }
            }

            return true;
        }

        /** Define custom growth logic for Frankincense tree.
         * @param probMap - Defines probabilities for the signal for each face of the block called upon.
         * **/
        @Override
        protected int[] customDirectionManipulation(World world, BlockPos pos, int radius, GrowSignal signal, int[] probMap) {
            for (EnumFacing dir : EnumFacing.VALUES) probMap[dir.getIndex()] = 2;

            // Generate first, second, and third branch heights.
            final double firstBranchHeight = (Math.random() * (3 - 1 + 1) + 1);
            final double secondBranchHeight = (Math.random() * (5 - 3 + 1) + 3);
            final double thirdBranchHeight = (Math.random() * (13 - 9 + 1) + 9);

            // Disallow branches growing downwards.
            probMap[EnumFacing.DOWN.getIndex()] = 0;

            // TODO: Remake logic.

            // Disallow branches growing upwards after having turned out of the trunk.
            if (!signal.isInTrunk()) probMap[EnumFacing.UP.getIndex()] = 0;

            float energyRatio = signal.delta.getY() / this.getEnergy(world, pos);
            float spreadPush = energyRatio * 2;
            spreadPush = spreadPush < 1.0f ? 1.0f : spreadPush;

            for (EnumFacing dir: EnumFacing.HORIZONTALS) probMap[dir.ordinal()] *= spreadPush;

            // Generate new branch off trunk if the number of steps is first branch height, second branch height, or third branch height.
            if (signal.delta.getY() != firstBranchHeight && signal.delta.getY() != secondBranchHeight && signal.delta.getY() != thirdBranchHeight && signal.isInTrunk()) {
                for (EnumFacing dir : EnumFacing.HORIZONTALS) probMap[dir.getIndex()] = 0;
                probMap[EnumFacing.UP.getIndex()] = 1;
            }

            // Stop branches growing more than one block away from branch.
            if (signal.numTurns == 1 && signal.delta.distanceSq(0, signal.delta.getY(), 0) == 1.0) for (EnumFacing dir : EnumFacing.HORIZONTALS) if (signal.dir != dir) probMap[dir.getIndex()] = 0;

            System.out.println("Turns: " + signal.numTurns + " Steps: " + signal.numSteps + " Delta Y: " + signal.delta.getY() + " Down: " + probMap[EnumFacing.DOWN.getIndex()] + " Up: " + probMap[EnumFacing.DOWN.getIndex()] + " North: " + probMap[EnumFacing.NORTH.getIndex()] + " South: " + probMap[EnumFacing.SOUTH.getIndex()] + " West: " + probMap[EnumFacing.WEST.getIndex()] + " East: " + probMap[EnumFacing.EAST.getIndex()]);

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
