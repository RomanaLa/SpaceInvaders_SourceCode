package com.laisterboehm.collision;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import com.laisterboehm.physics.InvaderBulletLogic;
import com.laisterboehm.physics.SpaceShipControl;
import com.laisterboehm.states.PlayState;

public class InvaderbulletShipCollisionListener implements CollisionListener{

	public void handlePreResponse(Collision c) {
		// TODO Auto-generated method stub
		
	}
	//handle Collisions between Invader Bullets and the Ship
	public void handleCollision(Collision c) {
		if (c.getColliderA().getEntity().hasComponent(InvaderBulletLogic.class) && c.getColliderB().getEntity().hasComponent(SpaceShipControl.class)) {
			PlayState.endOfGame();
			
			try {
		        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("explosion.wav").getAbsoluteFile());
		        Clip clip = AudioSystem.getClip();
		        clip.open(audioInputStream);
		        clip.start();
		    } catch(Exception ex) {
		        System.out.println("Error with playing sound.");
		    }
		}else if (c.getColliderB().getEntity().hasComponent(InvaderBulletLogic.class) && c.getColliderA().getEntity().hasComponent(SpaceShipControl.class)) {
			PlayState.endOfGame();
			
			try {
		        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("explosion.wav").getAbsoluteFile());
		        Clip clip = AudioSystem.getClip();
		        clip.open(audioInputStream);
		        clip.start();
		    } catch(Exception ex) {
		        System.out.println("Error with playing sound.");
		    }
		}
		
	}

}
