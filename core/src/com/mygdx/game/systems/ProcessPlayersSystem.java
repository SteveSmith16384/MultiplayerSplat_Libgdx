package com.mygdx.game.systems;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Settings;
import com.mygdx.game.datamodels.PlayerData;
import com.mygdx.game.input.IPlayerInput;
import com.scs.awt.RectF;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.ISystem;

public class ProcessPlayersSystem implements ISystem {

	private MyGdxGame game;
	
	public ProcessPlayersSystem(MyGdxGame _game) {
		game = _game;
	}


	@Override
	public void process() {
		// Create avatars for players
		for (PlayerData player : game.players.values()) {
			if (player.quit == false) {
				if (player.lives > 0) {
					if (player.avatar == null || player.avatar.isMarkedForRemoval()) {
						player.timeUntilAvatar -= Gdx.graphics.getDeltaTime();
						if (player.timeUntilAvatar <= 0) {
							createPlayersAvatar(player, player.controller);
						}
					}
				}
			}
		}
		
	}


	private void createPlayersAvatar(PlayerData player, IPlayerInput controller) {
		/*GridPoint2 start_pos = game.level_data.getStartPosition(player.playerIdx);
		AbstractEntity avatar = game.entityFactory.createPlayersAvatar(player, controller, start_pos.x*Settings.MAP_SQ_SIZE, start_pos.y*Settings.MAP_SQ_SIZE);
		game.ecs.addEntity(avatar);*/

		int y = (int)((int)(game.screen_cam_y/Settings.MAP_SQ_SIZE)*Settings.MAP_SQ_SIZE);
		RectF r = new RectF(0, y+Settings.PLAYER_SIZE, Settings.PLAYER_SIZE, y);
		for (int x = (int)Settings.MAP_SQ_SIZE ; x<Settings.LOGICAL_WIDTH_PIXELS-(Settings.MAP_SQ_SIZE*2) ; x++) {
			r.setLeft(x);
			if (game.collisionSystem.isAreaClear(r)) {
				AbstractEntity avatar = game.entityFactory.createPlayersAvatar(player, controller, r.left, r.bottom);
				game.ecs.addEntity(avatar);
				player.avatar = avatar;
				break;
			}
		}
		
	}

}
