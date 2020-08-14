package com.mygdx.game.levels;

import com.badlogic.gdx.math.GridPoint2;

public interface ILevelData {
	
	public static final int EMPTY = 0;
	public static final int WALL = 1;
	public static final int COIN = 2;

	void createLevel();
	
	int getSquareType(int x, int y);
	
	GridPoint2 getStartPosition();
}
