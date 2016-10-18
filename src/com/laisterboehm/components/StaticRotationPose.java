package com.laisterboehm.components;

import com.laisterboehm.update.Updatable;
import com.laisterboehm.update.UpdateSystem;

import at.fhooe.mtd.ecs.Component;

public class StaticRotationPose extends Component implements Pose, Updatable{

	private double posX;
	private double posY;
	private double angle;
	private double angularVelocity;
	
	public StaticRotationPose posX(double x) {
		posX = x;
		return this;
	}
	
	public StaticRotationPose posY(double y) {
		posY = y;
		return this;
	}
	
	public StaticRotationPose angle(double rad) {
		angle = rad;
		return this;
	}
	
	public StaticRotationPose angularVelocity(double radPerSecond) {
		angularVelocity = radPerSecond;
		return this;
	}
	
	
	@Override
	public double getPosX() {
		
		return posX;
	}

	@Override
	public double getPosY() {
		
		return posY;
	}

	@Override
	public double getAngle() {
		
		return angle;
	}

	@Override
	public void update(double dt) {
		angle += angularVelocity * dt;
		
	}

	@Override
	protected void activate() {
		getSystem(UpdateSystem.class).addUpdatable(this);
	}

	@Override
	protected void deactivate() {
		getSystem(UpdateSystem.class).removeUpdatable(this);
	}
	

}
