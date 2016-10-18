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

public class IntroductionState extends GameState {

	private double displayTime = 200.0;
	private Color color = Color.BLACK;
	private TextRenderer txtRenderer;

	public IntroductionState(Game context) {
		super(context);
	}

	public void enter() {
		Sgl.graphics.setClearColor(color);
		displayTime = 5.0;
		txtRenderer = new TextRenderer();
	}

	public void exit() {
		super.exit();
	}

	public void update(double dt) {
		displayTime -= dt;

		if (displayTime <= 0) {
			MenuState newState = new MenuState(getContext());
			getContext().switchState(new BlendState(getContext(), newState, color, newState.getColor()));
		}

		Graphics2D g = Sgl.graphics.beginUpdate();
		
		txtRenderer.reset();
		txtRenderer.setFont(new Font(Font.DIALOG, Font.PLAIN, 40));
		txtRenderer.setColor(Color.WHITE);
		txtRenderer.setAlignment(Alignment.LEFT);		
		txtRenderer.render(g, "hello astronaut!");
		txtRenderer.render(g, "");
		txtRenderer.setFont(new Font(Font.DIALOG, Font.PLAIN, 30));
		txtRenderer.render(g, "thank you for coming and");
		txtRenderer.render(g, "helping to protect our planet!");
		txtRenderer.render(g, "take care and watch out");
		txtRenderer.render(g, "to not get caught by invaders!");
		
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("planets.png"));
		} catch (IOException e) {
		}	
		
		Image scaledImg = img.getScaledInstance(img.getWidth() / 5, img.getHeight() / 5, Image.SCALE_SMOOTH);		
		g.drawImage(scaledImg, Sgl.graphics.getWidth() / 8 * 5, Sgl.graphics.getHeight() / 5 * 3 +50, null);

		

		Sgl.graphics.endUpdate();
	}

	public Color getColor() {
		return color;
	}

	public void resize(int width, int height) {
		txtRenderer.setPos(width * 0.333, height * 0.333);		
	}
	
	

}
