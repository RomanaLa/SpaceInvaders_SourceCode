package com.laisterboehm.states;

import java.awt.Color;

import at.fhooe.mtd.sgl.Sgl;
import at.fhooe.mtd.sgl.app.Game;
import at.fhooe.mtd.sgl.app.GameState;

public class BlendState extends GameState {

	private GameState nextState;
	private Color x, y;

	public BlendState(Game context, GameState nextState, Color x, Color y) {
		super(context);
		this.nextState = nextState;
		this.x = x;
		this.y = y;
	}

	public void enter() {

	}

	public void exit() {

	}

	public void update(double dt) {

		int steps = 25;
		
		double rd = 0;
		double gd = 0;
		double bd = 0;
		
		int r = x.getRed();
		int g = x.getGreen();
		int b = x.getBlue();
		
			for (int i = 0; i <= steps; i++) {				
				
				if( i == 0){
					
					rd = (x.getRed() - y.getRed()) / steps;
					gd = (x.getGreen() - y.getGreen()) / steps;
					bd = (x.getBlue() - y.getBlue()) / steps;
										
					rd = rd * (-1);
					gd = gd * (-1);
					bd = bd * (-1);
					
				}			
				
				r += rd;
				g += gd;
				b += bd;
				
				if(i == 25){
					r = y.getRed();
					g = y.getGreen();
					b = y.getBlue();
				}
			
				final Color c = new Color(r, g, b);

				Sgl.graphics.setClearColor(c);

				Sgl.graphics.beginUpdate();
				Sgl.graphics.endUpdate();

				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		getContext().switchState(nextState);
	}

}
