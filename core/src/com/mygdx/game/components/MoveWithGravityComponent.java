package com.mygdx.game.components;

import com.badlogic.gdx.math.Vector2;
import com.scs.lang.NumberFunctions;

public class MoveWithGravityComponent {

	public Vector2 dir;

	public MoveWithGravityComponent() {
		dir = new Vector2(NumberFunctions.rndFloat(-30, 30), NumberFunctions.rndFloat(20, 40));
	}

}
