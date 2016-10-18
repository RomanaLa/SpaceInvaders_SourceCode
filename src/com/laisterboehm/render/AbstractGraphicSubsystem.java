package com.laisterboehm.render;

import at.fhooe.mtd.ecs.Engine;
import at.fhooe.mtd.ecs.EngineSystem;

public abstract class AbstractGraphicSubsystem extends EngineSystem implements GraphicSubsystem{

	private String cameraName = GraphicSystem.DEFAULT_CAMERA;
	protected Camera camera;
	
	AbstractGraphicSubsystem cameraName(String name) {
		cameraName = name;
		return this;
	}
	
	public String getCameraName() {
		return cameraName;
	}

	public final void addedToEngine(Engine e) {
		GraphicSystem gfx = e.getSystem(GraphicSystem.class);
		gfx.addSubsystem(this);
		camera = gfx.getCamera(cameraName);
		initialize(e);
	}

	public final void removedFromEngine(Engine e) {
		cleanUp(e);
		e.getSystem(GraphicSystem.class).removeSubsystem(this);
		camera = null;
	}

	protected void initialize(Engine e) {};
	protected void cleanUp(Engine e) {};
	public final void update(double dt) {};
	
}
