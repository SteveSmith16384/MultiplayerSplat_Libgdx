package com.mygdx.game;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
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
import com.mygdx.game.systems.DrawingSystem;
import com.mygdx.game.systems.InputSystem;
import com.mygdx.game.systems.MoveToOffScreenSystem;
import com.mygdx.game.systems.MovementSystem;
import com.mygdx.game.systems.ProcessCollisionSystem;
import com.mygdx.game.systems.ProcessPlayersSystem;
import com.mygdx.game.systems.ScrollPlayAreaSystem;
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
	public int winnerImageId;
	public int gameStage = -1; // -1, 0, or 1 for before, during and after game
	private boolean nextStage = false;
	public HashMap<IPlayerInput, PlayerData> players = new HashMap<IPlayerInput, PlayerData>();
	public boolean keyboard_joined = false;
	public ILevelData level;

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

	public float screen_cam_x = Settings.LOGICAL_WIDTH_PIXELS/2-Settings.MAP_SQ_SIZE; // Centre of current point
	public float screen_cam_y = 0;//Settings.LOGICAL_HEIGHT_PIXELS/2;

	public MyGdxGame() {
		super(Settings.RELEASE_MODE);
	}


	@Override
	public void create() {
		super.create();

		font = new BitmapFont();
		font.getData().setScale(2);

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
		ecs.addSystem(new ScrollPlayAreaSystem(this));
		ecs.addSystem(new CheckIfPlayersAreOffScreenSystem(this, ecs));
		ecs.addSystem(new AddAndRemoveMapsquares(this, ecs));


		startPreGame();

		/*if (!Settings.RELEASE_MODE) {
			this.nextStage = true; // Auto-start game
		}*/
	}


	private void addPlayerForController(IPlayerInput controller) {
		if (this.players.containsKey(controller) == false) {
			PlayerData data = new PlayerData(controller);
			this.players.put(controller, data);
			//p("player created");
		}
	}


	public void startNextStage() {
		this.nextStage = true;
	}


	private void startPreGame() {
		this.playMusic("music/IntroLoop.wav");

		this.removeAllEntities();
	}


	private void startPostGame() {
		this.removeAllEntities();
		this.playMusic("music/VictoryMusic.wav");
	}


	public void setWinner(int imageId) {
		this.nextStage = true;
		this.winnerImageId = imageId;
	}


	private void startGame() {
		this.removeAllEntities();

		this.playMusic("music/8BitMetal.wav");

		// Reset all player data
		for (PlayerData player : players.values()) {
			player.init();
		}

		/*if (!Settings.RELEASE_MODE) {
			if (this.players.size() > 0) {
				if (this.players.get(0).isInGame() == false) {
					this.players.get(0).setInGame(true); // Auto-add keyboard player
				}
			}
		}*/

		gameData = new GameData();

		level = new LevelGenerator(ecs);
		level.createLevel();
	}


	private int getNumPlayersInGame() {
		return players.size();
	}


	@Override
	public void render() {
		super.render();

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

			if (this.gameStage == 0) {
				// loop through systems
				this.processPlayersSystem.process();
				this.moveToOffScreenSystem.process();
				//this.playerMovementSystem.process();
				this.walkingAnimationSystem.process(); // Must be before the movementsystem, as that clears the direction
				this.movementSystem.process();				
				ecs.processSystem(CheckIfPlayersAreOffScreenSystem.class);
				ecs.processSystem(ScrollPlayAreaSystem.class);
				ecs.processSystem(AddAndRemoveMapsquares.class);
				this.animSystem.process();
			}

			// Start actual drawing
			Gdx.gl.glClearColor(1, 1, 1, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

			batch.setProjectionMatrix(camera.combined);
			camera.update();

			batch.begin();
			this.drawingSystem.process();
			if (this.gameStage == -1) {
				this.drawPreGameGuiSystem.process();
			} else if (this.gameStage == 0) {
				this.drawInGameGuiSystem.process();
			} else if (this.gameStage == 1) {
				this.drawPostGameGuiSystem.process();
				this.drawInGameGuiSystem.process();
			}

			drawFont(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 20, 20);

			batch.end();

			if (Settings.SHOW_OUTLINES) {
				batch.begin();
				this.drawingSystem.drawDebug(batch);
				batch.end();
			}
		}
	}


	public void drawFont(Batch batch, String text, float x, float y) {
		font.draw(batch, text, x, y);
	}


	public void endOfLevel() {
		//p("End of level!");
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
	}


	private void removeAllEntities() {
		ecs.markAllEntitiesForRemoval();
		ecs.addAndRemoveEntities();
	}


	public void getScreenCoords(float x, float y, Vector2 out) {
		out.x = x-(screen_cam_x) + (Settings.LOGICAL_WIDTH_PIXELS/2);
		out.y = y-(screen_cam_y) + (Settings.LOGICAL_HEIGHT_PIXELS/2);
	}


	// todo - this is not called!
	public void playerKilled(AbstractEntity avatar) {
		/*todo		long diff = System.currentTimeMillis() - timeStarted;
		if (diff < 4000) { // Invincible for 4 seconds
			return;
		}
		 */
		sfx.play("sfx/Falling.mp3");

		avatar.remove();

		PlayersAvatarComponent uic = (PlayersAvatarComponent)avatar.getComponent(PlayersAvatarComponent.class);
		PlayerData player = uic.player;
		player.avatar = null;
		player.timeUntilAvatar = Settings.AVATAR_RESPAWN_TIME_SECS;
		player.lives--;

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

