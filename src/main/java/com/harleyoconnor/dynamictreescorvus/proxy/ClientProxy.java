package com.harleyoconnor.dynamictreescorvus.proxy;

import com.ferreusveritas.dynamictrees.api.TreeHelper;
import com.ferreusveritas.dynamictrees.api.client.ModelHelper;
import com.ferreusveritas.dynamictrees.blocks.BlockDynamicLeaves;
import com.ferreusveritas.dynamictrees.blocks.LeavesPaging;
import com.harleyoconnor.dynamictreescorvus.DynamicTreesCorvus;
import net.minecraft.block.Block;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {
	
	@Override
	public void preInit() {
		super.preInit();
	}
	
	@Override
	public void init() {
		super.init();
		registerColorHandlers();
	}
	
	@Override public void postInit() {
		super.postInit();
	}
	
	public void registerColorHandlers() {
		for (BlockDynamicLeaves leaves: LeavesPaging.getLeavesMapForModId(DynamicTreesCorvus.MODID).values()) {
			ModelHelper.regColorHandler(leaves, (state, worldIn, pos, tintIndex) -> {
				Block block = state.getBlock();

				if (TreeHelper.isLeaves(block)) return ((BlockDynamicLeaves) block).getProperties(state).foliageColorMultiplier(state, worldIn, pos);

				return 0x00FF00FF; // Magenta
			});
		}
	}
}
