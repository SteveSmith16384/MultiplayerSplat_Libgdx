package com.mygdx.game.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.GridPoint2;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Settings;
import com.mygdx.game.datamodels.PlayerData;
import com.mygdx.game.input.IPlayerInput;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.ISystem;

public class ProcessPlayersSystem implements ISystem {

	private MyGdxGame game;

	public ProcessPlayersSystem(MyGdxGame _game) {
		game = _game;
	}


	public void process() {
		// Create avatars for players
		for (PlayerData player : game.players.values()) {
			if (player.quit == false) {
				if (player.lives > 0) {
					if (player.avatar == null) {
						player.timeUntilAvatar -= Gdx.graphics.getDeltaTime();
						if (player.timeUntilAvatar <= 0) {
							createPlayersAvatar(player, player.controller);
						}
					}
				}
			}
		}

		// Check for winner
		int winner = -1;
		int highestScore = -1;

		for (PlayerData player : game.players.values()) {
			if (player.lives <= 0) {
				if (player.score > highestScore) {
					highestScore = player.score;
					winner = player.imageId;
				}
			} else {
				return;
			}
		}

		game.setWinner(winner);

	}


	private void createPlayersAvatar(PlayerData player, IPlayerInput controller) {
		GridPoint2 start_pos = game.level.getStartPosition();
		AbstractEntity avatar = game.entityFactory.createPlayersAvatar(player, controller, start_pos.x*Settings.MAP_SQ_SIZE, start_pos.y*Settings.MAP_SQ_SIZE);
		game.ecs.addEntity(avatar);

		player.avatar = avatar;
	}

}
