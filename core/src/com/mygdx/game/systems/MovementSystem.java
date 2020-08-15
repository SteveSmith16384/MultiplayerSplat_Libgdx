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
		MyGdxGame.p("Moving " + movingEntity);
		
		float delta = Gdx.graphics.getDeltaTime();
		MovementComponent md = (MovementComponent)movingEntity.getComponent(MovementComponent.class);
		PositionComponent pos = (PositionComponent)movingEntity.getComponent(PositionComponent.class);

		if (Settings.DEBUG_PLAYER_STUCK) {
			CollisionResults results = game.collisionSystem.collided(movingEntity);
			if (results != null && results.moveBack) {
				MyGdxGame.p("Start Mob pos: " + pos.rect);
				PositionComponent resultPos = (PositionComponent)results.collidedWith.getComponent(PositionComponent.class);
				MyGdxGame.p("Collided with: " + resultPos.rect);
				MyGdxGame.p("Stuck!");
			}
		}

		if (Settings.DEBUG_PLAYER_STUCK) {
			MyGdxGame.p("Mob pos: " + pos.rect);
		}
		if (md.offX != 0) {
			pos.prevPos.set(pos.rect);
			float totalDist = md.offX * delta;
			if (Math.abs(totalDist) > Settings.MAX_MOVEMENT) {
				totalDist = Settings.MAX_MOVEMENT * Math.signum(totalDist);
			}
			pos.rect.move(totalDist, 0);
			CollisionResults results = game.collisionSystem.collided(movingEntity);
			if (results != null) {
				game.processCollisionSystem.processCollision(movingEntity, results);
				if (results.moveBack) {
					if (Settings.DEBUG_PLAYER_STUCK) {
						MyGdxGame.p("New Mob pos: " + pos.rect);
						PositionComponent resultPos = (PositionComponent)results.collidedWith.getComponent(PositionComponent.class);
						MyGdxGame.p("Collided with: " + resultPos.rect);
					}
					pos.rect.set(pos.prevPos); // Move back
				}
			}
			md.offX = 0;

			if (Settings.DEBUG_PLAYER_STUCK) {
				results = game.collisionSystem.collided(movingEntity);
				if (results != null && results.moveBack) {
					MyGdxGame.p("Stuck!");
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
			if (results != null) {
				if (results.moveBack) {
					if (Settings.DEBUG_PLAYER_STUCK) {
						MyGdxGame.p("New Mob pos: " + pos.rect);
						PositionComponent resultPos = (PositionComponent)results.collidedWith.getComponent(PositionComponent.class);
						MyGdxGame.p("Collided with: " + resultPos.rect);
					}
					pos.rect.set(pos.prevPos); // Move back
				}
				game.processCollisionSystem.processCollision(movingEntity, results);
			}
			md.offY = 0;

			if (Settings.DEBUG_PLAYER_STUCK) {
				results = game.collisionSystem.collided(movingEntity);
				if (results != null && results.moveBack) {
					MyGdxGame.p("Stuck!");
				}
			}
		}

		if (Settings.DEBUG_PLAYER_STUCK) {
			MyGdxGame.p("Final Mob pos: " + pos.rect);
			CollisionResults results = game.collisionSystem.collided(movingEntity);
			if (results != null && results.moveBack) {
				MyGdxGame.p("Stuck!");
			}
			MyGdxGame.p("==================================");
		}
	}

}
