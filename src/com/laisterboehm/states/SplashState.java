package com.laisterboehm.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import at.fhooe.mtd.sgl.Sgl;
import at.fhooe.mtd.sgl.app.Game;
import at.fhooe.mtd.sgl.app.GameState;
import at.fhooe.mtd.sgl.graphics.Alignment;
import at.fhooe.mtd.sgl.graphics.TextRenderer;

public class SplashState extends GameState {
	
	private double displayTime = 5.0;
	private Color color = Color.BLACK;
	private double colorTime;
	private Color textColor;
	private TextRenderer txtRenderer;
	
	
	public SplashState(Game context) {
		super(context);
	}

	public void enter() {		
		displayTime = 5.0;
		colorTime = 0.2;
		textColor = new Color((int)(Math.random()*255) + 1, (int)(Math.random()*255) + 1, (int)(Math.random()*255) + 1);
		txtRenderer = new TextRenderer();
	}

	public void exit() {
	
	}

	public void update(double dt) {
		displayTime -= dt;		
		if(displayTime <= 0){
			IntroductionState newState = new IntroductionState(getContext());
			getContext().switchState(new BlendState(getContext(), newState, color, newState.getColor()));
		}
		
		colorTime -= dt;
		
		if(colorTime <= 0){
			textColor = new Color((int)(Math.random()*255) + 1, (int)(Math.random()*255) + 1, (int)(Math.random()*255) + 1);
			colorTime = 0.2;
		}
		
		Sgl.graphics.setClearColor(color);
		
		Graphics2D g = Sgl.graphics.beginUpdate();
		
		txtRenderer.reset();
		txtRenderer.setFont(new Font(Font.DIALOG, Font.BOLD, 80));
		txtRenderer.setColor(textColor);
		txtRenderer.setAlignment(Alignment.CENTER);
		txtRenderer.render(g, "SPACE INVADERS");
		
		txtRenderer.setFont(new Font(Font.DIALOG, Font.PLAIN, 30));
		txtRenderer.setColor(Color.WHITE);
		txtRenderer.render(g, "by Romana Laister & Valentina Böhm");
		
		Sgl.graphics.endUpdate();
		
	}
	
	public Color getColor(){
		return color;
	}

	public void resize(int width, int height) {
		txtRenderer.setPos(width *0.5, height *0.5);
		
	}

	
	
}
