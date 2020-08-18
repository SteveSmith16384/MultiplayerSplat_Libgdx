package com.mygdx.game.components;

import com.scs.awt.RectF;

public class PositionComponent {

	public RectF rect; // Note that this is NOT the screen co-ords!
	public RectF prevPos;
	
	public static PositionComponent ByBottomLeft(float x, float y, float w, float h) {
		PositionComponent pos = new PositionComponent();
		pos.rect = new RectF(x, y+h, x+(w), y);
		return pos;
	}


	private PositionComponent() {
		prevPos = new RectF();
	}

}
