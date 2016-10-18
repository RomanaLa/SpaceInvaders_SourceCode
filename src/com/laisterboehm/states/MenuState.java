package com.laisterboehm.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.laisterboehm.main.SpaceInvaders;

import at.fhooe.mtd.sgl.Sgl;
import at.fhooe.mtd.sgl.app.Game;
import at.fhooe.mtd.sgl.app.GameState;
import at.fhooe.mtd.sgl.graphics.Alignment;
import at.fhooe.mtd.sgl.graphics.TextRenderer;

public class MenuState extends GameState{
	
	private Color color = Color.WHITE;
	private TextRenderer txtRenderer;

	public MenuState(Game context) {
		super(context);
	}
	
	public void enter() {
		Sgl.graphics.setClearColor(color);
		txtRenderer = new TextRenderer();
	}

	public void exit() {
		super.exit();
	}

	public void update(double dt) {

		Graphics2D g = Sgl.graphics.beginUpdate();
		
		txtRenderer.reset();
		txtRenderer.setFont(new Font(Font.DIALOG, Font.PLAIN, 60));
		txtRenderer.setColor(Color.BLACK);
		txtRenderer.setAlignment(Alignment.LEFT);
		txtRenderer.render(g, "MENU");
		txtRenderer.render(g, "");
		txtRenderer.setFont(new Font(Font.DIALOG, Font.PLAIN, 30));
		txtRenderer.render(g, "press <SPACE> to play");
		txtRenderer.render(g, "");
		txtRenderer.render(g, "press <C> to hava a look at the controls");
		txtRenderer.render(g, "press <Q> to quit");
		
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("ship.png"));
		} catch (IOException e) {
		}	
		
		
		Image scaledImg = img.getScaledInstance(img.getWidth() / 4, img.getHeight() / 4, Image.SCALE_SMOOTH);		
		g.drawImage(scaledImg, Sgl.graphics.getWidth() / 8 * 5, Sgl.graphics.getHeight() / 5 * 3 +50, null);
		

		Sgl.graphics.endUpdate();
		
		if(Sgl.input.isKeyPressed('C') || Sgl.input.isKeyPressed('c')){
			ControlsState newState = new ControlsState(getContext());
			getContext().switchState(new BlendState(getContext(), newState, color, newState.getColor()));
		}
		
		if(Sgl.input.isKeyPressed(32)){
			PlayState newState = new PlayState(getContext());
			getContext().switchState(new BlendState(getContext(), newState, color, newState.getColor()));
			SpaceInvaders.getClip().stop();
		}
		
		if(Sgl.input.isKeyPressed('Q') || Sgl.input.isKeyPressed('q')){
			System.exit(0);
		}
	}

	
	public Color getColor(){
		return color;
	}

	public void resize(int width, int height) {
		txtRenderer.setPos(width * 0.333, height * 0.333);
	}
	
	

}
