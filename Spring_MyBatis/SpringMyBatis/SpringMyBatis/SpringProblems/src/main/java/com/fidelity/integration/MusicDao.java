package com.fidelity.integration;

import java.util.List;

import com.fidelity.business.Music;

public interface MusicDao {

	List<Music> queryForAllMusic();

}