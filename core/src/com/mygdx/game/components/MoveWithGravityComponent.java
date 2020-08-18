package com.mygdx.game.components;

import com.badlogic.gdx.math.Vector2;
import com.scs.lang.NumberFunctions;

public class MoveWithGravityComponent {

	public Vector2 dir;
	//public float speed;

	public MoveWithGravityComponent() {
		dir = new Vector2(NumberFunctions.rndFloat(-30, 30), 30);
		//dir = new Vector2(3000, 2000);
		//speed = 200;
	}

}
