package com.mygdx.game.components;

public class CollisionComponent {
	
	public boolean alwaysCollides;
	public boolean blocksMovement;

	public CollisionComponent(boolean _alwaysCollides, boolean _blocksMovement) {
		alwaysCollides = _alwaysCollides;
		blocksMovement = _blocksMovement;
	}

}
