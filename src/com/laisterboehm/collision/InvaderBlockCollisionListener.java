package com.laisterboehm.collision;

import com.laisterboehm.components.Block;
import com.laisterboehm.physics.InvaderLogic;
import com.laisterboehm.states.PlayState;

public class InvaderBlockCollisionListener implements CollisionListener{

	public void handlePreResponse(Collision c) {
		// TODO Auto-generated method stub
		
	}
	//handle Collisions between Invaders and Blocks
	public void handleCollision(Collision c) {
		if (c.getColliderA().getEntity().hasComponent(Block.class) && c.getColliderB().getEntity().hasComponent(InvaderLogic.class)) {
			PlayState.endOfGame();
		}else if (c.getColliderB().getEntity().hasComponent(Block.class) && c.getColliderA().getEntity().hasComponent(InvaderLogic.class)) {
			PlayState.endOfGame();
		}
	}

}
