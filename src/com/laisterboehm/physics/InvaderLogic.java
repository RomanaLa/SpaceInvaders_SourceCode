package com.laisterboehm.physics;

import java.awt.Color;

import at.fhooe.mtd.ecs.Component;
import at.fhooe.mtd.ecs.Entity;
import at.fhooe.mtd.sgl.math.Vector2d;

import com.laisterboehm.collision.Collider;
import com.laisterboehm.render.BoxVisual;
import com.laisterboehm.update.Updatable;
import com.laisterboehm.update.UpdateSystem;

public class InvaderLogic extends Component implements Updatable{

	private static Vector2d auxVec = new Vector2d();
	private static Vector2d up = new Vector2d(1,0);
	private double thrust;
	private Body body;
	private double pause;
	private double move = 1;
	private long now;
	private long now2;
	protected static boolean right = true;
	protected static boolean down = false;
	protected static boolean directionChanged = false;
	protected static int counter = 0;
	protected static int shoot;
	protected static boolean shooting = false;
	public static int nrAllInvaders = 0;
	public static int nrCurrentInvaders = 0;
	private int invaderID;
	private static boolean shootCalled = false;
	private static int downCounter = 0;
	private static int invaderCounter = 0;
	private int time;
	
	public InvaderLogic() {
		nrAllInvaders++;
		invaderID = nrAllInvaders;
		nrCurrentInvaders++;
	}
	
	public InvaderLogic thrust(double th) {
		thrust = th;
		return this;
	}
	
	public InvaderLogic pause(double t) {
		pause = t;
		return this;
	}

	protected void activate() {
		super.activate();
		body = getComponent(Body.class);
		getSystem(UpdateSystem.class).addUpdatable(this);
		now = System.nanoTime();
		now2 = System.nanoTime();
		time = 100000000;
	}

	protected void deactivate() {		
		nrCurrentInvaders--;
		super.deactivate();
		body = null;
		getSystem(UpdateSystem.class).removeUpdatable(this);
	}

	public void update(double dt) {
		if (System.nanoTime() - now >= pause * 1000000000) {	//if the pause is over move the invader
			auxVec.set(up)/*.rotate(body.getAngle())*/;
		
			//the force
			auxVec.scale(thrust);
		
			//update force	-> move invader right or left
			if (!right) {
				body.applyForce(-auxVec.x, auxVec.y);
			}else {
				body.applyForce(auxVec.x, auxVec.y);
			}
			if (down) {	//move invader down
				double posY = getEntity().getComponent(Body.class).getPosY();
				getEntity().getComponent(Body.class).setPosY(posY + 0.4);
				downCounter++;
				if(downCounter >= 700) {
					down = false;
					downCounter = 0;
				}
				
			}
			
			if (System.nanoTime() - now >= (pause + move) * 1000000000) {	//stop invader after pause + move time
				now = System.nanoTime();
				body.clearForces();
				body.clearVelocity();
				if (directionChanged){
					counter++;
				}

				if (counter >= 2 * nrCurrentInvaders) {
					directionChanged = false;
					counter = 0;
				}
			}
			thrust = thrust + 0.01;
			move = move - 0.00001;
			pause = pause - 0.00001;
			
			if(nrCurrentInvaders <= 8){
				time = 10000000;
			}
			
			if (System.nanoTime() - now2 >= 10 * time){	//let the invaders shoot
				//check if invader is last invader
				invaderCounter++;
				if (invaderCounter >= nrCurrentInvaders) {
					shootCalled = false;
					invaderCounter = 0;
				}
				shooting = true;
				now2 = System.nanoTime();
				shoot = (int)((Math.random() * nrAllInvaders) + 1);	//a random number to chose which invader should shoot
			}
			
			if (shooting && shoot == invaderID) {
				shooting = false;
				shoot();
				shoot = 0;
			}
		}
	}
	
	public void changeDirection() {	//change the direction of all invaders
		if (!directionChanged) {
			if (right) {
				right = false;
			}else {
				right = true;
			}
			down = true;
		}
		directionChanged = true;
	}
	
	private void shoot() {
		if (!shootCalled) {
			shootCalled = true;
			Entity entity = new Entity();			
			entity.addComponent(new Body().linearVelocity(0,100).mass(100).pos(body.getPosX(), body.getPosY() + 20/*Ändern!! */));
			entity.addComponent(new BoxVisual().color(Color.WHITE).filled(true).width(5).height(15));
			Collider c = new Collider();
			entity.addComponent(c);
			c.createBox(5, 15);
			entity.addComponent(new InvaderBulletLogic().thrust(10));
			getEngine().addEntity(entity);
		}
		
	}
}
