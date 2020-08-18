package com.mygdx.game;

import com.mygdx.game.components.AnimationCycleComponent;
import com.mygdx.game.components.CanCollectComponent;
import com.mygdx.game.components.CollectableComponent;
import com.mygdx.game.components.CollectableComponent.Type;
import com.mygdx.game.components.CollisionComponent;
import com.mygdx.game.components.ImageComponent;
import com.mygdx.game.components.MakeSpriteSmallerComponent;
import com.mygdx.game.components.MoveOffScreenComponent;
import com.mygdx.game.components.MoveWithGravityComponent;
import com.mygdx.game.components.MovementComponent;
import com.mygdx.game.components.PlayersAvatarComponent;
import com.mygdx.game.components.PositionComponent;
import com.mygdx.game.components.RemoveIfOffScreenComponent;
import com.mygdx.game.components.ScrollsAroundComponent;
import com.mygdx.game.components.WalkingAnimationComponent;
import com.mygdx.game.datamodels.PlayerData;
import com.mygdx.game.input.IPlayerInput;
import com.scs.basicecs.AbstractEntity;

public class EntityFactory {

	private MyGdxGame game;

	public EntityFactory(MyGdxGame _game) {
		game = _game;
	}


	public AbstractEntity createPlayersAvatar(PlayerData player, IPlayerInput controller, float x, float y) {
		AbstractEntity e = new AbstractEntity(game.ecs, "Player");

		ImageComponent imageData = new ImageComponent("grey_box.png", 1, Settings.PLAYER_SIZE, Settings.PLAYER_SIZE);
		e.addComponent(imageData);
		PositionComponent pos = PositionComponent.ByBottomLeft(x, y, Settings.PLAYER_SIZE, Settings.PLAYER_SIZE);
		e.addComponent(pos);
		CollisionComponent cc = new CollisionComponent(false);
		e.addComponent(cc);
		e.addComponent(new MovementComponent());
		PlayersAvatarComponent uic = new PlayersAvatarComponent(player, controller);
		e.addComponent(uic);
		CanCollectComponent ccc = new CanCollectComponent();
		e.addComponent(ccc);
		WalkingAnimationComponent wac = new WalkingAnimationComponent(.2f);
		e.addComponent(wac);
		e.addComponent(new ScrollsAroundComponent());

		game.animFrameHelper.createPlayersFrames(e, player.playerIdx, Settings.PLAYER_SIZE, Settings.PLAYER_SIZE);
		return e;
	}


	public AbstractEntity createWall(float x, float y, float w, float h) {
		AbstractEntity e = new AbstractEntity(game.ecs, "Wall");

		ImageComponent imageData = new ImageComponent("colours/black.png", 0, w, h);
		e.addComponent(imageData);
		PositionComponent pos = PositionComponent.ByBottomLeft(x, y, w, h);
		e.addComponent(pos);
		CollisionComponent cc = new CollisionComponent(true);
		e.addComponent(cc);
		e.addComponent(new ScrollsAroundComponent());

		return e;
	}


	public AbstractEntity createImage(String filename, int x, int y, float w, float h, int zOrder) {
		AbstractEntity e = new AbstractEntity(game.ecs, "Image_" + filename);

		ImageComponent imageData = new ImageComponent(filename, zOrder, w, h);
		e.addComponent(imageData);
		PositionComponent pos = PositionComponent.ByBottomLeft(x, y, w, h);
		e.addComponent(pos);

		return e;
	}


	public AbstractEntity createCoin(float x, float y) {
		AbstractEntity e = new AbstractEntity(game.ecs, "Coin");

		AnimationCycleComponent acc = game.animFrameHelper.generateForCoin(Settings.COLLECTABLE_SIZE);
		e.addComponent(acc);
		ImageComponent imageData = new ImageComponent(acc.frames[0], 1);
		e.addComponent(imageData);
		PositionComponent pos = PositionComponent.ByBottomLeft(x, y, Settings.COLLECTABLE_SIZE, Settings.COLLECTABLE_SIZE);
		e.addComponent(pos);
		CollisionComponent cc = new CollisionComponent(false);
		e.addComponent(cc);
		CollectableComponent col = new CollectableComponent(Type.Coin);
		e.addComponent(col);
		e.addComponent(new ScrollsAroundComponent());
		return e;
	}


	public AbstractEntity createRisingCoin(AbstractEntity coin) {
		PositionComponent pos = (PositionComponent)coin.getComponent(PositionComponent.class);
		ImageComponent img = (ImageComponent)coin.getComponent(ImageComponent.class);

		AbstractEntity e = new AbstractEntity(game.ecs, "RisingCoin");

		ImageComponent imageData;
		if (img.sprite != null) {
			imageData = new ImageComponent(img.sprite, 1);
		} else {
			imageData = new ImageComponent(img.imageFilename, 1, img.w, img.h);
		}
		e.addComponent(imageData);
		PositionComponent pos2 = PositionComponent.ByBottomLeft(pos.rect.left, pos.rect.bottom, pos.rect.width(), pos.rect.height());
		e.addComponent(pos2);
		MoveOffScreenComponent moc = new MoveOffScreenComponent(Settings.PLAYER_SPEED*2, Settings.PLAYER_SPEED*2);
		e.addComponent(moc);
		e.addComponent(new ScrollsAroundComponent());

		return e;
	}	


	public AbstractEntity createTopPlayerEffect(AbstractEntity player) {
		PositionComponent pos = (PositionComponent)player.getComponent(PositionComponent.class);

		AbstractEntity e = new AbstractEntity(game.ecs, "TopPlayerEffect");

		ImageComponent imageData = new ImageComponent("sprites/power_ring.png", 0, Settings.PLAYER_SIZE, Settings.PLAYER_SIZE);
		e.addComponent(imageData);
		PositionComponent pos2 = PositionComponent.ByBottomLeft(pos.rect.left, pos.rect.bottom, pos.rect.width(), pos.rect.height());
		e.addComponent(pos2);
		e.addComponent(new MakeSpriteSmallerComponent());
		e.addComponent(new ScrollsAroundComponent());

		return e;
	}	


	public AbstractEntity createExplodingCoin(float x, float y) {
		AbstractEntity e = new AbstractEntity(game.ecs, "ExplodingCoin");

		AnimationCycleComponent acc = game.animFrameHelper.generateForCoin(Settings.COLLECTABLE_SIZE);
		e.addComponent(acc);
		ImageComponent imageData = new ImageComponent(acc.frames[0], 1);
		e.addComponent(imageData);
		PositionComponent pos = PositionComponent.ByBottomLeft(x, y, Settings.COLLECTABLE_SIZE, Settings.COLLECTABLE_SIZE);
		e.addComponent(pos);
		e.addComponent(new RemoveIfOffScreenComponent());
		e.addComponent(new MovementComponent());
		e.addComponent(new MoveWithGravityComponent());
		return e;
	}	

}
