package com.mygdx.game.systems;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.mygdx.game.components.CollisionComponent;
import com.mygdx.game.components.PositionComponent;
import com.mygdx.game.datamodels.CollisionResults;
import com.scs.awt.RectF;
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


	public CollisionResults collided(AbstractEntity mover) {
		CollisionComponent moverCC = (CollisionComponent)mover.getComponent(CollisionComponent.class);
		if (moverCC == null) {
			return null;
		}
		
		AbstractEntity collided_with = null;
		boolean block_movement = false;
		
		PositionComponent moverPos = (PositionComponent)mover.getComponent(PositionComponent.class);
		Iterator<AbstractEntity> it = entities.iterator();
		while (it.hasNext()) {
			AbstractEntity e = it.next();
			if (e != mover) {
				CollisionComponent cc = (CollisionComponent)e.getComponent(CollisionComponent.class);
				PositionComponent pos = (PositionComponent)e.getComponent(PositionComponent.class);
				if (moverPos.rect.intersects(pos.rect)) {
					//return new CollisionResults(e, cc.blocksMovement);
					collided_with = e;
					block_movement = block_movement || cc.blocksMovement;
				}
			}
		}
		if (collided_with != null) {
			return new CollisionResults(collided_with, block_movement);
		} else {
			return null;
		}
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


	public boolean isAreaClear(RectF rect) {
		Iterator<AbstractEntity> it = this.entities.iterator();
		while (it.hasNext()) {
			AbstractEntity e = it.next();
			CollisionComponent coll = (CollisionComponent)e.getComponent(CollisionComponent.class);
			if (coll == null || coll.blocksMovement == false) {
				continue;
			}
			PositionComponent pos = (PositionComponent)e.getComponent(PositionComponent.class);
			if (pos != null) {
				if (pos.rect.intersects(rect)) {
					return false;
				}
			}
		}
		return true;
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
