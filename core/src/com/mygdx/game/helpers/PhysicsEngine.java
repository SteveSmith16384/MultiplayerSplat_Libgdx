package com.mygdx.game.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class PhysicsEngine { // todo - delete

	private Vector2 dir;
	public Vector2 offset;
	private float speed, grav;

	public PhysicsEngine(Vector2 _dir, float _speed, float _grav) {
		super();
		
		dir = _dir;
		speed = _speed;
		grav = _grav;
		offset = new Vector2();
	}
	
	
	public void process() {
		offset.x = dir.x * speed * Gdx.graphics.getDeltaTime();
		offset.y = dir.y * speed * Gdx.graphics.getDeltaTime();

		dir.y += grav; //Statics.ROCK_GRAVITY;
	}

}
