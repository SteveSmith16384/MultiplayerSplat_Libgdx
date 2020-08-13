package com.mygdx.game.datamodels;

import com.scs.basicecs.AbstractEntity;

public class CollisionResults {

	public AbstractEntity collidedWith;
	public boolean moveBack;
	
	public CollisionResults(AbstractEntity _collidedWith, boolean _fromAbove, boolean _moveBack) { // todo - remove _fromAbove
		collidedWith = _collidedWith;
		//fromAbove = _fromAbove;
		moveBack = _moveBack;
	}

}
