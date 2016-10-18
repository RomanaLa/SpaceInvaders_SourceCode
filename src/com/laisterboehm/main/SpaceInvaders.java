package com.laisterboehm.main;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import com.laisterboehm.states.SplashState;

import at.fhooe.mtd.sgl.app.Game;

public class SpaceInvaders extends Game{
	
	public static final int WIDTH = 640;
	public static final int HEIGHT = 480;
	private static Clip clip;
	
	public SpaceInvaders(){
		try {
	        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("Splash.wav").getAbsoluteFile());
	        clip = AudioSystem.getClip();
	        clip.open(audioInputStream);
	    } catch(Exception ex) {
	        System.out.println("Error with playing sound.");
	    }
	}
    
	public void create() {	
		
		try {
	        clip.start();
	    } catch(Exception ex) {
	        System.out.println("Error with playing sound.");
	    }
		
		switchState(new SplashState(this));
	}
	
	public static Clip getClip(){
		return clip;
	}
	


}
