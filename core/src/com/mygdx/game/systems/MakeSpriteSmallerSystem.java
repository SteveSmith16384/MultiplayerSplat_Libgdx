package com.mygdx.game.systems;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.components.ImageComponent;
import com.mygdx.game.components.MakeSpriteSmallerComponent;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;

public class MakeSpriteSmallerSystem extends AbstractSystem {

	public MakeSpriteSmallerSystem(BasicECS ecs) {
		super(ecs, MakeSpriteSmallerComponent.class);
	}


	@Override
	public void processEntity(AbstractEntity entity) {
		float delta = Gdx.graphics.getDeltaTime();
		MakeSpriteSmallerComponent md = (MakeSpriteSmallerComponent)entity.getComponent(MakeSpriteSmallerComponent.class);
		md.scale -= delta;
		if (md.scale < 0.1f) {
			entity.remove();
			return;
		}

		ImageComponent image = (ImageComponent)entity.getComponent(ImageComponent.class);
		if (image.sprite != null) {
			image.sprite.setScale(md.scale);
		}
	}

}
