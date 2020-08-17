package com.mygdx.game.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.mygdx.game.components.PositionComponent;
import com.mygdx.game.components.SineInterpolationComponent;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;

public class SineInterpolationSystem extends AbstractSystem {

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

		float frac = Interpolation.bounceOut.apply(0, sic.duration, sic.elapsed);
		//MyGdxGame.p("Y: " + extraY);
		posData.rect.setBottom(sic.startY - ((frac/sic.duration) * sic.final_offset_y));
		
		if (sic.elapsed > sic.duration) {
			entity.removeComponent(SineInterpolationComponent.class);
		}

	}
}
