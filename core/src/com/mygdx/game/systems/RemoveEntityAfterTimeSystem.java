package com.mygdx.game.systems;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.components.RemoveEntityAfterTimeComponent;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;

public class RemoveEntityAfterTimeSystem extends AbstractSystem {

	public RemoveEntityAfterTimeSystem(BasicECS ecs) {
		super(ecs, RemoveEntityAfterTimeComponent.class);
	}


	@Override
	public void processEntity(AbstractEntity entity) {
		RemoveEntityAfterTimeComponent hdc = (RemoveEntityAfterTimeComponent)entity.getComponent(RemoveEntityAfterTimeComponent.class);

		float dt = Gdx.graphics.getDeltaTime(); // todo - just store time for removal

		hdc.timeRemaining_secs -= dt;
		if(hdc.timeRemaining_secs <= 0) {
			//Settings.p("Removed " + entity);
			entity.remove();
		}
	}

}
