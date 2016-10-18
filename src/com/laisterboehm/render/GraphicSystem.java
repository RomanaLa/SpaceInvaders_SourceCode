package com.laisterboehm.render;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import at.fhooe.mtd.ecs.EngineSystem;
import at.fhooe.mtd.sgl.Sgl;

public class GraphicSystem extends EngineSystem{

	public static final String DEFAULT_CAMERA = "defaultCam";
	private List<GraphicSubsystem> subsystems = new ArrayList<>();
	private Map<String, Camera> cameras = new HashMap<>();
	
	public GraphicSystem() {
		createCamera(DEFAULT_CAMERA);
	}
	
	public boolean hasCamera(String name) {
		return cameras.containsKey(name);
	}
	
	public Camera getCamera() {
		return getCamera(DEFAULT_CAMERA);
	}
	
	public Camera getCamera(String name) {
		Camera result = cameras.get(name);
		if (result == null) {
			throw new IllegalArgumentException("unknown camera " + name);
		}
		return result;
	}
	
	public Camera createCamera(String name) {
		if (hasCamera(name)) {
			throw new IllegalArgumentException("ambiguous camera name " + name);
		}
		
		Camera result = new Camera(Sgl.graphics.getWidth(), Sgl.graphics.getHeight());
		cameras.put(name, result);
		return result;
	}
	
	public Camera getOrCreateCamera(String name) {
		if(hasCamera(name)) {
			return getCamera(name);
		}
		else {
			return createCamera(name);
		}
	}
	
	public void addSubsystem(GraphicSubsystem gs) {
		assert !subsystems.contains(gs): "GraphicSubsystem already added!";
		subsystems.add(gs);
	}
	
	public void removeSubsystem(GraphicSubsystem gs) {
		subsystems.remove(gs);
	}
	
	@Override
	public void update(double dt) {
		Graphics2D g = Sgl.graphics.beginUpdate();
		for (GraphicSubsystem gs: subsystems) {
			gs.render(g, dt);
		}
		Sgl.graphics.endUpdate();
	}

	

}
