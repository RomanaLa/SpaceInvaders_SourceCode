package com.laisterboehm.collision;

public interface CollisionHandler {
	
	public void resolve(Collision c);
	public void respond(Collision c);
}
