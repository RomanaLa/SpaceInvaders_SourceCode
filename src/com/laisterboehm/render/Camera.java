package com.laisterboehm.render;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;

import at.fhooe.mtd.sgl.math.Vector2d;

public class Camera {

	/**Camera transform.*/
	private AffineTransform tx = new AffineTransform();
	
	/**Inverse transform. */
	private AffineTransform txInv = new AffineTransform();
	
	private double halfWidth;
	private double halfHeight;
	private double zoom = 1.0;
	private double posX;
	private double posY;
	private boolean dirty;
	private Point2D.Double auxPt = new Point2D.Double();
	
	
	Camera(int screenWidth, int screenHeight) {
		resize(screenWidth, screenHeight);
	}
	
	void resize(int screenWidth, int screenHeight) {
		halfWidth = screenWidth * 0.5;
		halfHeight = screenHeight * 0.5;
		dirty = true;
	}
	
	public void setZoom(double z) {
		assert z > 0 : "zoom value must be > 0";
		zoom = z;
		dirty = true;
	}
	
	public double getZoom() {
		return zoom;
	}
	
	public void setPos(double x, double y) {
		setPosX(x);
		setPosY(y);
	}
	
	public void setPosX(double x) {
		posX = x;
		dirty = true;
	}
	
	public void setPosY(double y) {
		posY = y;
		dirty = true;
	}
	
	public double getPosX() {
		return posX;
	}
	
	public double getPosY() {
		return posY;
	}
	
	/**
	 * Transforms screen coordinates to world coordinates
	 */
	public Vector2d unproject(int screenX, int screenY) {
		Vector2d result = new Vector2d();
		auxPt.x = screenX;
		auxPt.y = screenY;
		txInv.transform(auxPt, auxPt);
		
		result.x = auxPt.x;
		result.y = auxPt.y;
		
		return result;
	}
	
	public void transform(Graphics2D g) {
		update();
		g.transform(tx);
	}

	private void update() {
		if (!dirty) {
			return;
		}
		tx.setToIdentity();
		tx.translate(halfWidth, halfHeight);
		
		tx.scale(zoom, zoom);
		tx.translate(-posX, -posY);
		
		txInv.setTransform(tx);
		try {
			txInv.invert();
		} catch (NoninvertibleTransformException e) {
			// should never happen
			assert false;
		}
		dirty = false;
	}
}
