package com.mygdx.game.levels;

import com.badlogic.gdx.math.GridPoint2;
import com.mygdx.game.EntityFactory;
import com.mygdx.game.Settings;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.BasicECS;

public class LevelGenerator implements ILevelData {
	
	private static final int MAP_SIZE = 20;

	private EntityFactory entityFactory;
	private BasicECS ecs;
	private GridPoint2 start_pos;

	public LevelGenerator(EntityFactory _entityFactory, BasicECS _ecs) {
		entityFactory = _entityFactory;
		ecs = _ecs;
	}


	public void createLevel() {
		MazeGen1 mazegen = new MazeGen1(MAP_SIZE, MAP_SIZE, 0);
		for (int y=0 ; y<MAP_SIZE ; y++) {
			for (int x=0 ; x<MAP_SIZE ; x++) {
				if (mazegen.map[x][y] == false) {
					AbstractEntity wall = entityFactory.createWall(Settings.MAP_SQ_SIZE*x, Settings.MAP_SQ_SIZE*y, Settings.MAP_SQ_SIZE, Settings.MAP_SQ_SIZE);
					ecs.addEntity(wall);
				} else {
					AbstractEntity wall = entityFactory.createCoin(Settings.MAP_SQ_SIZE*x, Settings.MAP_SQ_SIZE*y);
					ecs.addEntity(wall);
				}
			}
		}
		
		this.start_pos = mazegen.start_pos;
	}


	@Override
	public GridPoint2 getStartPosition() {
		return start_pos;
	}

}
