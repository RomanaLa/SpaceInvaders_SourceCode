package com.laisterboehm.render;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;

import com.laisterboehm.components.Pose;

import at.fhooe.mtd.ecs.Component;

public class BoxVisual extends Component implements Visual{

	private AffineTransform tx = new AffineTransform();
	private Rectangle2D.Double rect = new Rectangle2D.Double();
	private Pose pose;
	private Color color = Color.CYAN;
	private boolean filled;
	public boolean clicked;
	
	public BoxVisual width(double w) {
		rect.x = -w/2;
		rect.width = w;
		return this;
	}
	
	public BoxVisual height(double h) {
		rect.y = -h/2;
		rect.height = h;
		return this;
	}
	
	public BoxVisual color(Color c) {
		color = c;
		return this;
	}
	
	public BoxVisual filled(boolean b){
		filled = b;
		return this;
	}
	
	public Double getRect() {
		return rect;
	}

	public void render(Graphics2D g) {
		tx.setToIdentity();
		tx.translate(pose.getPosX(), pose.getPosY());
		tx.rotate(pose.getAngle());
		g.transform(tx);
		
		g.setColor(color);
		if (clicked) {
			g.setColor(Color.GREEN);
		}
		if (filled) {
			g.fill(rect);
		} else {
			g.draw(rect);
		}
	}

	protected void activate() {
		pose = getComponent(Pose.class);
		getSystem(VisualSystem.class).addVisual(this);
	}

	protected void deactivate() {
		pose = null;
		getSystem(VisualSystem.class).removeVisual(this);
	}
	
	

}
