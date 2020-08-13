package com.mygdx.game.systems;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.MyGdxGame;
import com.scs.basicecs.ISystem;

public class ScrollPlayAreaSystem implements ISystem {

	private MyGdxGame game;

	public ScrollPlayAreaSystem(MyGdxGame _game) {
		game = _game;
	}


	@Override
	public void process() {
		game.screen_cam_x += 10f * Gdx.graphics.getDeltaTime();
		game.screen_cam_y += 10f * Gdx.graphics.getDeltaTime();
	}


}