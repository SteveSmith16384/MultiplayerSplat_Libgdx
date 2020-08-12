package com.mygdx.game.input;

import com.badlogic.gdx.Input.Keys;
import com.mygdx.game.systems.InputSystem;

public class KeyboardInput implements IPlayerInput {

	private InputSystem inputSystem;
	
	public KeyboardInput(InputSystem _inputSystem) {
		inputSystem = _inputSystem;
	}

	@Override
	public boolean isLeftPressed() {
		return this.inputSystem.key[29];
	}

	
	@Override
	public boolean isRightPressed() {
		return this.inputSystem.key[32];
	}

	
	@Override
	public boolean isUpPressed() {
		return this.inputSystem.key[Keys.UP];
	}

	
	@Override
	public boolean isDownPressed() {
		return this.inputSystem.key[Keys.DOWN];
	}

	
}
