package com.mygdx.game;

import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.BasicECS;

public class LevelGenerator {
	
	private static final int MAP_SIZE = 20;

	private EntityFactory entityFactory;
	private BasicECS ecs;

	public LevelGenerator(EntityFactory _entityFactory, BasicECS _ecs) {
		entityFactory = _entityFactory;
		ecs = _ecs;
	}


	public void createLevel1() {
		//AbstractEntity background = this.entityFactory.createImage("background.jpg", 0, 0, Settings.LOGICAL_WIDTH_PIXELS, Settings.LOGICAL_HEIGHT_PIXELS, -99);
		//ecs.addEntity(background);

		MazeGen1 mazegen = new MazeGen1(MAP_SIZE, MAP_SIZE, 0);
		for (int y=0 ; y<MAP_SIZE ; y++) {
			for (int x=0 ; x<MAP_SIZE ; x++) {
				if (mazegen.map[x][y] == false) {
					AbstractEntity wall = entityFactory.createWall(Settings.MAP_SQ_SIZE*x, Settings.MAP_SQ_SIZE*y, Settings.MAP_SQ_SIZE, Settings.MAP_SQ_SIZE);
					ecs.addEntity(wall);
				}
			}
		}
	}

}
