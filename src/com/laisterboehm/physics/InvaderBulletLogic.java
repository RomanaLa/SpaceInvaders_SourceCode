package com.laisterboehm.physics;

import com.laisterboehm.update.Updatable;
import com.laisterboehm.update.UpdateSystem;

import at.fhooe.mtd.ecs.Component;
import at.fhooe.mtd.sgl.math.Vector2d;

public class InvaderBulletLogic extends Component implements Updatable{

	private Vector2d auxVec = new Vector2d();
	private Vector2d up = new Vector2d(0,1);
	private double thrust;
	private Body body;
	
	public InvaderBulletLogic thrust(double th) {
		thrust = -th;
		return this;
	}

	protected void activate() {
		super.activate();
		body = getComponent(Body.class);
		getSystem(UpdateSystem.class).addUpdatable(this);
	}

	protected void deactivate() {
		super.deactivate();
		body = null;
		getSystem(UpdateSystem.class).removeUpdatable(this);
	}

	public void update(double dt) {
		auxVec.set(up).rotate(body.getAngle());
		
		//the force of the bullet
		auxVec.scale(thrust);
		
		//update force
		body.applyForce(auxVec.x, auxVec.y);
		
		
	}

}
