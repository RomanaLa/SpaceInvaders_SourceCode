package com.laisterboehm.collision;

import com.laisterboehm.components.Bottom;
import com.laisterboehm.physics.InvaderLogic;
import com.laisterboehm.states.PlayState;

public class BottomInvaderCollisionListener implements CollisionListener{

	@Override
	public void handlePreResponse(Collision c) {
		// TODO Auto-generated method stub
		
	}
	//handle Collisions between the Bottom and the Invaders
	@Override
	public void handleCollision(Collision c) {
		if (c.getColliderA().getEntity().hasComponent(Bottom.class) && c.getColliderB().getEntity().hasComponent(InvaderLogic.class)) {
			PlayState.endOfGame();
		}else if (c.getColliderB().getEntity().hasComponent(Bottom.class) && c.getColliderA().getEntity().hasComponent(InvaderLogic.class)) {
			PlayState.endOfGame();
		}
		
	}

}
