package com.laisterboehm.collision;

public interface CollisionListener {

	public void handlePreResponse(Collision c);
	public void handleCollision(Collision c);
}
