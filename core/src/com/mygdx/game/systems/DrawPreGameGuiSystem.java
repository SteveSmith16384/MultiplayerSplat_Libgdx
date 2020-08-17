package com.mygdx.game.systems;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Settings;
import com.scs.basicecs.ISystem;

public class DrawPreGameGuiSystem implements ISystem {

	private Sprite background;
	private Sprite logo;
	private MyGdxGame game;
	private SpriteBatch batch;

	public DrawPreGameGuiSystem(MyGdxGame _game, SpriteBatch _batch) {
		game = _game;
		batch = _batch;

		Texture tex = game.getTexture("colours/black.png");
		background = new Sprite(tex);
		background.setSize(Settings.LOGICAL_WIDTH_PIXELS,  Settings.LOGICAL_HEIGHT_PIXELS);
	}


	@Override
	public void process() {
		//background.draw(batch);

		//game.drawFont(batch, game.con.getControllers().size + " controllers found", 20, Settings.LOGICAL_HEIGHT_PIXELS-40);

		game.drawFont(batch, game.players.size() + " players in the game!", 20, 140);
		game.drawFont(batch, "Press 'Space' for keyboard player", 20, 100);
		game.drawFont(batch, "PRESS 'S' TO START!", 20, 60);
	}



}
