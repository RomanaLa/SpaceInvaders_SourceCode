package com.laisterboehm.states;

import java.awt.Color;

import at.fhooe.mtd.ecs.Engine;
import at.fhooe.mtd.ecs.Entity;
import at.fhooe.mtd.sgl.Sgl;
import at.fhooe.mtd.sgl.app.Game;
import at.fhooe.mtd.sgl.app.GameState;
import at.fhooe.mtd.sgl.math.Vector2d;

import com.laisterboehm.collision.Collider;
import com.laisterboehm.collision.CollisionSystem;
import com.laisterboehm.collision.PosXDelimiterInvaders;
import com.laisterboehm.collision.PosXDelimiterShip;
import com.laisterboehm.components.Block;
import com.laisterboehm.components.Bottom;
import com.laisterboehm.components.StaticPose;
import com.laisterboehm.physics.Body;
import com.laisterboehm.physics.InvaderLogic;
import com.laisterboehm.physics.PhysicSystem;
import com.laisterboehm.physics.SpaceShipControl;
import com.laisterboehm.render.BoxVisual;
import com.laisterboehm.render.Camera;
import com.laisterboehm.render.GraphicSystem;
import com.laisterboehm.render.PolygonVisual;
import com.laisterboehm.render.VisualSystem;
import com.laisterboehm.update.UpdateSystem;

public class PlayState extends GameState{
	
	private Color color = Color.BLACK;
	private static boolean end = false;

	public PlayState(Game context) {
		super(context);
	}
	
	public void enter() {
		Sgl.graphics.setClearColor(color);
		create();
	}

	public void exit() {
		super.exit();
		
	}
	
	public Color getColor(){
		return color;
	}

	
	public static final String APP_NAME = "Space Invadors";
	private static final double VIEW_WIDTH = 800;
	private Engine engine;
	private Camera camera;


	public static void endOfGame() {
		end = true;
	}
	
	private void endGame() {
		LostState newState = new LostState(getContext());
		getContext().switchState(new BlendState(getContext(), newState, color, newState.getColor()));
		end = false;
		dispose();
	}
	private void wonGame() {
		WonState newState = new WonState(getContext());
		getContext().switchState(new BlendState(getContext(), newState, color, newState.getColor()));
	}
	
	public void create() {
		engine = new Engine();
		engine.addSystem(new UpdateSystem());
		engine.addSystem(new CollisionSystem());
		engine.addSystem(new PhysicSystem());
		engine.addSystem(new GraphicSystem());
		engine.addSystem(new VisualSystem());

		camera = engine.getSystem(GraphicSystem.class).getCamera();

		addBackgroundEntity(800, 600, Color.black);

		// add Space Ship
		Entity entity = new Entity();
		entity.addComponent(new PolygonVisual(new double[] { 0, 30, -20, -10,
				0, 0, 20, -10 }).color(Color.ORANGE).filled(true));
		entity.addComponent(new PosXDelimiterShip().width(VIEW_WIDTH)
				.componentWidth(40));
		entity.addComponent(new Body().pos(0, 190).angle(Math.toRadians(180))
				.mass(0.01).linearDamping(3));
		entity.addComponent(new SpaceShipControl().thrust(10));
		entity.addComponent(new Collider(new Vector2d(0, 30 + 190-60), new Vector2d(-20, -10 + 190 +20), new Vector2d(0,190), new Vector2d(20, -1 + 190+10)));
		engine.addEntity(entity);
		
		for (int i = 0; i < 4; i++) {	
			Color c1 = Color.WHITE;
			switch(i){
				case 0: c1 = Color.GREEN; break;
				case 1: c1 = Color.YELLOW; break;
				case 2: c1 = Color.CYAN; break;
				case 3: c1 = Color.RED; break;
			}		
			for (int j = 0; j < 8; j++) {				
				// add Invadors
				entity = new Entity();
				entity.addComponent(new PolygonVisual(new double[] { 0, 0, -15,
						-15, 0, -30, 15, -15 }).color(c1).filled(true));
				entity.addComponent(new Body()
						.pos(-280 + j * 45, -180 + i * 45).linearVelocity(0, 0));
				entity.addComponent(new InvaderLogic().pause(1).thrust(100));
				int x = -280 + j * 45;
				int y = -180 + i * 45;
				entity.addComponent(new Collider(new Vector2d(0 + x, 0 + y),
						new Vector2d(-15 + x, -15 + y), new Vector2d(0 + x, -30
								+ y), new Vector2d(15 + x, -15 + y)));
				entity.addComponent(new PosXDelimiterInvaders().width(VIEW_WIDTH - 150).componentWidth(50));
				engine.addEntity(entity);
			}
		}
		
		// adding protection blocks
		int posx = -300;
		int posy = 105;
		int blocks = 0;
		
		for(int i = 0; i < 2; i++){
			
			for(int j = 0; j < 12; j++){
				switch(j){
					case 0: posx = -300; blocks = 0; break;
					case 4: posx = -30; blocks = 0; break;
					case 8: posx = 230; blocks = 0; break;
				}
				entity = new Entity();
				entity.addComponent(new BoxVisual().color(Color.WHITE).filled(true).width(20).height(20));
				entity.addComponent(new StaticPose().posX(posx + blocks * 20).posY(posy));
				entity.addComponent(new Block());
				Collider c = new Collider();
				entity.addComponent(c);
				c.createBox(20, 20);
				engine.addEntity(entity);
				
				blocks++;
			}
			posy += 20;
			
		}
		
		//add bottom block
		entity = new Entity();
		//entity.addComponent(new BoxVisual().width(VIEW_WIDTH).height(50));
		entity.addComponent(new StaticPose().posX(0).posY(170));
		entity.addComponent(new Bottom());
		Collider c = new Collider();
		entity.addComponent(c);
		c.createBox(VIEW_WIDTH, 50);
		engine.addEntity(entity);
		
		

	}

	private void addBackgroundEntity(double w, double h, Color c) {
		Entity entity = new Entity();
		entity.addComponent(new BoxVisual().width(w).height(h).color(c)
				.filled(true));
		entity.addComponent(new StaticPose().posX(0).posY(0));
		engine.addEntity(entity);

	}

	public void dispose() {

		engine.dispose();
		engine = null;

	}

	public void update(double dt) {
		engine.update(dt);
		if(InvaderLogic.nrCurrentInvaders == 0) {
			wonGame();
		}
		if (end) {
			endGame();
		}
		
	}

	public void resize(int width, int height) {
		camera.setZoom(width / VIEW_WIDTH);

	}
}
