package com.laisterboehm.physics;

import com.laisterboehm.components.MutablePose;

import at.fhooe.mtd.ecs.Component;

public class Body extends Component implements MutablePose{
	
	private static final int X_IDX = 0;
	private static final int Y_IDX = 1;
	private static final int ANG_IDX = 2;
	
	private double pos[] = new double[3];
	private double vel[] = new double[3];
	private double acc[] = new double[3];
	private double dmp[] = new double [3];
	//mass of the body
	private double mass = 1;
	//moment of inertia of the body
	private double moi = 1;
	
	public Body pos(double x, double y) {
		setPosX(x);
		setPosY(y);
		return this;
	}
	
	public Body mass(double m) {
		if (m <=0) {
			throw new IllegalArgumentException("Mass must be > 0.");
		}
		mass = m;
		return this;
	}
	
	public Body moi(double j) {
		if (j <=0) {
			throw new IllegalArgumentException("MOI must be > 0.");
		}
		moi = j;
		return this;
	}
	
	public Body angle(double rad) {
		setAngle(rad);
		return this;
	}
	
	public Body linearVelocity(double vx, double vy)
	{
		setLinearVelocity(vx, vy);
		return this;
	}
	public Body angularVelocity(double radPerSecond) {
		setAngularVelocity(radPerSecond);
		return this;
	}
	
	public Body linearDamping(double ld) {
		dmp[X_IDX] = dmp [Y_IDX] = ld;
		return this;
	}
	
	public Body angularDamping (double ad) {
		dmp[ANG_IDX] = ad;
		return this;
	}
	
	@Override
	protected void activate() {
		super.activate();
		getSystem(PhysicSystem.class).addBody(this);
	}

	@Override
	protected void deactivate() {
		super.deactivate();
		getSystem(PhysicSystem.class).removeBody(this);
	}

	public void update(double dt) {
		// Semi-implicit Euler step (with damping)
		for (int i = 0; i < 3; ++i) {
			vel[i] += (acc[i] - dmp[i] * vel[i]) * dt;
			pos[i] += vel[i] * dt;
		}
		
	}
	
	public void setLinearVelocity(double vx, double vy) {
		vel[X_IDX] = vx;
		vel[Y_IDX] = vy;
	}
	
	public double[] getLinearVelocity() {
		return (new double[]{vel[X_IDX], vel[Y_IDX]});
	}
	
	public void setAngularVelocity(double radPerSecond) {
		vel[ANG_IDX] = radPerSecond;
	}
	
	public void applyForce(double fx, double fy) {
		//add force to net force
		acc[X_IDX] += fx /mass;
		acc[Y_IDX] += fy /mass;
	}
	
	public void applyTorque(double t) {
		acc[ANG_IDX] = t;
	}
	
	void clearForces() {
		acc[X_IDX] = acc[Y_IDX] = acc[ANG_IDX] = 0;
	}
	
	void clearVelocity() {
		vel[X_IDX] = vel[Y_IDX] = vel[ANG_IDX] = 0;
	}
	
	///////////////////////////////////////
	//Interface Mutable Pose
	///////////////////////////////////////
	
	@Override
	public double getPosX() {
		return pos[X_IDX];
	}

	@Override
	public double getPosY() {
		return pos[Y_IDX];
	}

	@Override
	public double getAngle() {
		return pos[ANG_IDX];
	}

	@Override
	public void setPosX(double x) {
		pos[X_IDX] = x;
		
	}

	@Override
	public void setPosY(double y) {
		pos[Y_IDX] = y;
		
	}

	@Override
	public void setAngle(double rad) {
		pos[ANG_IDX] = rad;
		
	}

	

}
