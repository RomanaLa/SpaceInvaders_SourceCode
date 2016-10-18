package com.laisterboehm.collision;

import com.laisterboehm.components.Block;
import com.laisterboehm.physics.InvaderBulletLogic;
import com.laisterboehm.physics.ShipBulletLogic;

public class BulletBlockCollisionListener implements CollisionListener{

	public void handlePreResponse(Collision c) {
		// TODO Auto-generated method stub
		
	}

	//handle Collisions between Ship Bullets and Blocks and Invader Bullets and Blocks
	public void handleCollision(Collision c) {
		if (c.getColliderA().getEntity().hasComponent(ShipBulletLogic.class) && c.getColliderB().getEntity().hasComponent(Block.class)) {
			c.getColliderA().getEntity().getEngine().removeEntity(c.getColliderA().getEntity());
			c.getColliderB().getEntity().getEngine().removeEntity(c.getColliderB().getEntity());
		} else if (c.getColliderA().getEntity().hasComponent(InvaderBulletLogic.class) && c.getColliderB().getEntity().hasComponent(Block.class)){
			c.getColliderA().getEntity().getEngine().removeEntity(c.getColliderA().getEntity());
			c.getColliderB().getEntity().getEngine().removeEntity(c.getColliderB().getEntity());
		} else if (c.getColliderB().getEntity().hasComponent(ShipBulletLogic.class)&& c.getColliderA().getEntity().hasComponent(Block.class)) {
			c.getColliderB().getEntity().getEngine().removeEntity(c.getColliderB().getEntity());
			c.getColliderA().getEntity().getEngine().removeEntity(c.getColliderA().getEntity());
		} else if (c.getColliderB().getEntity().hasComponent(InvaderBulletLogic.class)&& c.getColliderA().getEntity().hasComponent(Block.class)) {
			c.getColliderB().getEntity().getEngine().removeEntity(c.getColliderB().getEntity());
			c.getColliderA().getEntity().getEngine().removeEntity(c.getColliderA().getEntity());
		}
		
	}

}
