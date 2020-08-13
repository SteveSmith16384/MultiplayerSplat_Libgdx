package com.mygdx.game.systems;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.mygdx.game.components.CollisionComponent;
import com.mygdx.game.components.PositionComponent;
import com.mygdx.game.datamodels.CollisionResults;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;

/**
 * This system just detects if two things collide.  it does not actually handle the collisions.
 * @author StephenCS
 *
 */
public class CollisionSystem extends AbstractSystem {

	public CollisionSystem(BasicECS ecs) {
		super(ecs, CollisionComponent.class);
	}


	public CollisionResults collided(AbstractEntity mover, float offX, float offY) {
		PositionComponent moverPos = (PositionComponent)mover.getComponent(PositionComponent.class);
		if (moverPos == null) {
			throw new RuntimeException(mover + " has no " + PositionComponent.class.getSimpleName());
		}
		Iterator<AbstractEntity> it = entities.iterator();
		while (it.hasNext()) {
			AbstractEntity e = it.next();
			if (e != mover) {
				CollisionComponent cc = (CollisionComponent)e.getComponent(CollisionComponent.class);
				//if (cc != null) {
				PositionComponent pos = (PositionComponent)e.getComponent(PositionComponent.class);
				//if (pos != null) {
					if (moverPos.rect.intersects(pos.rect)) {
						if (cc.alwaysCollides) {
							return new CollisionResults(e, cc.blocksMovement);
						}
					}
				//}
				//}
			}
		}
		return null;
	}


	public List<AbstractEntity> getEntitiesAt(float x, float y) {
		List<AbstractEntity> ret = new ArrayList<AbstractEntity>();

		Iterator<AbstractEntity> it = this.entities.iterator();// ecs.getIterator();
		while (it.hasNext()) {
			AbstractEntity e = it.next();
			PositionComponent pos = (PositionComponent)e.getComponent(PositionComponent.class);
			if (pos != null) {
				if (pos.rect.contains(x, y)) {
					ret.add(e);
				}
			}
		}
		return ret;
	}


	public boolean intersects(AbstractEntity e1, AbstractEntity e2) {
		PositionComponent p1 = (PositionComponent)e1.getComponent(PositionComponent.class);
		PositionComponent p2 = (PositionComponent)e2.getComponent(PositionComponent.class);
		if (p1 != null && p2 != null) {
			return p1.rect.intersects(p2.rect);
		}
		return false;
	}


	public boolean isCentreOver(AbstractEntity train, AbstractEntity track) {
		PositionComponent trainPos = (PositionComponent)train.getComponent(PositionComponent.class);
		PositionComponent trackPos = (PositionComponent)track.getComponent(PositionComponent.class);
		if (trainPos != null && trackPos != null) {
			return trackPos.rect.contains(trainPos.rect.centerX(), trainPos.rect.centerY());
		}
		return false;
	}


	public boolean isCentreOver(AbstractEntity track, float cx, float cy) {
		PositionComponent trackPos = (PositionComponent)track.getComponent(PositionComponent.class);
		if (trackPos != null) {
			return trackPos.rect.contains(cx, cy);
		}
		return false;
	}


}
