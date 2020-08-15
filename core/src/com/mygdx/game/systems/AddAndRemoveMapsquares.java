package com.mygdx.game.systems;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Settings;
import com.mygdx.game.levels.ILevelData;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.BasicECS;
import com.scs.basicecs.ISystem;

public class AddAndRemoveMapsquares implements ISystem {

	private static final long INTERVAL = 1000;

	private MyGdxGame game;
	private BasicECS ecs;
	private AbstractEntity[][] map_ents;
	private long next_check_time = 0;

	private Vector2 tmpVec2 = new Vector2();

	public AddAndRemoveMapsquares(MyGdxGame _game, BasicECS _ecs) {
		game = _game;
		ecs = _ecs;

		map_ents = new AbstractEntity[Settings.MAP_WIDTH][Settings.MAP_HEIGHT];
	}


	public void runNow() {
		this.next_check_time = 0;
	}
	
	
	@Override
	public void process() {
		if (System.currentTimeMillis() < this.next_check_time) {
			return;
		}

		next_check_time = System.currentTimeMillis() + INTERVAL;

		for (int y=0 ; y<Settings.MAP_HEIGHT ; y++) {
			for (int x=0 ; x<Settings.MAP_WIDTH ; x++) {
				int type = game.level_data.getSquareType(x, y);
				if (type != ILevelData.EMPTY) {
					game.getScreenCoords(x*Settings.MAP_SQ_SIZE, y*Settings.MAP_SQ_SIZE, tmpVec2);
					// Add squares
					if (map_ents[x][y] == null) {
						if (tmpVec2.x < 0 || tmpVec2.x > Settings.LOGICAL_WIDTH_PIXELS) {
							continue;
						} else if (tmpVec2.y < 0 || tmpVec2.y > Settings.LOGICAL_HEIGHT_PIXELS) {//-DrawInGameGuiSystem.WALL_WIDTH) {
							continue;
						}

						//MyGdxGame.p("Creating map for " + x + ",  " + y);

						if (type == ILevelData.WALL) {
							AbstractEntity wall = game.entityFactory.createWall(Settings.MAP_SQ_SIZE*x, Settings.MAP_SQ_SIZE*y, Settings.MAP_SQ_SIZE, Settings.MAP_SQ_SIZE);
							ecs.addEntity(wall);
							map_ents[x][y] = wall;
						} else if (type == ILevelData.COIN) {
							AbstractEntity coin = game.entityFactory.createCoin(Settings.MAP_SQ_SIZE*x, Settings.MAP_SQ_SIZE*y);
							ecs.addEntity(coin);
							map_ents[x][y] = coin;
							game.level_data.removeSquare(x, y);
						} else {
							throw new RuntimeException("Unknown type: " + type);
						}
					} else {
						if (map_ents[x][y].isMarkedForRemoval()) {
							map_ents[x][y].remove();
							map_ents[x][y] = null;
							continue;
						}
						// Remove ents
						if (tmpVec2.x > -Settings.MAP_SQ_SIZE*2 && tmpVec2.x < Settings.LOGICAL_WIDTH_PIXELS+(Settings.MAP_SQ_SIZE*2) &&
								tmpVec2.y > -Settings.MAP_SQ_SIZE*2 && tmpVec2.y < Settings.LOGICAL_HEIGHT_PIXELS+(Settings.MAP_SQ_SIZE*2)) {
							continue;
						}

						//MyGdxGame.p("Removing map for " + x + ",  " + y);

						map_ents[x][y].remove();
						map_ents[x][y] = null;
					}
				}
			}
		}

	}

}
