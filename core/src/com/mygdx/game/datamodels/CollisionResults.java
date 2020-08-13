package com.mygdx.game.datamodels;

import com.scs.basicecs.AbstractEntity;

public class CollisionResults {

	public AbstractEntity collidedWith;
	public boolean moveBack;
	
	public CollisionResults(AbstractEntity _collidedWith, boolean _moveBack) {
		collidedWith = _collidedWith;
		moveBack = _moveBack;
	}

}
