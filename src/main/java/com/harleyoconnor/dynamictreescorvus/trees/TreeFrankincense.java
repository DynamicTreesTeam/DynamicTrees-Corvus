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

        public SpeciesFrankincense(TreeFamily treeFamily) {
            super(treeFamily.getName(), treeFamily, ModContent.frankincenseLeavesProperties);

            // Set growing parameters.
            this.setBasicGrowingParameters(0.15f, 20.0f, 10, 1, 0.7f);

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

        @Override
        protected int[] customDirectionManipulation(World world, BlockPos pos, int radius, GrowSignal signal, int[] probMap) {
            // Disallow growing downwards.
            probMap[EnumFacing.DOWN.getIndex()] = 0;

            probMap[EnumFacing.UP.getIndex()] = signal.isInTrunk() ? this.upProbability : 0;

            // Custom direction manipulation coming soon.

            return probMap;
        }

        private int getRandomNumber (int min, int max) {
            return new Random().nextInt((max + 1) - min) + min;
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
