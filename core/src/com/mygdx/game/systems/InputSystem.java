package com.mygdx.game.systems;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Settings;
import com.mygdx.game.components.MovementComponent;
import com.mygdx.game.components.PlayersAvatarComponent;
import com.mygdx.game.datamodels.PlayerData;
import com.mygdx.game.input.KeyboardInput;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;

public class InputSystem extends AbstractSystem {

	private MyGdxGame game;

	public InputSystem(MyGdxGame _game, BasicECS ecs) {
		super(ecs, PlayersAvatarComponent.class);

		game = _game;
	}


	@Override
	public void process() {
		// Process keys
		if (Gdx.input.isKeyJustPressed(Keys.F1)) {
			if (Gdx.app.getType() == ApplicationType.WebGL) {
				if (!Gdx.graphics.isFullscreen()) {
					Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayModes()[0]);
				}
			} else {
				game.toggleFullscreen = true;
			}
		}

		if (Gdx.input.isKeyJustPressed(Keys.S) && game.gameStage != 0) { // S to start
			game.startNextStage();
		}

		if (game.gameStage == -1) {
			if (Gdx.input.isKeyJustPressed(Keys.SPACE)) { // Space for keyboard player to join
				if (game.keyboard_joined == false) {
					game.keyboard_joined = true;
					KeyboardInput ki = new KeyboardInput();
					game.players.put(ki, new PlayerData(ki)); // Create keyboard player by default (they might not actually join though!)

					MyGdxGame.p("Keyboard player joined");
					//player.setInGame(true);
				}
			}

		} else if (game.gameStage == 0) {
			super.process();
		}
	}


	@Override
	public void processEntity(AbstractEntity entity) {
		PlayersAvatarComponent uic = (PlayersAvatarComponent)entity.getComponent(PlayersAvatarComponent.class);
		if (uic != null) {
			MovementComponent mc = (MovementComponent)entity.getComponent(MovementComponent.class);

			if (uic.controller.isLeftPressed()) {
				mc.offX = -Settings.PLAYER_SPEED;
			} else if (uic.controller.isRightPressed()) {
				mc.offX = Settings.PLAYER_SPEED;
			}
			if (uic.controller.isUpPressed()) {
				mc.offY = Settings.PLAYER_SPEED;
			} else if (uic.controller.isDownPressed()) {
				mc.offY = -Settings.PLAYER_SPEED;
			}

		}
	}


}
