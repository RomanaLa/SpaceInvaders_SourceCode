package com.laisterboehm.collision;

import com.laisterboehm.components.MutablePose;
import com.laisterboehm.physics.Body;
import com.laisterboehm.update.Updatable;
import com.laisterboehm.update.UpdateSystem;

import at.fhooe.mtd.ecs.Component;

public class PosXDelimiterShip extends Component implements Updatable{
	
	private MutablePose pose;
	private double width;
	private double compWidth;
	
	public PosXDelimiterShip componentWidth(double w) {
		compWidth = w;
		return this;
	}
	
	public PosXDelimiterShip width(double w) {
		width = w;
		return this;
	}
	
	@Override
	protected void activate() {
		super.activate();
		pose = getComponent(MutablePose.class);
		getSystem(UpdateSystem.class).addUpdatable(this);
	}

	@Override
	protected void deactivate() {
		super.deactivate();
		pose = null;
		getSystem(UpdateSystem.class).removeUpdatable(this);
	}

	@Override
	public void update(double dt) {
		//delimit position of the Ship
		if (pose.getPosX() > width/2 - compWidth/2) {
			pose.setPosX(width/2 - compWidth/2);
			if (pose instanceof Body) {
				double[] vel = ((Body) pose).getLinearVelocity();
				((Body) pose).linearVelocity(-vel[0], -vel[1]);
			}	
		} else if (pose.getPosX() < -width/2 + compWidth/2) {
			pose.setPosX(-width/2 + compWidth/2);
			if (pose instanceof Body) {
				double[] vel = ((Body) pose).getLinearVelocity();
				((Body) pose).linearVelocity(-vel[0], -vel[1]);
			}
		}
		
	}

}
