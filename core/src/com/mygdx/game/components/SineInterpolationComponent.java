package com.mygdx.game.components;

public class SineInterpolationComponent {

	public float elapsed;
	public float startY;
	public float extraY;
	public float duration;
	public float end_y;
	
	public SineInterpolationComponent(float _duration, float _end_y) {
		duration = _duration;
		end_y =_end_y;
	}
}
