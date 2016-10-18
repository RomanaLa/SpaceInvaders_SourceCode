package com.laisterboehm.render;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

import com.laisterboehm.components.Pose;

import at.fhooe.mtd.ecs.Component;

public class PolygonVisual extends Component implements Visual{

	private AffineTransform tx = new AffineTransform();
	private Path2D.Double path = new Path2D.Double();
	private Pose pose;
	private Color color = Color.CYAN;
	private boolean filled;
	public boolean clicked;
	
	public PolygonVisual(double vertices[]) {
		if (vertices.length % 2 != 0 || vertices.length < 3 * 2 ) {
			throw new IllegalArgumentException("Invalid number of vertices");
		}
		
		path.moveTo(vertices[0], vertices[1]);
		for (int i = 2; i < vertices.length; i += 2) {
			path.lineTo(vertices[i], vertices[i + 1]);
		}
		path.closePath();
	}
	
	public PolygonVisual color(Color c) {
		color = c;
		return this;
	}
	
	public PolygonVisual filled(boolean b){
		filled = b;
		return this;
	}
	
	public java.awt.geom.Path2D.Double getPath() {
		return path;
	}

	
	@Override
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
			g.fill(path);
		} else {
			g.draw(path);
		}
	}

	@Override
	protected void activate() {
		pose = getComponent(Pose.class);
		getSystem(VisualSystem.class).addVisual(this);
	}

	@Override
	protected void deactivate() {
		pose = null;
		getSystem(VisualSystem.class).removeVisual(this);
	}
	
	

}
