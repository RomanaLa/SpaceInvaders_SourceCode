package com.laisterboehm.physics;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import at.fhooe.mtd.ecs.Component;
import at.fhooe.mtd.ecs.Entity;
import at.fhooe.mtd.sgl.Sgl;
import at.fhooe.mtd.sgl.math.Vector2d;

import com.laisterboehm.collision.Collider;
import com.laisterboehm.render.BoxVisual;
import com.laisterboehm.update.Updatable;
import com.laisterboehm.update.UpdateSystem;

public class SpaceShipControl extends Component implements Updatable{

	private Vector2d auxVec = new Vector2d();
	private Vector2d up = new Vector2d(1,0);
	private double thrust;
	private Body body;
	private boolean keyPressed = false;
	private long now;
	
	public SpaceShipControl thrust(double th) {
		thrust = th;
		return this;
	}
	
	protected void activate() {
		super.activate();
		body = getComponent(Body.class);
		getSystem(UpdateSystem.class).addUpdatable(this);
		now = System.nanoTime();
	}

	protected void deactivate() {
		super.deactivate();
		body = null;
		getSystem(UpdateSystem.class).removeUpdatable(this);
	}

	public void update(double dt) {
		auxVec.set(up).rotate(body.getAngle());
		
		//the force of the ship's engine
		auxVec.scale(getCurrentThrust());
		
		//update force
		body.applyForce(auxVec.x, auxVec.y);
		
		if(Sgl.input.isKeyPressed(KeyEvent.VK_SPACE)) {
			//shoot
			if(!keyPressed && System.nanoTime() - now >= 2 * 1000000000) {
				keyPressed = true;
				createBullet();
				now = System.nanoTime();
			}
			
		}else {
			keyPressed = false;
		}
		
	}
	
	private double getCurrentThrust() {
		if(Sgl.input.isKeyPressed(KeyEvent.VK_LEFT)) {
			return thrust;
		} else if(Sgl.input.isKeyPressed(KeyEvent.VK_RIGHT)) {
			return -thrust;
		}
		return 0;
	}
	
	private void createBullet() {
		try {
	        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("shoot.wav").getAbsoluteFile());
	        Clip clip = AudioSystem.getClip();
	        clip.open(audioInputStream);
	        clip.start();
	    } catch(Exception ex) {
	        System.out.println("Error with playing sound.");
	    }
		
		Entity entity = new Entity();
		entity.addComponent(new Body().linearVelocity(0,-100).mass(100).pos(body.getPosX(), body.getPosY()- 40 /*Ändern!! */));
		entity.addComponent(new BoxVisual().color(Color.WHITE).filled(true).width(5).height(15));
		Collider c = new Collider();
		entity.addComponent(c);
		c.createBox(5, 15);
		entity.addComponent(new ShipBulletLogic().thrust(10));
		getEngine().addEntity(entity);
	}

}
