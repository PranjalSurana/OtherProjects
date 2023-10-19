package com.fidelity.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fidelity.business.Music;
import com.fidelity.integration.MusicDao;

class MusicServiceTest {
	private static ApplicationContext context;
	private static String configFile = "music-beans.xml";
	private MusicService service;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		context = new ClassPathXmlApplicationContext(configFile);
	}

	@BeforeEach
	void setUp() throws Exception {
		service = context.getBean(MusicService.class);
	}

	@Test
	void testQueryAllMusic() {
		List<Music> musics = service.queryAllMusic();
		
		assertNotNull(musics);
		assertTrue(musics.size() > 0);
		for (Music music : musics) {
			assertNotNull(music);
			assertNotNull(music.getTitle());
			System.out.println(music);
		}
	}

	@Test
	void testGetDao() {
		MusicDao dao = service.getMusicDao();
		assertNotNull(dao);
	}

}
