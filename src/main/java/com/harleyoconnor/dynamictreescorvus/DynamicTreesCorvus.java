package com.harleyoconnor.dynamictreescorvus;

import com.ferreusveritas.dynamictrees.ModConstants;
import com.harleyoconnor.dynamictreescorvus.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid= DynamicTreesCorvus.MODID, name= DynamicTreesCorvus.NAME, dependencies = DynamicTreesCorvus.DEPENDENCIES)
public class DynamicTreesCorvus {
	
	public static final String MODID = "dynamictreescorvus";
	public static final String NAME = "Dynamic Trees for Corvus";
	public static final String DEPENDENCIES = "required-after:" + ModConstants.DYNAMICTREES_LATEST
			+ ";required-after:corvus";
	
	@Mod.Instance
	public static DynamicTreesCorvus instance;
	
	@SidedProxy(clientSide = "com.harleyoconnor.dynamictreescorvus.proxy.ClientProxy", serverSide = "com.harleyoconnor.dynamictreescorvus.proxy.CommonProxy") //com.
	public static CommonProxy proxy;

	public static Logger logger;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();
		proxy.preInit();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		proxy.postInit();
	}
	
}
