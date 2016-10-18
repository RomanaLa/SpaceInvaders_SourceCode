package com.laisterboehm.physics;

import java.util.ArrayList;
import java.util.List;

import at.fhooe.mtd.ecs.EngineSystem;

public class PhysicSystem extends EngineSystem{

	private List<Body> bodies = new ArrayList<>();

	public void addBody(Body body) {
		assert !bodies.contains(body) : "Body already added!";
		bodies.add(body);
		
	}

	public void removeBody(Body body) {
		bodies.remove(body);
	}

	@Override
	public void update(double dt) {
		for(Body body: bodies) {
			body.update(dt);
			body.clearForces();
		}
	}

	
}
