package com.mygdx.game.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.mygdx.game.systems.InputSystem;

public class KeyboardInput implements IPlayerInput {

	@Override
	public boolean isLeftPressed() {
		return Gdx.input.isKeyPressed(Keys.LEFT);
	}

	
	@Override
	public boolean isRightPressed() {
		return Gdx.input.isKeyPressed(Keys.RIGHT);
	}

	
	@Override
	public boolean isUpPressed() {
		return Gdx.input.isKeyPressed(Keys.UP);
	}

	
	@Override
	public boolean isDownPressed() {
		return Gdx.input.isKeyPressed(Keys.DOWN);
	}

	
}
