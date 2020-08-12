package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.components.AnimationCycleComponent;
import com.mygdx.game.components.CanCollectComponent;
import com.mygdx.game.components.CollectableComponent;
import com.mygdx.game.components.CollectableComponent.Type;
import com.mygdx.game.components.CollisionComponent;
import com.mygdx.game.components.HarmOnContactComponent;
import com.mygdx.game.components.ImageComponent;
import com.mygdx.game.components.MoveOffScreenComponent;
import com.mygdx.game.components.MovementComponent;
import com.mygdx.game.components.PlayersAvatarComponent;
import com.mygdx.game.components.PositionComponent;
import com.mygdx.game.components.ScrollsAroundComponent;
import com.mygdx.game.components.WalkingAnimationComponent;
import com.mygdx.game.input.IPlayerInput;
import com.mygdx.game.models.PlayerData;
import com.scs.awt.Rect;
import com.scs.basicecs.AbstractEntity;
import com.scs.libgdx.Ninepatch;

public class EntityFactory {
	
	private MyGdxGame game;
	
	public EntityFactory(MyGdxGame _game) {
		game = _game;
	}
	

	public AbstractEntity createPlayersAvatar(PlayerData player, IPlayerInput controller, int x, int y) {
		AbstractEntity e = new AbstractEntity(game.ecs, "Player");

		ImageComponent imageData = new ImageComponent("grey_box.png", 1, Settings.PLAYER_SIZE, Settings.PLAYER_SIZE);
		e.addComponent(imageData);
		PositionComponent pos = PositionComponent.ByBottomLeft(x, y, Settings.PLAYER_SIZE, Settings.PLAYER_SIZE);
		e.addComponent(pos);
		CollisionComponent cc = new CollisionComponent(true, false, true, false);
		e.addComponent(cc);
		MovementComponent mc = new MovementComponent(true);
		e.addComponent(mc);
		PlayersAvatarComponent uic = new PlayersAvatarComponent(player, controller);
		e.addComponent(uic);
		CanCollectComponent ccc = new CanCollectComponent();
		e.addComponent(ccc);
		WalkingAnimationComponent wac = new WalkingAnimationComponent(.2f);
		e.addComponent(wac);

		game.animFrameHelper.createPlayersFrames(e, player.imageId, Settings.PLAYER_SIZE, Settings.PLAYER_SIZE);
		return e;
	}


	public AbstractEntity createWall(int x, int y, float w, float h) {
		AbstractEntity e = new AbstractEntity(game.ecs, "Wall");

		if (Settings.SHOW_GREY_BOXES) {
			ImageComponent imageData = new ImageComponent("grey_box.png", 0, w, h);
			e.addComponent(imageData);
		}
		PositionComponent pos = PositionComponent.ByBottomLeft(x, y, w, h);
		e.addComponent(pos);
		CollisionComponent cc = new CollisionComponent(true, false, true, false);
		e.addComponent(cc);
		ScrollsAroundComponent mdc = new ScrollsAroundComponent(true);
		e.addComponent(mdc);

		return e;
	}


	public AbstractEntity createHarmfulArea(int x, int y, float w, float h) {
		AbstractEntity e = new AbstractEntity(game.ecs, "HarmfulArea");

		if (Settings.SHOW_GREY_BOXES) {
			ImageComponent imageData = new ImageComponent("grey_box.png", 0, w, h);
			e.addComponent(imageData);
		}
		PositionComponent pos = PositionComponent.ByBottomLeft(x, y, w, h);
		e.addComponent(pos);
		CollisionComponent cc = new CollisionComponent(true, false, true, false);
		e.addComponent(cc);
		HarmOnContactComponent hoc = new HarmOnContactComponent();
		e.addComponent(hoc);
		ScrollsAroundComponent mdc = new ScrollsAroundComponent(true);
		e.addComponent(mdc);
		return e;
	}


	public AbstractEntity createTestImage(int x, int y, int w, int h, int zOrder) {
		AbstractEntity e = new AbstractEntity(game.ecs, "TestImage");
		
		//Texture tex = new Texture("grey_box.png");
		Ninepatch np = new Ninepatch(null, null);
		Sprite sprite = np.getImage(w, h);
		sprite.setSize(w, h);

		ImageComponent imageData = new ImageComponent(sprite, zOrder);
		e.addComponent(imageData);
		PositionComponent pos = PositionComponent.ByBottomLeft(x, y, w, h);
		e.addComponent(pos);

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


	public AbstractEntity createLadderArea(int x, int y, float w, float h) {
		AbstractEntity e = new AbstractEntity(game.ecs, "Ladder");

		if (Settings.SHOW_GREY_BOXES) {
			ImageComponent imageData = new ImageComponent("grey_box.png", 0, w, h);
			e.addComponent(imageData);
		}
		PositionComponent pos = PositionComponent.ByBottomLeft(x, y, w, h);
		e.addComponent(pos);
		CollisionComponent cc = new CollisionComponent(true, false, true, true);
		e.addComponent(cc);
		ScrollsAroundComponent mdc = new ScrollsAroundComponent(true);
		e.addComponent(mdc);

		return e;
	}


	public AbstractEntity createFluidPlatform(int x, int y, float w) {
		AbstractEntity e = new AbstractEntity(game.ecs, "FluidPlatform");

		if (Settings.SHOW_GREY_BOXES) {
			ImageComponent imageData = new ImageComponent("grey_box.png", 0, w, Settings.PLAYER_SIZE);
			e.addComponent(imageData);
		}
		PositionComponent pos = PositionComponent.ByTopLeft(x, y, w, Settings.PLAYER_SIZE);
		e.addComponent(pos);
		CollisionComponent cc = new CollisionComponent(false, true, false, false);
		e.addComponent(cc);
		ScrollsAroundComponent mdc = new ScrollsAroundComponent(true);
		e.addComponent(mdc);

		return e;
	}


	public AbstractEntity createPlatformType1(int x, int y, int w, int h) {
		AbstractEntity e = new AbstractEntity(game.ecs, "PlatformImage1");

		Ninepatch ninepatch = new Ninepatch("platform1.png", new Rect(1, 8, 1, 3));
		
		ImageComponent imageData = new ImageComponent(ninepatch.getImage(w, h), -1);
		e.addComponent(imageData);
		
		PositionComponent pos = PositionComponent.ByTopLeft(x, y, w, Settings.PLAYER_SIZE);
		e.addComponent(pos);
		CollisionComponent cc = new CollisionComponent(false, true, false, false);
		e.addComponent(cc);
		ScrollsAroundComponent mdc = new ScrollsAroundComponent(true);
		e.addComponent(mdc);

		return e;
	}


	public AbstractEntity createCoin(int x, int y) {
		AbstractEntity e = new AbstractEntity(game.ecs, "Coin");

		AnimationCycleComponent acc = game.animFrameHelper.generateForCoin(Settings.COLLECTABLE_SIZE);
		e.addComponent(acc);
		/*if (acc.frames[0] == null) {
			int dfgf = 456;
		}*/
		ImageComponent imageData = new ImageComponent(acc.frames[0], 1);
		e.addComponent(imageData);
		PositionComponent pos = PositionComponent.ByBottomLeft(x, y, Settings.COLLECTABLE_SIZE, Settings.COLLECTABLE_SIZE);
		e.addComponent(pos);
		CollisionComponent cc = new CollisionComponent(true, false, false, false);
		e.addComponent(cc);
		CollectableComponent col = new CollectableComponent(Type.Coin);
		e.addComponent(col);
		ScrollsAroundComponent mdc = new ScrollsAroundComponent(false);
		e.addComponent(mdc);
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

		return e;
	}	


}
