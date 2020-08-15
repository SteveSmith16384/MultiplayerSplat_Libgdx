package com.mygdx.game.systems;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.MyGdxGame;
import com.scs.basicecs.ISystem;

public class ScrollPlayAreaSystem implements ISystem {
	
	private static final long SPEED_UP_INTERVAL = 10*1000;

	private MyGdxGame game;
	private long next_Speed_up;
	
	public ScrollPlayAreaSystem(MyGdxGame _game) {
		game = _game;
	}


	@Override
	public void process() {
		if (System.currentTimeMillis() > this.next_Speed_up) {
			game.scroll_speed += 1;
			this.next_Speed_up = System.currentTimeMillis() + SPEED_UP_INTERVAL;
		}
		//game.screen_cam_x += 20f * Gdx.graphics.getDeltaTime();
		game.screen_cam_y += game.scroll_speed * Gdx.graphics.getDeltaTime();
	}


}
