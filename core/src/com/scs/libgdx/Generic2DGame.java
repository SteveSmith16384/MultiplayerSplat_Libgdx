package com.scs.libgdx;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Settings;
import com.mygdx.game.SoundEffects;

public abstract class Generic2DGame extends ApplicationAdapter implements ControllerConnectionListener {

	public OrthographicCamera camera;
	private Viewport viewport;
	private Music music;
	public SoundEffects sfx = new SoundEffects();
	protected SpriteBatch batch;
	protected ControllerManager controllerManager;
	
	protected boolean paused = false;
	public boolean toggleFullscreen = true, currently_fullscreen = false;

	public Generic2DGame(boolean set_fullscreen) {
		currently_fullscreen = !set_fullscreen;
	}
	
	
	@Override
	public void create() {
		camera = new OrthographicCamera(Settings.LOGICAL_WIDTH_PIXELS, Settings.LOGICAL_HEIGHT_PIXELS);
		camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
		viewport = new StretchViewport(Settings.WINDOW_WIDTH_PIXELS, Settings.WINDOW_HEIGHT_PIXELS, camera);

		batch = new SpriteBatch();
		//Gdx.input.setInputProcessor(this);

		controllerManager = new ControllerManager(this, 4);

	}


	@Override
	public void render() {
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			Gdx.app.exit();
			return;
		}
		
		controllerManager.checkForControllers();

		if (this.toggleFullscreen) {
			this.toggleFullscreen = false;
			if (currently_fullscreen) {
				Gdx.graphics.setWindowedMode(Settings.WINDOW_WIDTH_PIXELS, Settings.WINDOW_HEIGHT_PIXELS);
				batch.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
				Gdx.gl.glViewport(0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight());
				currently_fullscreen = false;
			} else {
				DisplayMode m = null;
				for(DisplayMode mode: Gdx.graphics.getDisplayModes()) {
					if (m == null) {
						m = mode;
					} else {
						if (m.width < mode.width) {
							m = mode;
						}
					}
				}

				Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
				batch.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
				Gdx.gl.glViewport(0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight());
				currently_fullscreen = true;
			}
		}
	}


	@Override
	public void dispose() {
		super.dispose();
		
		batch.dispose();
		this.sfx.dispose();
	}


	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
		viewport.apply();

		camera.viewportWidth = Settings.LOGICAL_WIDTH_PIXELS;
		camera.viewportHeight = Settings.LOGICAL_HEIGHT_PIXELS;
		camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
		camera.update();

	}


	@Override
	public void pause() {
		p("Game paused");
		if (Settings.RELEASE_MODE) {
			paused = true;
		}
	}


	@Override
	public void resume() {
		p("Game resumed");
		paused = false;
	}


	protected void playMusic(String filename) {
		if (music != null) {
			music.dispose();
			music = null;
		}

		try {
			music = Gdx.audio.newMusic(Gdx.files.internal(filename));
			music.setLooping(true);
			music.setVolume(1f);
			music.play();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}


	public static final void p(String s) {
		System.out.println(s);
	}


	public static final void pe(String s) {
		System.err.println(s);
	}


}

