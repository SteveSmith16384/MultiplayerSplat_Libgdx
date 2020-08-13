package com.mygdx.game.levels;

import com.badlogic.gdx.math.GridPoint2;

public interface ILevelData {

	void createLevel();
	
	GridPoint2 getStartPosition();
}
