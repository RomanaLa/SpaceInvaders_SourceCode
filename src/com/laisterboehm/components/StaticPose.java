package com.laisterboehm.components;

import at.fhooe.mtd.ecs.Component;

public class StaticPose extends Component implements MutablePose{

	private double posX;
	private double posY;
	private double angle;
	
	public StaticPose posX(double x) {
		setPosX(x);
		return this;
	}
	
	public StaticPose posY(double y) {
		setPosY(y);
		return this;
	}
	
	public StaticPose angle(double rad) {
		setAngle(rad);
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
	public void setPosX(double x) {
		posX = x;
		
	}

	@Override
	public void setPosY(double y) {
		posY = y;
		
	}

	@Override
	public void setAngle(double rad) {
		angle = rad;
		
	}

}
