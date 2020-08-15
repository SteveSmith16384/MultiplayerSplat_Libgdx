package com.mygdx.game.datamodels;

import com.mygdx.game.Settings;
import com.mygdx.game.input.IPlayerInput;
import com.scs.basicecs.AbstractEntity;

public class PlayerData {

	public static int nextPlayerId = 1;

	public IPlayerInput controller;
	public boolean quit = false; // If they've removed their controller; prevent them re-attaching to start again
	public AbstractEntity avatar;
	public float timeUntilAvatar;
	public int score;
	public int lives;
	public int playerIdx = -1;

	public PlayerData(IPlayerInput _controller) {
		this.controller = _controller;
	}


	public void init() {
		this.lives = Settings.START_LIVES;
		this.score = 0;
		if (playerIdx < 0) {
			playerIdx = nextPlayerId++;
		}
		timeUntilAvatar = 0;
	}

}
