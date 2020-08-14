package com.mygdx.game.systems;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Settings;
import com.mygdx.game.components.MovementComponent;
import com.mygdx.game.components.PositionComponent;
import com.mygdx.game.datamodels.CollisionResults;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;

public class MovementSystem extends AbstractSystem {

	private MyGdxGame game;

	public MovementSystem(MyGdxGame _game, BasicECS ecs) {
		super(ecs, MovementComponent.class);

		game = _game;
	}


	@Override
	public void processEntity(AbstractEntity movingEntity) {
		float delta = Gdx.graphics.getDeltaTime();
		MovementComponent md = (MovementComponent)movingEntity.getComponent(MovementComponent.class);
		//if (md != null) {
			PositionComponent pos = (PositionComponent)movingEntity.getComponent(PositionComponent.class);
			//MyGdxGame.p("Mob pos :" + pos.rect);
			if (md.offX != 0) {
				pos.prevPos.set(pos.rect);
				float totalDist = md.offX * delta;
				if (Math.abs(totalDist) > Settings.MAX_MOVEMENT) {
					totalDist = Settings.MAX_MOVEMENT * Math.signum(totalDist);
				}
				pos.rect.move(totalDist, 0);
				CollisionResults results = game.collisionSystem.collided(movingEntity);
				md.offX = 0;
				if (results != null) {
					game.processCollisionSystem.processCollision(movingEntity, results);
					if (results.moveBack) {
						pos.rect.set(pos.prevPos); // Move back
					}
				}
			}
			if (md.offY != 0) {
				pos.prevPos.set(pos.rect);
				float totalDist = md.offY * delta;//Gdx.graphics.getDeltaTime();
				if (Math.abs(totalDist) > Settings.MAX_MOVEMENT) {
					//MyGdxGame.p("Max movement hit!");					
					totalDist = Settings.MAX_MOVEMENT * Math.signum(totalDist);
				}
				pos.rect.move(0, totalDist);
				CollisionResults results = game.collisionSystem.collided(movingEntity);
				md.offY = 0;
				if (results != null) {
					if (results.moveBack) {
						pos.rect.set(pos.prevPos); // Move back
					}
					game.processCollisionSystem.processCollision(movingEntity, results);
				}
			}
		//}
	}

}
