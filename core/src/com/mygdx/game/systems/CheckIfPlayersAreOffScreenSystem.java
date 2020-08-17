package com.mygdx.game.systems;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Settings;
import com.mygdx.game.components.PlayersAvatarComponent;
import com.mygdx.game.components.PositionComponent;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;

public class CheckIfPlayersAreOffScreenSystem extends AbstractSystem {

	private MyGdxGame game;
	
	private Vector2 tmpVec2 = new Vector2();

	public CheckIfPlayersAreOffScreenSystem(MyGdxGame _game, BasicECS ecs) {
		super(ecs, PlayersAvatarComponent.class);
		
		game = _game;
	}


	@Override
	public void processEntity(AbstractEntity entity) {
		PositionComponent posData = (PositionComponent)entity.getComponent(PositionComponent.class);
		game.getScreenCoords(posData.rect.getX(), posData.rect.getY(), tmpVec2);
		if (tmpVec2.x < DrawInGameGuiSystem.WALL_WIDTH || tmpVec2.x + posData.rect.width() > Settings.LOGICAL_WIDTH_PIXELS-DrawInGameGuiSystem.WALL_WIDTH) {
			game.playerKilled(entity);
		} else if (tmpVec2.y < DrawInGameGuiSystem.WALL_HEIGHT || tmpVec2.y + posData.rect.height() > Settings.LOGICAL_HEIGHT_PIXELS-DrawInGameGuiSystem.WALL_WIDTH) {
			game.playerKilled(entity);
		}

	}

}
