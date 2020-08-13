package com.mygdx.game.components;

import com.mygdx.game.datamodels.PlayerData;
import com.mygdx.game.input.IPlayerInput;

public class PlayersAvatarComponent {

	public PlayerData player;
	public IPlayerInput controller;
	public boolean moveLeft, moveRight, moveUp, moveDown;
	public long timeStarted;
	
	public PlayersAvatarComponent(PlayerData _player, IPlayerInput _controller) {
		player = _player;
		controller = _controller;
		
		timeStarted = System.currentTimeMillis();
	}
	
}
