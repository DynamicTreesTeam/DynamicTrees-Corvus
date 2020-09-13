package com.harleyoconnor.dynamictreescorvus.growth;

import com.ferreusveritas.dynamictrees.ModConstants;
import com.ferreusveritas.dynamictrees.api.TreeRegistry;
import com.ferreusveritas.dynamictrees.api.cells.CellNull;
import com.ferreusveritas.dynamictrees.api.cells.ICell;
import com.ferreusveritas.dynamictrees.api.cells.ICellKit;
import com.ferreusveritas.dynamictrees.api.cells.ICellSolver;
import com.ferreusveritas.dynamictrees.cells.CellAcaciaLeaf;
import com.ferreusveritas.dynamictrees.cells.CellKits;
import com.ferreusveritas.dynamictrees.cells.LeafClusters;
import com.ferreusveritas.dynamictrees.util.SimpleVoxmap;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

public final class CustomCellKits {

    public CustomCellKits() {
        TreeRegistry.registerCellKit(new ResourceLocation(ModConstants.MODID, "frankincense"), this.frankincenseCellKit);
    }

    private final ICellKit frankincenseCellKit = new ICellKit() {

        private final ICell frankincenseBranch = new ICell() {
            @Override
            public int getValue() {
                return 3;
            }

            final int[] map = {0, 3, 3, 3, 3, 3};

            @Override
            public int getValueFromSide(EnumFacing side) {
                return map[side.ordinal()];
            }
        };

        private final ICell[] acaciaLeafCells = {
                CellNull.NULLCELL,
                new CellAcaciaLeaf(1),
                new CellAcaciaLeaf(2),
                new CellAcaciaLeaf(3),
                new CellAcaciaLeaf(4)
        };

        private final CellKits.BasicSolver frankincenseSolver = new CellKits.BasicSolver(new short[]{0x0514, 0x0413, 0x0312, 0x0211});

        @Override
        public ICell getCellForLeaves(int i) {
            return this.acaciaLeafCells[i];
        }

        @Override
        public ICell getCellForBranch(int i, int i1) {
            return i == 1 ? this.frankincenseBranch : CellNull.NULLCELL;
        }

        @Override
        public ICellSolver getCellSolver() {
            return this.frankincenseSolver;
        }

        @Override
        public SimpleVoxmap getLeafCluster() {
            return CustomLeafClusters.frankincense;
        }

        @Override
        public int getDefaultHydration() {
            return 4;
        }

    };

}
