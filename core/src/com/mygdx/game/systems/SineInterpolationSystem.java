package com.mygdx.game.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Settings;
import com.mygdx.game.components.PositionComponent;
import com.mygdx.game.components.SineInterpolationComponent;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;

public class SineInterpolationSystem extends AbstractSystem {

	private static final int DURATION = 20;
	
	public SineInterpolationSystem(BasicECS ecs) {
		super(ecs, SineInterpolationComponent.class);
	}


	@Override
	public void processEntity(AbstractEntity entity) {
		SineInterpolationComponent sic = (SineInterpolationComponent)entity.getComponent(SineInterpolationComponent.class);
		PositionComponent posData = (PositionComponent)entity.getComponent(PositionComponent.class);

		if (sic.elapsed == 0) {
			sic.startY = posData.rect.bottom;
		}
		sic.elapsed += Gdx.graphics.getDeltaTime();

		float extraY = Interpolation.bounceOut.apply(0, DURATION, sic.elapsed);
		//MyGdxGame.p("Y: " + extraY);
		posData.rect.setBottom(sic.startY - (extraY * Settings.LOGICAL_HEIGHT_PIXELS/(DURATION*2)));
		
		if (sic.elapsed > DURATION) {
			entity.removeComponent(SineInterpolationComponent.class);
		}

	}
}
