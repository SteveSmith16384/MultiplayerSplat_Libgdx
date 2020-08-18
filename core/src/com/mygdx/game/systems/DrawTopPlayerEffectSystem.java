package com.mygdx.game.systems;

import com.mygdx.game.MyGdxGame;
import com.mygdx.game.components.ImageComponent;
import com.mygdx.game.datamodels.PlayerData;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.BasicECS;
import com.scs.basicecs.ISystem;

public class DrawTopPlayerEffectSystem implements ISystem {

	private MyGdxGame game;
	private BasicECS ecs;
	private long next_check_time;

	public DrawTopPlayerEffectSystem(MyGdxGame _game, BasicECS _ecs) {
		//super(ecs, MakeSpriteSmallerComponent.class);
		game = _game;
		ecs = _ecs;
	}


	@Override
	public void process() {
		if (System.currentTimeMillis() < next_check_time) {
			return;
		}
		this.next_check_time = System.currentTimeMillis() + 500;
		//	float delta = Gdx.graphics.getDeltaTime();

		PlayerData winner = null;
		int highestScore = -1;
		for (PlayerData p : game.players.values()) {
			if (p.lives > 0) {
				if (p.score > highestScore) {
					highestScore = p.score;
					winner = p;
				}
			}
		}

		//PositionComponent posData = (PositionComponent)winner.avatar.getComponent(PositionComponent.class);
		if (winner != null && winner.avatar != null) {
			AbstractEntity flame = game.entityFactory.createTopPlayerEffect(winner.avatar);
			ecs.addEntity(flame);

			// todo - remove this
			/*ImageComponent img = (ImageComponent)winner.avatar.getComponent(ImageComponent.class);
			if (img != null && img.sprite != null) {
				AbstractEntity deleteme = game.entityFactory.createExplodingCoin(img.sprite.getX(), img.sprite.getY());
				ecs.addEntity(deleteme);
			}*/
		}
	}

}
