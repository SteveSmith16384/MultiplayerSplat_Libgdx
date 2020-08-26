package com.mygdx.game;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Vector2;
import com.crashinvaders.vfx.VfxManager;
import com.crashinvaders.vfx.effects.CrtEffect;
import com.mygdx.game.components.ImageComponent;
import com.mygdx.game.components.PlayersAvatarComponent;
import com.mygdx.game.datamodels.GameData;
import com.mygdx.game.datamodels.PlayerData;
import com.mygdx.game.helpers.AnimationFramesHelper;
import com.mygdx.game.input.ControllerInput;
import com.mygdx.game.input.IPlayerInput;
import com.mygdx.game.levels.ILevelData;
import com.mygdx.game.levels.LevelGenerator;
import com.mygdx.game.systems.AddAndRemoveMapsquares;
import com.mygdx.game.systems.AnimationCycleSystem;
import com.mygdx.game.systems.CheckIfPlayersAreOffScreenSystem;
import com.mygdx.game.systems.CollectorSystem;
import com.mygdx.game.systems.CollisionSystem;
import com.mygdx.game.systems.DrawInGameGuiSystem;
import com.mygdx.game.systems.DrawPostGameGuiSystem;
import com.mygdx.game.systems.DrawPreGameGuiSystem;
import com.mygdx.game.systems.DrawTopPlayerEffectSystem;
import com.mygdx.game.systems.DrawingSystem;
import com.mygdx.game.systems.InputSystem;
import com.mygdx.game.systems.MakeSpriteSmallerSystem;
import com.mygdx.game.systems.MoveToOffScreenSystem;
import com.mygdx.game.systems.MoveWithGravitySystem;
import com.mygdx.game.systems.MovementSystem;
import com.mygdx.game.systems.ProcessCollisionSystem;
import com.mygdx.game.systems.ProcessPlayersSystem;
import com.mygdx.game.systems.RemoveEntityAfterTimeSystem;
import com.mygdx.game.systems.RemoveIfOffScreenSystem;
import com.mygdx.game.systems.ScrollPlayAreaSystem;
import com.mygdx.game.systems.SineInterpolationSystem;
import com.mygdx.game.systems.WalkingAnimationSystem;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.BasicECS;
import com.scs.libgdx.Generic2DGame;

public final class MyGdxGame extends Generic2DGame {

	public BasicECS ecs;
	public BitmapFont font;
	public EntityFactory entityFactory;
	public GameData gameData;
	public AnimationFramesHelper animFrameHelper;
	public int winnerImageId = -1;
	public int gameStage = -1; // -1, 0, or 1 for before, during and after game
	private boolean nextStage = false;
	public HashMap<IPlayerInput, PlayerData> players = new HashMap<IPlayerInput, PlayerData>();
	public boolean keyboard_joined = false;
	public ILevelData level_data;
	private VfxManager vfxManager;

	// Systems
	public InputSystem inputSystem;
	private DrawingSystem drawingSystem;
	public CollisionSystem collisionSystem;
	private MovementSystem movementSystem;
	private AnimationCycleSystem animSystem;
	public ProcessCollisionSystem processCollisionSystem;
	public CollectorSystem collectorSystem;
	private WalkingAnimationSystem walkingAnimationSystem;
	private MoveToOffScreenSystem moveToOffScreenSystem;
	private DrawInGameGuiSystem drawInGameGuiSystem;
	private ProcessPlayersSystem processPlayersSystem;
	private DrawPreGameGuiSystem drawPreGameGuiSystem;
	private DrawPostGameGuiSystem drawPostGameGuiSystem;
	private AddAndRemoveMapsquares addAndRemoveMapsquares;
	private ScrollPlayAreaSystem scrollPlayAreaSystem;
	private SineInterpolationSystem sineInterpolationSystem;
	private MakeSpriteSmallerSystem makeSpriteSmallerSystem;
	private DrawTopPlayerEffectSystem drawTopPlayerEffectSystem;
	private RemoveIfOffScreenSystem removeIfOffScreenSystem;
	private MoveWithGravitySystem moveWithGravitySystem;
	private RemoveEntityAfterTimeSystem removeEntityAfterTimeSystem;

	// Centre of current point
	public float screen_cam_x;
	public float screen_cam_y;
	public int scroll_speed;

	public MyGdxGame() {
		super(Settings.RELEASE_MODE);
	}


	@Override
	public void create() {
		super.create();

		//vfxManager = new VfxManager(Pixmap.Format.RGBA8888, Settings.LOGICAL_SIZE_PIXELS, Settings.LOGICAL_SIZE_PIXELS);//viewports[i].viewPos.width, viewports[i].viewPos.height);
		vfxManager = new VfxManager(Pixmap.Format.RGBA8888);//, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		//vfxManager.addEffect(new ChromaticAberrationEffect(50)); // good but trippy
		//vfxManager.addEffect(new OldTvEffect());
		vfxManager.addEffect(new CrtEffect());
		//vfxManager.addEffect(new GaussianBlurEffect(GaussianBlurEffect.BlurType.Gaussian3x3b)); // No effect?
		//vfxManager.addEffect(new FilmGrainEffect()); // No use
		//vfxManager.addEffect(new LensFlareEffect()); // Good
		//vfxManager.addEffect(new BloomEffect(new BloomEffect.Settings(10, 0.85f, 1f, .85f, 1.1f, .85f))); // Good
		//vfxManager.addEffect(new FxaaEffect()); // No effect?
		//vfxManager.addEffect(new LevelsEffect()); // No effect
		//vfxManager.addEffect(new MotionBlurEffect(Pixmap.Format.RGBA8888, MixEffect.Method.MAX, .95f)); // A bit trippy
		//vfxManager.addEffect(new NfaaEffect(true)); // No difference?
		//vfxManager.addEffect(new RadialBlurEffect(2)); // Very blurry
		//vfxManager.addEffect(new RadialDistortionEffect()); // Puts in a window
		//vfxManager.addEffect(new VignettingEffect(false)); // Puts a shade around the edge
		//vfxManager.addEffect(new WaterDistortionEffect(2, 2)); // No use?
		//vfxManager.addEffect(new ZoomEffect()); // No effect?

		this.generateFonts();

		ecs = new BasicECS();
		entityFactory = new EntityFactory(this);
		animFrameHelper = new AnimationFramesHelper(this);

		// Systems
		this.inputSystem = new InputSystem(this, ecs);
		drawingSystem = new DrawingSystem(this, ecs, batch);
		collisionSystem = new CollisionSystem(ecs);
		movementSystem = new MovementSystem(this, ecs);
		animSystem = new AnimationCycleSystem(ecs);
		processCollisionSystem = new ProcessCollisionSystem(this);
		this.collectorSystem = new CollectorSystem(this);
		this.walkingAnimationSystem = new WalkingAnimationSystem(ecs);
		this.moveToOffScreenSystem = new MoveToOffScreenSystem(ecs);
		this.drawInGameGuiSystem = new DrawInGameGuiSystem(this, batch);
		this.processPlayersSystem = new ProcessPlayersSystem(this);
		this.drawPreGameGuiSystem = new DrawPreGameGuiSystem(this, batch);
		this.drawPostGameGuiSystem = new DrawPostGameGuiSystem(this, batch);
		ecs.addSystem(new CheckIfPlayersAreOffScreenSystem(this, ecs));
		this.addAndRemoveMapsquares = new AddAndRemoveMapsquares(this, ecs);
		this.sineInterpolationSystem = new SineInterpolationSystem(ecs);
		this.makeSpriteSmallerSystem = new MakeSpriteSmallerSystem(ecs);
		this.drawTopPlayerEffectSystem = new DrawTopPlayerEffectSystem(this, ecs);
		this.removeIfOffScreenSystem = new RemoveIfOffScreenSystem(ecs);
		this.moveWithGravitySystem = new MoveWithGravitySystem(ecs);
		this.removeEntityAfterTimeSystem = new RemoveEntityAfterTimeSystem(ecs);

		startPreGame();
	}


	private void generateFonts() {
		int height = Gdx.graphics.getBackBufferHeight();
		//Settings.p("Height: " + height);

		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/SHOWG.TTF"));

		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = height/30;
		//Settings.p("Font size=" + parameter.size);
		font = generator.generateFont(parameter);

		generator.dispose();
	}


	private void addPlayerForController(IPlayerInput controller) {
		if (this.players.containsKey(controller) == false) {
			PlayerData data = new PlayerData(controller);
			this.players.put(controller, data);
			sfx.play("sfx/Collect_Point_01.wav");
		}
	}


	public void startNextStage() {
		this.nextStage = true;
	}


	private void startPreGame() {
		this.playMusic("music/retro.mp3");

		this.removeAllEntities();

		AbstractEntity multi = TextEntityFactory.createMultiplayer(this);
		ecs.addEntity(multi);
		AbstractEntity splat = TextEntityFactory.createSplatText(this);
		ecs.addEntity(splat);
	}


	private void startPostGame() {
		this.removeAllEntities();
		this.playMusic("music/VictoryMusic.wav");
	}


	public void setWinner(int id) {
		this.nextStage = true;
		if (this.winnerImageId < 0) {
			this.winnerImageId = id;
		}
	}


	private void startGame() {
		this.playMusic("music/Boss Battle #2 V1.mp3");

		for (PlayerData player : players.values()) {
			player.reset();
		}

		gameData = new GameData();
		winnerImageId = -1;

		this.startLevel();
	}


	private void startLevel() {
		this.removeAllEntities();

		this.gameData.level++;

		if (gameData.level > 1) {
			AbstractEntity multi = TextEntityFactory.createNextLevelText(this);
			ecs.addEntity(multi);
		}

		level_data = new LevelGenerator(ecs);
		level_data.createLevel();

		screen_cam_x = Settings.LOGICAL_WIDTH_PIXELS/2-Settings.MAP_SQ_SIZE; // Centre of current point
		screen_cam_y = Settings.MAP_SQ_SIZE*2;
		scroll_speed = Settings.START_SPEED + (gameData.level*5);

		this.scrollPlayAreaSystem = new ScrollPlayAreaSystem(this);
		this.addAndRemoveMapsquares = new AddAndRemoveMapsquares(this, ecs);
	}


	private int getNumPlayersInGame() {
		return players.size();
	}


	@Override
	public void render() {
		super.render();

		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			if (this.gameStage == -1) {
				Gdx.app.exit();
				return;
			} else {
				this.gameStage = -1;
				startPreGame();
			}
		}

		if (!paused) {
			if (nextStage) {
				nextStage = false;
				if (this.gameStage == -1 && this.getNumPlayersInGame() > 0) {
					this.gameStage = 0;
					this.startGame();
				} else if (this.gameStage == 0) {
					this.gameStage = 1;
					startPostGame();
				} else if (this.gameStage == 1) {
					this.gameStage = -1;
					startPreGame();
				}
			}

			ecs.addAndRemoveEntities();

			this.inputSystem.process();
			this.sineInterpolationSystem.process();
			this.removeEntityAfterTimeSystem.process();

			if (this.gameStage == 0) {
				// loop through systems
				this.makeSpriteSmallerSystem.process();
				this.addAndRemoveMapsquares.process(); // Must be before process player system so we know where we can start the player
				this.processPlayersSystem.process();
				this.moveToOffScreenSystem.process();
				this.walkingAnimationSystem.process(); // Must be before the movementsystem, as that clears the direction
				this.moveWithGravitySystem.process();
				this.movementSystem.process();
				ecs.processSystem(CheckIfPlayersAreOffScreenSystem.class);
				this.drawTopPlayerEffectSystem.process();
				this.scrollPlayAreaSystem.process();
				this.animSystem.process();
			}
			this.removeIfOffScreenSystem.process();


			// Start actual drawing
			Gdx.gl.glClearColor(1, 1, 1, 1);			
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

			vfxManager.cleanUpBuffers();
			vfxManager.beginInputCapture();

			batch.setProjectionMatrix(camera.combined);
			camera.update();

			batch.begin();
			this.drawingSystem.process();
			if (this.gameStage == -1) {
				this.drawPreGameGuiSystem.process();
			} else if (this.gameStage == 0) {
				this.drawInGameGuiSystem.process();
			} else if (this.gameStage == 1) {
				this.drawInGameGuiSystem.process();
				this.drawPostGameGuiSystem.process();
			}

			if (Settings.RELEASE_MODE == false) {
				drawFont(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 20, 20);
			}

			batch.end();

			if (Settings.SHOW_OUTLINES) {
				batch.begin();
				this.drawingSystem.drawDebug(batch);
				batch.end();
			}

			vfxManager.endInputCapture();
			vfxManager.applyEffects();
			vfxManager.renderToScreen();

		}
	}


	public void drawFont(Batch batch, String text, float x, float y) {
		font.setColor(Color.BLACK);
		font.draw(batch, text, x+2, y);
		font.draw(batch, text, x-2, y);
		font.draw(batch, text, x, y+2);
		font.draw(batch, text, x, y-2);
		font.setColor(Color.WHITE);
		font.draw(batch, text, x, y);
	}


	public void endOfLevel() {
		startLevel();
	}


	@Override
	public void dispose() {
		super.dispose();

		removeAllEntities();

		if (font != null) {
			font.dispose();
		}
		this.animFrameHelper.dispose();
		ecs.dispose();
		this.vfxManager.dispose();
	}


	private void removeAllEntities() {
		ecs.markAllEntitiesForRemoval();
		ecs.addAndRemoveEntities();
	}


	public void getScreenCoords(float x, float y, Vector2 out) {
		out.x = x-(screen_cam_x) + (Settings.LOGICAL_WIDTH_PIXELS/2);
		out.y = y-(screen_cam_y) + (Settings.LOGICAL_HEIGHT_PIXELS/2);
	}


	public void playerKilled(AbstractEntity avatar) {
		p(avatar + " killed");

		// Exploding coins
		ImageComponent img = (ImageComponent)avatar.getComponent(ImageComponent.class);
		if (img != null && img.sprite != null) {
			for (int i=0 ; i<10 ; i++) {
				AbstractEntity deleteme = entityFactory.createExplodingCoin(img.sprite.getX(), img.sprite.getY());
				ecs.addEntity(deleteme);
			}
		}

		avatar.remove();

		PlayersAvatarComponent uic = (PlayersAvatarComponent)avatar.getComponent(PlayersAvatarComponent.class);
		PlayerData player = uic.player;
		player.avatar = null;
		player.timeUntilAvatar = Settings.AVATAR_RESPAWN_TIME_SECS;
		player.lives--;
		player.score -= 10;
		if (player.score < 0) {
			player.score = 0;
		}

		sfx.play("sfx/Falling.mp3");

		// Check for winner
		int winner = -1;
		int highestScore = -1;
		boolean all_died = true;
		for (PlayerData p : players.values()) {
			if (p.lives <= 0) {
				if (p.score > highestScore) {
					highestScore = p.score;
					winner = p.playerIdx;
				}
			} else {
				all_died = false;
				break;
			}
		}
		if (all_died) {
			setWinner(winner);
			return;
		}

		this.scroll_speed += 5f;

	}


	@Override
	public void resize(int width, int height) {
		super.resize(width, height);

		this.vfxManager.resize(width, height);
	}


	@Override
	public void connected(Controller controller) {
		addPlayerForController(new ControllerInput(controller));
		p(this.controllerManager.getInGameControllers().size() + " controllers");
	}


	@Override
	public void disconnected(Controller controller) {
		// todo - remove player
		p(this.controllerManager.getInGameControllers().size() + " controllers");
	}

}

