package com.laisterboehm.collision;

import at.fhooe.mtd.sgl.math.Vector2d;

public class Collision {

	private Collider colliderA;
	private Collider colliderB;
	private Vector2d normal;
	private double depth;
	
	public Collision(Collider a, Collider b, Vector2d normal, double depth){
		colliderA = a;
		colliderB = b;
		this.normal = normal;
		this.depth = depth;
	}
	
	public Collider getColliderA() {
		return colliderA;
	}
	
	public Collider getColliderB() {
		return colliderB;
	}
	
	public Vector2d getNormal() {
		return normal;
	}
	
	public double getDepth() {
		return depth;
	}
}
