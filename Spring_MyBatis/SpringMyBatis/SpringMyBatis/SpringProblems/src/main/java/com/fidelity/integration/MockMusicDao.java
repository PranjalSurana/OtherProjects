package com.fidelity.integration;

import java.util.ArrayList;
import java.util.List;

import com.fidelity.business.Music;

public class MockMusicDao implements MusicDao {
	private static List<Music> music;

	static {
		music = new ArrayList<>();
		music.add(new Music("White Album", "The Beatles", "Rock", 1));
		music.add(new Music("Dark Side of the Moon", "Pink Floyd", "Rock", 2));
		music.add(new Music("Worlds Collide", "Apocalyptica", "World", 3));		
		music.add(new Music("Out of This World", "Tito Puente", "Jazz", 4));		
	}

	@Override
	public List<Music> queryForAllMusic() {
		return music;
	}
}
