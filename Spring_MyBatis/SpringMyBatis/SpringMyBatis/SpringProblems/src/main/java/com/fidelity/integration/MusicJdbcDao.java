package com.fidelity.integration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fidelity.business.Music;

public class MusicJdbcDao implements MusicDao {
	private Log log = LogFactory.getLog(this.getClass());

	private Connection conn;
	private Properties properties;


	public MusicJdbcDao() throws IOException {
		// Get properties from config file in classpath
		Properties properties = new Properties();
		properties.load(this.getClass()
				.getClassLoader()
				.getResourceAsStream("db.properties"));
	}	

	public List<Music> queryForAllMusic() {
		PreparedStatement statement = null;
		try {
			List<Music> musicList = new ArrayList<>();
			openConnection();
			statement = conn.prepareStatement("SELECT * from music");
			ResultSet rs = statement.executeQuery();
			while (rs.next()){
				String title = rs.getString("title");
				String artist = rs.getString("artist");
				String genre = rs.getString("genre");
				long id = rs.getLong("musicId");
				Music music = new Music(title, artist, genre, id);
				musicList.add(music);
			}

			return musicList;
		} catch (Exception e) {
			throw new DatabaseException(e);
		} finally {
			close();
		}
	}

	private void openConnection()  {
		try {
			String driver = properties.getProperty("db.driver");
			String dburl = properties.getProperty("db.url");
			String user = properties.getProperty("db.username");
			String password = properties.getProperty("db.password");

			Class.forName(driver);
			conn = DriverManager.getConnection(dburl,user,password);
		} catch (ClassNotFoundException | SQLException e) {
			throw new DatabaseException(e);		}
	}

	private void close(){
		try {
			conn.close();
		} catch (Exception e) {
			// ok if close() fails
		}
		log.info("DAO resources released.");
	}

}
