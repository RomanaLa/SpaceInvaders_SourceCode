package com.laisterboehm.render;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

public class VisualSystem extends AbstractGraphicSubsystem{

	private List<Visual> visuals = new ArrayList<>();
	
	
	public void addVisual(Visual v) {
		assert !visuals.contains(v): "Visual already added!";
		visuals.add(v);
	}
	
	public void removeVisual(Visual v) {
		visuals.remove(v);
	}

	public void render (Graphics2D g, double dt) {
		camera.transform(g);
		AffineTransform oldTx = g.getTransform();
		
		for (Visual v: visuals) {
			v.render(g);
			g.setTransform(oldTx);
		}
		
	}
	

}
