package com.mygdx.game;

import com.scs.basicecs.BasicECS;

public class LevelGenerator {

	private EntityFactory entityFactory;
	private BasicECS ecs;

	public LevelGenerator(EntityFactory _entityFactory, BasicECS _ecs) {
		entityFactory = _entityFactory;
		ecs = _ecs;
	}


	public void createLevel1() {
		//AbstractEntity background = this.entityFactory.createImage("background.jpg", 0, 0, Settings.LOGICAL_WIDTH_PIXELS, Settings.LOGICAL_HEIGHT_PIXELS, -99);
		//ecs.addEntity(background);

	}

}
