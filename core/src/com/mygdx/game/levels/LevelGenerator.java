package com.mygdx.game.levels;

import com.badlogic.gdx.math.GridPoint2;
import com.mygdx.game.EntityFactory;
import com.mygdx.game.Settings;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.BasicECS;

public class LevelGenerator implements ILevelData {
	
	private BasicECS ecs;
	private GridPoint2 start_pos;
	private int[][] map_data;
	
	public LevelGenerator(BasicECS _ecs) {
		ecs = _ecs;
	}


	public void createLevel() {
		map_data = new int[Settings.MAP_SIZE][Settings.MAP_SIZE];
		MazeGen1 mazegen = new MazeGen1(Settings.MAP_SIZE, Settings.MAP_SIZE, 0);

		for (int y=0 ; y<Settings.MAP_SIZE ; y++) {
			for (int x=0 ; x<Settings.MAP_SIZE ; x++) {
				this.map_data[x][y] = mazegen.map[x][y] ? ILevelData.FLOOR : ILevelData.WALL;
			}
		}

		this.start_pos = mazegen.start_pos;
	}


	@Override
	public GridPoint2 getStartPosition() {
		return start_pos;
	}


	@Override
	public int getSquareType(int x, int y) {
		return map_data[x][y];
	}

}
