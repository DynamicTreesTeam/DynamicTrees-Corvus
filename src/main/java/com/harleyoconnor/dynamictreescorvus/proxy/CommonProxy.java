package com.harleyoconnor.dynamictreescorvus.proxy;

import com.harleyoconnor.dynamictreescorvus.growth.CustomCellKits;

public class CommonProxy {
	
	public void preInit() {
		// Initialise custom cell kits.
		new CustomCellKits();
	}
	
	public void init() {

	}
	
	public void postInit() {
	}
	
}
