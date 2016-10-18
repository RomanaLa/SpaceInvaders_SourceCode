package com.laisterboehm.collision;

import java.awt.Graphics2D;

import at.fhooe.mtd.ecs.Component;
import at.fhooe.mtd.sgl.math.Vector2d;

import com.laisterboehm.components.Pose;
import com.laisterboehm.render.Visual;
import com.laisterboehm.render.VisualSystem;

public class Collider extends Component implements Visual{

	private Pose pose;
	private double posX;
	private double posY;
	// Arrays because elements are easier to address
	private Vector2d[] vertices;
	private Vector2d[] normals;
	private boolean collision;
	
	private Vector2d[] axes1;
	private Vector2d[] axes2;
	double overlap = Double.MAX_VALUE;
	Vector2d smallest = new Vector2d();

	public Collider(Vector2d... vertices) {
		this.vertices = vertices;
		normals = new Vector2d[vertices.length];
	}
	
	public Collider() {
		
	}
	
	public boolean collision() {
		return collision;
	}
	
	public Collider createBox(double w, double h) {
		double x = getEntity().getComponent(Pose.class).getPosX();
		double y = getEntity().getComponent(Pose.class).getPosY();
		vertices = new Vector2d[4];
		normals = new Vector2d[4];
		
		vertices [0] = new Vector2d(x - w/2, y - h/2);
		vertices [1] = new Vector2d(x + w/2, y - h/2);
		vertices [2] = new Vector2d(x + w/2, y + h/2);
		vertices [3] = new Vector2d(x - w/2, y + h/2);
		return this;
	}

	@Override
	protected void activate() {
		pose = getComponent(Pose.class);
		posX = pose.getPosX();
		posY = pose.getPosY();
		

		for (int i = 1; i < vertices.length; i++) {
			double dx = vertices[i].x - vertices[i - 1].x;
			double dy = vertices[i].y - vertices[i - 1].y;
			normals[i - 1] = new Vector2d(dy, -dx);
		}
		// last normal
		normals[normals.length - 1] = new Vector2d((-1)* (vertices[vertices.length - 1].y - vertices[0].y),vertices[vertices.length - 1].x - vertices[0].x);

		getSystem(VisualSystem.class).addVisual(this);
		getSystem(CollisionSystem.class).addCollider(this);

		/*
		 * double diagonal = Math.sqrt(Math.pow(visual.getRect().width, 2) +
		 * Math.pow(visual.getRect().height, 2)); rect.width = diagonal;
		 * rect.height = diagonal;
		 */
	}

	@Override
	protected void deactivate() {
		pose = null;
		//getSystem(VisualSystem.class).removeVisual(this);
		getSystem(CollisionSystem.class).removeCollider(this);
	}

	//for testing!!!!
	@Override
	public void render(Graphics2D g) {
		/*tx.setToIdentity();
		//tx.translate(pose.getPosX() - posX, pose.getPosY() - posY);
		//tx.rotate(pose.getAngle() - angle);
		g.transform(tx);
		if (collision) {
			g.setColor(Color.RED);
		}else {
			g.setColor(Color.BLACK);
		}
		
		shape = new Path2D.Double();
		
		//draw Collider
		shape.moveTo(vertices[0].x, vertices[0].y);
		for (int i = 1; i < vertices.length; i++) {
			shape.lineTo(vertices[i].x, vertices[i].y);
		}
		shape.closePath();
		
		g.draw(shape);
		
		double normalLength = 1;
		//draw normals
		for (int i = 1; i < normals.length; i++) {
			// calculate middle between two points
			double x0 = Math.max(vertices[i - 1].x, vertices[i].x) - ((Math.max(vertices[i - 1].x, vertices[i].x) - Math.min(vertices[i - 1].x, vertices[i].x)) / 2.);
			double y0 = Math.max(vertices[i - 1].y, vertices[i].y) - ((Math.max(vertices[i - 1].y, vertices[i].y) - Math.min(vertices[i - 1].y, vertices[i].y)) / 2.);
			
			normalLength = normals[i-1].length() / 10;
			g.draw(new Line2D.Double(x0, y0, x0 + normals[i - 1].x/normalLength, y0+ normals[i - 1].y/normalLength));
		}
		// last normal
		double x0 = Math.max(vertices[vertices.length - 1].x, vertices[0].x)- ((Math.max(vertices[vertices.length - 1].x, vertices[0].x) - Math.min(vertices[vertices.length - 1].x, vertices[0].x)) / 2.);
		double y0 = Math.max(vertices[vertices.length - 1].y, vertices[0].y)- ((Math.max(vertices[vertices.length - 1].y, vertices[0].y) - Math.min(vertices[vertices.length - 1].y, vertices[0].y)) / 2.);
		
		normalLength = normals[normals.length - 1].length() / 10;
		
		g.draw(new Line2D.Double(x0, y0, x0 + normals[normals.length - 1].x/normalLength, y0 + normals[normals.length - 1].y/normalLength));

		angle = pose.getAngle();
		
		
		//draw MTV
		if (collision) {
			g.setColor(Color.BLUE);
			//g.draw(new Line2D.Double(pose.getPosX(), pose.getPosY(), (pose.getPosX() + smallest.normalize().x * overlap)/10, (pose.getPosY() + smallest.normalize().y * overlap)/10));
			Vector2d mtv = new Vector2d(overlap * smallest.x/smallest.length(), overlap * smallest.y/smallest.length());
			g.draw(new Line2D.Double(pose.getPosX(), pose.getPosY(), pose.getPosX() + mtv.x/10/4, pose.getPosY() + mtv.y/10/4));	//I don't know why....
		}
		//draw axes
		/*if (axes1 != null && axes2 != null) {
			for (int i = 1; i < axes1.length; i++){
				g.draw(new Line2D.Double(0, 0, axes1[i - 1].x, axes1[i-1].y));
			}
			for (int i = 1; i < axes2.length; i++){
				g.draw(new Line2D.Double(0, 0, axes2[i - 1].x, axes2[i-1].y));
			}
		}*/
		
	}

	
	
	public Collision isColliding(Collider other) {
		overlap = Double.MAX_VALUE;
		
		axes1 = getAxes(this);
		axes2 = getAxes(other);
		
		//loop over the axes 1
		for(int i = 0; i < axes1.length; i ++) {
			Vector2d axis = axes1[i];
			//project both shapes onto the axis
			Projection p1 = project(axis, this);
			Projection p2 = project(axis, other);
			//do the projections overlap?
			if(!p1.overlap(p2)) {
				//than we can guarantee that the shapes do not overlap
				return null;
			} else {
				//get the overlap
				double o = p1.getOverlap(p2);
				//check for minimum
				if (o < overlap) {
					//then set this one as the smallest
					overlap = o;
					other.overlap = o;
					smallest = axis;
					other.smallest = axis;
				}
			}
		}
		
		//loop over the axes 2
		for (int i = 0; i < axes2.length; i++) {
			Vector2d axis = axes2[i];
			// project both shapes onto the axis
			Projection p1 = project(axis,this);
			Projection p2 = project(axis, other);
			// do the projections overlap?
			if (!p1.overlap(p2)) {
				// than we can guarantee that the shapes do not overlap
				return null;
			}else {
				//get the overlap
				double o = p1.getOverlap(p2);
				//check for minimum
				if (o < overlap) {
					//then set this one as the smallest
					overlap = o;
					other.overlap = o;
					smallest = axis;
					other.smallest = axis;
				}
			}
		}
		other.collision = true;
		this.collision = true;
		//if we get here then we know that every axis had overlap on it
		//so we can guarantee an intersection
		return new Collision(this, other, smallest, overlap);
	}
	
	private Vector2d[] getAxes(Collider c) {
		Vector2d[] vertices = new Vector2d[c.numOfVertices()];
		for (int i = 0; i < c.numOfVertices(); i++) {
			Vector2d vertice = new Vector2d(c.getVertices()[i].x, c.getVertices()[i].y);
			vertices[i] = vertice;
		}
		
		Vector2d[] axes = new Vector2d[c.numOfVertices()]; //get the axes to test;
		//loop over the vertices
		for (int i = 0; i < c.numOfVertices(); i++) {
			//get the current vertex
			Vector2d p1 = vertices[i];
			//get the next vertex
			Vector2d p2 = vertices[i + 1 == c.numOfVertices() ? 0 : i + 1];
			//subtract the two to get the edge vector
			Vector2d edge = p1.sub(p2);
			//get either perpendicular vector
			Vector2d normal = edge.perpendicularize();
			axes[i] = normal;
		}
		return axes;
	}
	
	private Projection project(Vector2d axis, Collider c) {
		Vector2d[] vertices = new Vector2d[c.numOfVertices()];
		for (int i = 0; i < c.numOfVertices(); i++) {
			Vector2d vertice = new Vector2d(c.getVertices()[i].x, c.getVertices()[i].y);
			vertices[i] = vertice;
		}
		
		double min = axis.dot(vertices[0]);
		double max = min;
		for (int i = 1; i < c.numOfVertices(); i++) {
			//NOTE: the axis must be normalized to get accurate projections
			double p = axis.dot(vertices[i]);
			if (p < min) {
				min = p;
			}else if (p > max) {
				max = p;
			}
		}
		Projection proj = new Projection(min, max);
		return proj;
	}
	
	public Vector2d[] getVertices() {
		return vertices;
	}

	public void update() {
		for (Vector2d v: vertices) {
			v.x += pose.getPosX() - posX;
			v.y += pose.getPosY() - posY;

			//v.rotate(pose.getAngle() - angle);
			
		}
		
		posX = pose.getPosX();
		posY = pose.getPosY();
		//angle = pose.getAngle();
		
	}
	
	int numOfVertices() {
		return vertices.length;
	}
	
	Vector2d getTxVertex(int idx, Vector2d result) {
		//what do i have to do???
		return result;
	}
	
	int numOfNormals() {
		//is there any case in which there are more/less normals than vertices??
		return vertices.length;
	}
	
	Vector2d getTxNormal(int idx, Vector2d result) {
		//what do i have to do???
		return result;
	}
	
	public void resetCollision() {
		collision = false;
	}

}
