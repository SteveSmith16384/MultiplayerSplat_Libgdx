package com.mygdx.game.systems;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Settings;
import com.scs.basicecs.ISystem;

public class ScrollPlayAreaSystem implements ISystem {
	
	public enum Direction {Left, Right, Up};
	
	private static final long SPEED_UP_INTERVAL = 10*1000;

	private MyGdxGame game;
	private long next_Speed_up;
	private Direction current_dir = Direction.Right;
	private Direction prev_dir_lr = Direction.Right;
	private float end_of_up;
	
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
			MyGdxGame.p("Scroll speed is now " + game.scroll_speed);
		}
		
		if (Settings.SIMPLE_SCROLLING) {
			game.screen_cam_y += game.scroll_speed * delta;
		} else {
			switch (this.current_dir) {
			case Left:
				game.screen_cam_x -= game.scroll_speed * delta;
				if (game.screen_cam_x < Settings.LOGICAL_WIDTH_PIXELS/2) {
					this.current_dir = Direction.Up;
					this.prev_dir_lr = Direction.Left;
					end_of_up = this.game.screen_cam_y + (Settings.LOGICAL_HEIGHT_PIXELS);
				}
				break;
			case Right:
				game.screen_cam_x += game.scroll_speed * delta;
				if (game.screen_cam_x > (Settings.MAP_WIDTH * Settings.MAP_SQ_SIZE)-(Settings.LOGICAL_WIDTH_PIXELS/3)) {
					this.current_dir = Direction.Up;
					this.prev_dir_lr = Direction.Right;
					end_of_up = this.game.screen_cam_y + (Settings.LOGICAL_HEIGHT_PIXELS);
				}
				break;
			case Up:
				game.screen_cam_y += game.scroll_speed * delta;
				if (game.screen_cam_y > this.end_of_up) {
					if (this.prev_dir_lr == Direction.Left) {
						this.current_dir = Direction.Right;
					} else {
						this.current_dir = Direction.Left;
					}
				}
				break;
			default:
				throw new RuntimeException ("Todo");
			
			}
		}
	}


}
