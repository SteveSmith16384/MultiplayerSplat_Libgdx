package com.mygdx.game.systems;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.components.MoveWithGravityComponent;
import com.mygdx.game.components.MovementComponent;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;

public class MoveWithGravitySystem extends AbstractSystem {

	public MoveWithGravitySystem(BasicECS ecs) {
		super(ecs, MoveWithGravityComponent.class);
	}


	@Override
	public void processEntity(AbstractEntity entity) {
		MoveWithGravityComponent mwg = (MoveWithGravityComponent)entity.getComponent(MoveWithGravityComponent.class);
		float offx = mwg.dir.x * 10;// * Gdx.graphics.getDeltaTime();
		float offy = mwg.dir.y * 10;// * Gdx.graphics.getDeltaTime();

		mwg.dir.y -= 70 * Gdx.graphics.getDeltaTime(); // Gravity
		
		MovementComponent posData = (MovementComponent)entity.getComponent(MovementComponent.class);
		posData.offX = offx;
		posData.offY = offy;
	}

}
