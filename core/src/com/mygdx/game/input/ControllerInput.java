package com.mygdx.game.input;

import com.badlogic.gdx.controllers.Controller;

public class ControllerInput implements IPlayerInput {

	public Controller controller;
	
	public ControllerInput(Controller _controller) {
		controller = _controller;
	}

	@Override
	public boolean isLeftPressed() {
		return controller.getAxis(0) < -0.5f;
	}

	
	@Override
	public boolean isRightPressed() {
		return controller.getAxis(0) > 0.5f;
	}

	
	@Override
	public boolean isUpPressed() {
		return controller.getAxis(1) < -0.5f;
	}

	
	@Override
	public boolean isDownPressed() {
		return controller.getAxis(1) > 0.5f;
	}

	
}
