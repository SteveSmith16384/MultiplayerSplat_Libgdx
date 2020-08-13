package com.mygdx.game;

import java.util.HashMap;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public final class SoundEffects {

	private HashMap<String, Sound> sfx = new HashMap<String, Sound>(); 

	public SoundEffects() {
	}


	public void play(String s) {
		try {
			if (!sfx.containsKey(s)) {
				sfx.put(s, Gdx.audio.newSound(Gdx.files.internal(s)));
			}
			Sound sound = sfx.get(s);
			sound.play();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}


	public void dispose() {
		Iterator<Sound> it = sfx.values().iterator();
		while (it.hasNext()) {
			Sound s = it.next();
			s.dispose();
		}
		sfx.clear();
	}


}
