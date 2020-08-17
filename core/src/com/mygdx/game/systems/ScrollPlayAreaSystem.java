package com.mygdx.game.systems;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Settings;
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
		float delta = Math.min(Gdx.graphics.getDeltaTime(), 1);
		// Have we reached the top?
		if (game.screen_cam_y > (Settings.MAP_HEIGHT * Settings.MAP_SQ_SIZE)-(Settings.LOGICAL_HEIGHT_PIXELS/3)) {
			game.endOfLevel();
			return;
		}
		
		if (System.currentTimeMillis() > this.next_Speed_up) {
			game.scroll_speed += 1;
			this.next_Speed_up = System.currentTimeMillis() + SPEED_UP_INTERVAL;
		}
		//game.screen_cam_x += 20f * Gdx.graphics.getDeltaTime();
		game.screen_cam_y += game.scroll_speed * delta;
	}


}
