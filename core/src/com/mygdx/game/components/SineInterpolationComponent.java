package com.mygdx.game.components;

public class SineInterpolationComponent {

	public float elapsed;
	public float startY;
	public float extraY;
	public float duration;
	public float final_offset_y;
	
	public SineInterpolationComponent(float _duration, float _final_offset_y) {
		duration = _duration;
		final_offset_y = _final_offset_y;
	}
	
}
