package com.mygdx.game.systems;

import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Settings;
import com.mygdx.game.components.CollectableComponent;
import com.mygdx.game.components.PlayersAvatarComponent;
import com.scs.basicecs.AbstractEntity;

public class CollectorSystem {

	private MyGdxGame game;

	public CollectorSystem(MyGdxGame _game) {
		game = _game;
	}


	public void entityCollected(AbstractEntity collector, AbstractEntity coin) {
		coin.remove();

		CollectableComponent cc = (CollectableComponent)coin.getComponent(CollectableComponent.class);
		switch (cc.type) {
		case Coin:
			PlayersAvatarComponent uic = (PlayersAvatarComponent)collector.getComponent(PlayersAvatarComponent.class);
			game.sfx.play("sfx/Collect_Point_00.wav");
			if (uic != null) {
				uic.player.score += 1;
				if (uic.player.score >= Settings.WINNING_COINS) {
					game.setWinner(uic.player.playerIdx);
				}
			}
			game.ecs.addEntity(game.entityFactory.createRisingCoin(coin));
			break;
		default:
			throw new RuntimeException("Unknown collectable type: " + cc.type);
		}
	}

}

