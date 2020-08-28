package com.harleyoconnor.dynamictreescorvus.trees;

import com.ferreusveritas.dynamictrees.api.treedata.ITreePart;
import com.ferreusveritas.dynamictrees.blocks.BlockRooty;
import com.ferreusveritas.dynamictrees.systems.GrowSignal;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.trees.TreeFamily;
import com.harleyoconnor.dynamictreescorvus.DynamicTreesCorvus;
import com.harleyoconnor.dynamictreescorvus.ModContent;
import com.harleyoconnor.dynamictreescorvus.dropcreators.DropCreatorFrankincense;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import party.lemons.corvus.block.effectcandle.BlockEffectCandle;
import party.lemons.corvus.block.effectcandle.CandleEffect;

import java.util.HashMap;
import java.util.Random;

public class TreeFrankincense extends TreeFamily {

    public static Block logBlock = Block.getBlockFromName("corvus:frankinsence_log");
    public static Block leavesBlock = Block.getBlockFromName("corvus:frankinsence_leaves");

    public class SpeciesFrankincense extends Species {

        private final HashMap<BlockPos, BlockPos> cachedCandlePositions = new HashMap<>();

        public SpeciesFrankincense(TreeFamily treeFamily) {
            super(treeFamily.getName(), treeFamily, ModContent.frankincenseLeavesProperties);

            // Set growing parameters.
            this.setBasicGrowingParameters(0.15f, 20.0f, 10, 1, 0.7f);

            // Setup drop creator for frankincense tears.
            this.addDropCreator(new DropCreatorFrankincense());

            // Setup seed.
            this.generateSeed();
            this.setupStandardSeedDropping();
        }

        @Override
        public int maxBranchRadius() {
            return 4;
        }

        @Override
        public boolean isBiomePerfect(Biome biome) {
            return BiomeDictionary.hasType(biome, BiomeDictionary.Type.SAVANNA);
        }

        @Override
        public boolean grow(World world, BlockRooty rootyDirt, BlockPos rootPos, int soilLife, ITreePart treeBase, BlockPos treePos, Random random, boolean natural) {
            // Only grow tree if there is a growth candle in range.
            final int range = 6 + 1;

            if (this.cachedCandlePositions.containsKey(rootPos))
                if (isGrowthCandle(world.getBlockState(this.cachedCandlePositions.get(rootPos)).getBlock()))
                    return super.grow(world, rootyDirt, rootPos, soilLife, treeBase, treePos, random, natural);
                else this.cachedCandlePositions.remove(rootPos);

            for (int x = rootPos.getX() - range; x < rootPos.getX() + range; x++) {
                for (int y = rootPos.getY() - range; y < rootPos.getY() + range; y++) {
                    for (int z = rootPos.getZ() - range; z < rootPos.getZ() + range; z++) {
                        BlockPos blockPos = new BlockPos(x, y, z);
                        Block block = world.getBlockState(blockPos).getBlock();

                        if (isGrowthCandle(block)) {
                            this.cachedCandlePositions.put(rootPos, blockPos);
                            return super.grow(world, rootyDirt, rootPos, soilLife, treeBase, treePos, random, natural);
                        }
                    }
                }
            }

            return true;
        }

        private boolean isGrowthCandle (Block block) {
            if (block instanceof BlockEffectCandle) return ((BlockEffectCandle) block).getEffect() instanceof CandleEffect.EffectGrowth;
            return false;
        }

        @Override
        protected int[] customDirectionManipulation(World world, BlockPos pos, int radius, GrowSignal signal, int[] probMap) {
            // Ensure all values are initially equal.
            for (EnumFacing direction : EnumFacing.VALUES) probMap[direction.getIndex()] = 0;

            // Disallow growing downwards.
            probMap[EnumFacing.DOWN.getIndex()] = 0;

            // Allow growing upwards in the trunk only.
            probMap[EnumFacing.UP.getIndex()] = signal.isInTrunk() ? this.upProbability : 0;

            if (signal.isInTrunk()) {
                // If height of tree is certain, random numbers, set horizontals to double the energy.
                if ((signal.delta.getY() == getRandomNumber(1, 2) || signal.delta.getY() == getRandomNumber(4, 5)))
                    for (EnumFacing direction : EnumFacing.HORIZONTALS)
                        probMap[direction.getIndex()] = getRandomNumber(1, 50) == 1 ? (int) signal.energy * 2 : 0;
                else if (signal.delta.getY() >= 11 && signal.delta.getY() < 16) {
                    for (EnumFacing direction : EnumFacing.HORIZONTALS)
                        probMap[direction.getIndex()] = (int) signal.energy * 2 * signal.delta.getY();
                }
            }

            if (signal.delta.getY() >= 11 && signal.delta.getY() < 16) {
                // Allow branches to grow outwards more near top to spread leaves.
                for (EnumFacing direction : EnumFacing.HORIZONTALS)
                    probMap[direction.getIndex()] = signal.delta.getX() < 2 && signal.delta.getZ() < 2 ? (int) signal.energy * 3 : 0;

                // Allow small chance of branch growing up.
                probMap[EnumFacing.UP.getIndex()] = getRandomNumber(1, 150) == 1 ? 20 : 0;
            } else if (signal.delta.getY() >= 16) {
                // Set all values to zero if tree is 16 high or greater.
                for (EnumFacing direction : EnumFacing.VALUES) probMap[direction.getIndex()] = 0;
            }

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

    @Override
    public ItemStack getPrimitiveLogItemStack(int qty) {
        return new ItemStack(logBlock, qty, 0);
    }

    @Override
    public ItemStack getStick(int qty) {
        return new ItemStack(Items.STICK, qty, 0);
    }

}
