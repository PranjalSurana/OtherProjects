package com.fidelity.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.fidelity.business.Music;
import com.fidelity.integration.MusicDao;
import org.springframework.stereotype.Component;

public class MusicService {

	private MusicDao dao;

	public MusicService(MusicDao dao) {
		this.dao = dao;
	}

	public List<Music> queryAllMusic() {
		List<Music> musics = dao.queryForAllMusic();
		
		return musics;
	}
	
	public MusicDao getMusicDao() {
		return dao;
	}

	@Autowired
	@Qualifier("supremeDao")
	public void setMusicDao(MusicDao dao) {
		this.dao = dao;
	}
	
	
}
