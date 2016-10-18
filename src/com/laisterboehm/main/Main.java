package com.laisterboehm.main;
import at.fhooe.mtd.sgl.app.Java2dApplication;
import at.fhooe.mtd.sgl.app.Java2dApplicationConfig;
import at.fhooe.mtd.sgl.graphics.GfxConfigurator;

public class Main {	

	public static void main(String[] args) {
		
		GfxConfigurator gfxc = new GfxConfigurator();
		gfxc.setTitle("SpaceInvaders Graphics Configuration");
		gfxc.runDialog();
		
		Java2dApplicationConfig c = Java2dApplicationConfig.create(gfxc);
		c.title = "SPACE INVADERS";
		new Java2dApplication(c, new SpaceInvaders());
	}
}
