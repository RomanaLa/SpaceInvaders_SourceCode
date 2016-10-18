package com.laisterboehm.collision;

import at.fhooe.mtd.ecs.Component;

import com.laisterboehm.components.MutablePose;
import com.laisterboehm.physics.InvaderLogic;
import com.laisterboehm.update.Updatable;
import com.laisterboehm.update.UpdateSystem;

public class PosXDelimiterInvaders extends Component implements Updatable{
	
	private MutablePose pose;
	private double width;
	private double compWidth;
	
	public PosXDelimiterInvaders componentWidth(double w) {
		compWidth = w;
		return this;
	}
	
	public PosXDelimiterInvaders width(double w) {
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
		//change direction of the invaders if they are on the edge
		if (pose.getPosX() >= width/2 - compWidth/2) {
			this.getEntity().getComponent(InvaderLogic.class).changeDirection();	
		} else if (pose.getPosX() <= -width/2 + compWidth/2) {
			this.getEntity().getComponent(InvaderLogic.class).changeDirection();
		}
		
	}

}
