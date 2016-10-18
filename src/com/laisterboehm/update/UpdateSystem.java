package com.laisterboehm.update;

import java.util.ArrayList;
import java.util.List;

import at.fhooe.mtd.ecs.EngineSystem;

public class UpdateSystem extends EngineSystem{

	private List<Updatable> updatables = new ArrayList<>();
	
	public void addUpdatable(Updatable u) {
		assert !updatables.contains(u): "Updatable already added!";
		updatables.add(u);
	}

	public void removeUpdatable(Updatable u) {
		updatables.remove(u);
	}

	@Override
	public void update(double dt) {
		for (Updatable u: updatables) {
			u.update(dt);
		}
	}
	
	
}
