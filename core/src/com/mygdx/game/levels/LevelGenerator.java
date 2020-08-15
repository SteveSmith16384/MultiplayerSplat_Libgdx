package com.mygdx.game.levels;

import com.badlogic.gdx.math.GridPoint2;
import com.mygdx.game.EntityFactory;
import com.mygdx.game.Settings;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.BasicECS;

public class LevelGenerator implements ILevelData {

	private BasicECS ecs;
	private GridPoint2[] start_pos;
	private int[][] map_data;

	public LevelGenerator(BasicECS _ecs) {
		ecs = _ecs;
	}


	public void createLevel() {
		map_data = new int[Settings.MAP_WIDTH][Settings.MAP_HEIGHT];
		MazeGen1 mazegen = new MazeGen1(Settings.MAP_WIDTH, Settings.MAP_HEIGHT, 0);

		for (int y=0 ; y<Settings.MAP_HEIGHT ; y++) {
			for (int x=0 ; x<Settings.MAP_WIDTH ; x++) {
				if (y == 1 && x>0 && x<Settings.MAP_WIDTH-1) {
					this.map_data[x][y] = ILevelData.EMPTY; // Clear space for start pos
				} else {
					this.map_data[x][y] = mazegen.map[x][y] ? ILevelData.COIN : ILevelData.WALL;
				}
			}
		}

		this.start_pos = new GridPoint2[Settings.MAX_PLAYERS];
		for (int i=0 ; i<Settings.MAX_PLAYERS ; i++) {
			start_pos[i] = new GridPoint2(i*3+1, 1);
		}
	}


	@Override
	public GridPoint2 getStartPosition(int idx) {
		return start_pos[idx];
	}


	@Override
	public int getSquareType(int x, int y) {
		return map_data[x][y];
	}

}
