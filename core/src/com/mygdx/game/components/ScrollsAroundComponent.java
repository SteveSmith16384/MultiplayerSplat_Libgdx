package com.mygdx.game.components;

/**
 * For entities that move around as the play area is scrolled.
 *
 */
public class ScrollsAroundComponent { // todo - delete this?
	
	public boolean removeWhenNearEdge; // otherwise, removed only when off-screen
	
	public ScrollsAroundComponent(boolean _removeWhenNearEdge) {
		removeWhenNearEdge = _removeWhenNearEdge;		
	}

}
