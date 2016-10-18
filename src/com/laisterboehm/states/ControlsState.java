package com.laisterboehm.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import at.fhooe.mtd.sgl.Sgl;
import at.fhooe.mtd.sgl.app.Game;
import at.fhooe.mtd.sgl.app.GameState;
import at.fhooe.mtd.sgl.graphics.Alignment;
import at.fhooe.mtd.sgl.graphics.TextRenderer;

public class ControlsState extends GameState{
	
	private Color color = Color.BLACK;
	private TextRenderer txtRenderer;

	public ControlsState(Game context) {
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
		txtRenderer.setFont(new Font(Font.DIALOG, Font.PLAIN, 30));
		txtRenderer.setColor(Color.WHITE);
		txtRenderer.setAlignment(Alignment.CENTER);
		txtRenderer.render(g, "                        SHOOT                            GO LEFT      GO RIGHT");
		txtRenderer.render(g, "");
		txtRenderer.render(g, "");
		txtRenderer.render(g, "");
		txtRenderer.render(g, "press <M> to return to the menu");
		
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("controls.png"));
		} catch (IOException e) {
		}
		
		Image scaledImg = img.getScaledInstance(img.getWidth() / 4, img.getHeight() / 4, Image.SCALE_SMOOTH);		
		g.drawImage(scaledImg, Sgl.graphics.getWidth() / 2 - (img.getWidth() / 4) / 2, Sgl.graphics.getHeight() / 3 + 25, null);
		

		Sgl.graphics.endUpdate();
		
		if(Sgl.input.isKeyPressed('M') || Sgl.input.isKeyPressed('m')){
			MenuState newState = new MenuState(getContext());
			getContext().switchState(new BlendState(getContext(), newState, color, newState.getColor()));
		}
		
	}
	
	public Color getColor(){
		return color;
	}

	public void resize(int width, int height) {
		txtRenderer.setPos(width * 0.5, height * 0.5);
	}
	
	
}
