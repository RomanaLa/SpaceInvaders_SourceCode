package com.laisterboehm.collision;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import com.laisterboehm.physics.InvaderLogic;
import com.laisterboehm.physics.ShipBulletLogic;

public class BulletInvaderCollisionListener implements CollisionListener{

	public void handlePreResponse(Collision c) {
		// TODO Auto-generated method stub
		
	}
	//handle Collisions between Invaders and Ship Bullets
	public void handleCollision(Collision c) {
		if (c.getColliderA().getEntity().hasComponent(InvaderLogic.class) && c.getColliderB().getEntity().hasComponent(ShipBulletLogic.class)) {
			c.getColliderA().getEntity().getEngine().removeEntity(c.getColliderA().getEntity());
			c.getColliderB().getEntity().getEngine().removeEntity(c.getColliderB().getEntity());
			
			try {
		        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("invaderkilled.wav").getAbsoluteFile());
		        Clip clip = AudioSystem.getClip();
		        clip.open(audioInputStream);
		        clip.start();
		    } catch(Exception ex) {
		        System.out.println("Error with playing sound.");
		    }
		}
		else if (c.getColliderB().getEntity().hasComponent(InvaderLogic.class)&& c.getColliderA().getEntity().hasComponent(ShipBulletLogic.class)) {
			c.getColliderB().getEntity().getEngine().removeEntity(c.getColliderB().getEntity());
			c.getColliderA().getEntity().getEngine().removeEntity(c.getColliderA().getEntity());
			
			try {
		        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("invaderkilled.wav").getAbsoluteFile());
		        Clip clip = AudioSystem.getClip();
		        clip.open(audioInputStream);
		        clip.start();
		    } catch(Exception ex) {
		        System.out.println("Error with playing sound.");
		    }
		}
		
	}

}
