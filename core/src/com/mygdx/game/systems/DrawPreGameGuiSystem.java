package com.mygdx.game.systems;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Settings;
import com.scs.basicecs.ISystem;

public class DrawPreGameGuiSystem implements ISystem {

	private Sprite background;
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
		game.drawFont(batch, "Winner is first player to collect " + Settings.WINNING_COINS + " coins", 20, 180);
		game.drawFont(batch, game.players.size() + " players in the game!", 20, 140);
		game.drawFont(batch, "Press 'Space' for keyboard player or X on gamepad", 20, 100);
		game.drawFont(batch, "PRESS 'S' TO START GAME!", 20, 60);
	}



}
