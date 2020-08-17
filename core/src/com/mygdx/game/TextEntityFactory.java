package com.mygdx.game;

import com.mygdx.game.components.ImageComponent;
import com.mygdx.game.components.PositionComponent;
import com.mygdx.game.components.SineInterpolationComponent;
import com.scs.basicecs.AbstractEntity;

public class TextEntityFactory {

	public static AbstractEntity createMultiplayer(MyGdxGame game) {
		AbstractEntity e = new AbstractEntity(game.ecs, "Multiplayer");

		float w = Settings.LOGICAL_WIDTH_PIXELS * 0.8f;
		float h = Settings.LOGICAL_HEIGHT_PIXELS * 0.3f;
		ImageComponent imageData = new ImageComponent("text/multiplayer.png", 1, w, h);
		e.addComponent(imageData);
		
		PositionComponent pos = PositionComponent.ByBottomLeft(0, Settings.LOGICAL_HEIGHT_PIXELS, w, h);
		//PositionComponent pos = PositionComponent.ByBottomLeft(0, 0, w, h);
		e.addComponent(pos);

		e.addComponent(new SineInterpolationComponent());
		return e;
	}


}
