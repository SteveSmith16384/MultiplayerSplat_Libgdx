package com.mygdx.game.systems;

import com.mygdx.game.Settings;
import com.mygdx.game.components.ImageComponent;
import com.mygdx.game.components.RemoveIfOffScreenComponent;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;

public class RemoveIfOffScreenSystem extends AbstractSystem {

	public RemoveIfOffScreenSystem(BasicECS ecs) {
		super(ecs, RemoveIfOffScreenComponent.class);
	}


	@Override
	public void processEntity(AbstractEntity entity) {
		ImageComponent imageData = (ImageComponent)entity.getComponent(ImageComponent.class);
		if (imageData.sprite.getX() < 0 || imageData.sprite.getX() > Settings.LOGICAL_WIDTH_PIXELS) {
			entity.remove();
		} else if (imageData.sprite.getY() < 0 || imageData.sprite.getY() > Settings.LOGICAL_HEIGHT_PIXELS) {
			entity.remove();
		}

	}

}
