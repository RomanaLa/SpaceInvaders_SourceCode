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

public class WonState extends GameState{
	
	private Color color = Color.GRAY;
	private TextRenderer txtRenderer;

	public WonState(Game context) {
		super(context);
	}
	
	public void enter() {
		Sgl.graphics.setClearColor(color);
		txtRenderer = new TextRenderer();
		SpaceInvaders.getClip().start();
	}

	public void exit() {
		super.exit();
	}

	public void update(double dt) {

Graphics2D g = Sgl.graphics.beginUpdate();
		
		txtRenderer.reset();
		txtRenderer.setFont(new Font(Font.DIALOG, Font.PLAIN, 80));
		txtRenderer.setColor(Color.BLACK);
		txtRenderer.setAlignment(Alignment.CENTER);
		txtRenderer.render(g, "YOU WON!");
		txtRenderer.render(g, "");
		txtRenderer.setFont(new Font(Font.DIALOG, Font.PLAIN, 30));
		txtRenderer.setColor(Color.WHITE);
		txtRenderer.render(g, "press <SPACE> to play again");
		txtRenderer.render(g, "press <M> to return to the menu");
		
		BufferedImage img1 = null;
		BufferedImage img2 = null;		
		try {
		    img1 = ImageIO.read(new File("planets.png"));
		    img2 = ImageIO.read(new File("ship.png"));
		} catch (IOException e) {
		}
		
		Image scaledImg = img1.getScaledInstance(img1.getWidth() / 7, img1.getHeight() / 7, Image.SCALE_SMOOTH);		
		g.drawImage(scaledImg, 25, 50, null);		
		scaledImg = img2.getScaledInstance(img2.getWidth() / 5, img2.getHeight() / 5, Image.SCALE_SMOOTH);		
		g.drawImage(scaledImg, Sgl.graphics.getWidth() - img2.getWidth() / 5 - 25, 75, null);

		Sgl.graphics.endUpdate();
		
		if(Sgl.input.isKeyPressed('M') || Sgl.input.isKeyPressed('m')){
			MenuState newState = new MenuState(getContext());
			getContext().switchState(new BlendState(getContext(), newState, color, newState.getColor()));
		}
		
		if(Sgl.input.isKeyPressed(32)){
			PlayState newState = new PlayState(getContext());
			getContext().switchState(new BlendState(getContext(), newState, color, newState.getColor()));
			SpaceInvaders.getClip().stop();
		}		
	}
	
	public Color getColor(){
		return color;
	}
	
	public void resize(int width, int height) {
		txtRenderer.setPos(width * 0.5, height * 0.5);
	}
}
