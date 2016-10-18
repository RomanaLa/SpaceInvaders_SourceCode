package com.laisterboehm.collision;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.laisterboehm.components.Pose;

import at.fhooe.mtd.ecs.Engine;
import at.fhooe.mtd.ecs.EngineSystem;
import at.fhooe.mtd.sgl.math.Vector2d;

public class CollisionSystem extends EngineSystem{

	private List<Collider> colliders = new ArrayList<>();
	private Vector2d offset = new Vector2d();
	private List<Collision> collisions = new ArrayList<>();
	private List<CollisionListener> listeners = new ArrayList();
	private CollisionHandler handler;
	

	public void addedToEngine(Engine e) {
		setCollisionHandler(null);
		addCollisionListener(new BulletInvaderCollisionListener());
		addCollisionListener(new BottomInvaderCollisionListener());
		addCollisionListener(new BulletBlockCollisionListener());
		addCollisionListener(new InvaderBlockCollisionListener());
		addCollisionListener(new InvaderbulletShipCollisionListener());
		addCollisionListener(new BulletBulletCollisionListener());
	}

	List<Collider> getColliders() {
		return Collections.unmodifiableList(colliders);
	}
	
	public void setCollisionHandler(CollisionHandler ch) {
		if(ch == null) {
			handler = new NullHandler();
		}else {
		handler = ch;}
	}
	
	public CollisionHandler getCollisionHandler() {
		return handler;
	}
	
	public void addCollisionListener(CollisionListener l) {
		assert !listeners.contains(l): "collision listener already added" + l;
		listeners.add(l);
	}
	
	public void removeCollisionListener(CollisionListener l) {
		listeners.remove(l);
	}
	
	public void addCollider(Collider c) {
		assert !colliders.contains(c) : "Collider already added!";
		colliders.add(c);
	}

	public void removeCollider(Collider c) {
		colliders.remove(c);
	}

	@Override
	public void update(double dt) {
		resetColliders();
		updateColliders();
		clearCollisions();
		testForCollisions();
		informListeners();

	}
	
	private void informListeners() {
		for (Collision c: collisions) {
			handler.resolve(c);
			for(CollisionListener l: listeners) {
				l.handlePreResponse(c);
			}
			handler.respond(c);
			for (CollisionListener l: listeners) {
				l.handleCollision(c);
			}
		}
	}
	
	private void testForCollisions() {
		for (int i = 0; i < colliders.size(); ++i) {
			Collider colA = colliders.get(i);
			for (int j = i + 1; j < colliders.size(); ++j) {
				Collision col = colA.isColliding(colliders.get(j));
				if (col != null) {
					collisions.add(col);
				}
			}
		}
	}
	
	private void updateColliders() {
		for (Collider c: colliders) {
			c.update();
		}
	}
	
	private void clearCollisions() {
		// TODO if collisions are pooled, free collisions here
		collisions.clear();
	}

	public Collider inCollider(Vector2d point) {
		for (Collider c : colliders) {
			// detection in collider
			Vector2d[] vertices = c.getVertices();
			double maxX = Double.MIN_VALUE;
			double minX = Double.MAX_VALUE;
			double maxY = Double.MIN_VALUE;
			double minY = Double.MAX_VALUE;
			//if point.y is between maximum and minimum y vertex and point.x is between maximum and minimum x vertex
			for (Vector2d vertexPoint: vertices) { //search for max and min vertex point components
				if (vertexPoint.x > maxX) {
					maxX = vertexPoint.x;
				}
				else if (vertexPoint.x < minX) {
					minX = vertexPoint.x;
				}
				if (vertexPoint.y > maxY) {
					maxY = vertexPoint.y;
				}
				else if (vertexPoint.y < minY) {
					minY = vertexPoint.y;
				}
			}
			
			//check if point is inside collider
			if (point.x < maxX && point.x > minX && point.y < maxY && point.y > minY) {
				offset.x = c.getEntity().getComponent(Pose.class).getPosX() - point.x;
				offset.y = c.getEntity().getComponent(Pose.class).getPosY() - point.y;
				return c;
				
			}
		}
		return null;
	}

	public Vector2d getOffset() {
		return offset;
	}

	
	public void resetColliders() {
		for (int i = 0; i < colliders.size(); i++) {
			colliders.get(i).resetCollision();
		}
	}
	
}
