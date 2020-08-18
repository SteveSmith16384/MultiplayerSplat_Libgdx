package com.mygdx.game.systems;

import com.mygdx.game.Settings;
import com.mygdx.game.components.ImageComponent;
import com.mygdx.game.components.MoveWithGravityComponent;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;

public class MoveWithGravitySystem extends AbstractSystem {

	public MoveWithGravitySystem(BasicECS ecs) {
		super(ecs, MoveWithGravityComponent.class);
	}


	@Override
	public void processEntity(AbstractEntity entity) {
		// todo
	}

}
