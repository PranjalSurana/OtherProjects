package com.fidelity.business;

public class Music {
	private String title;
	private String artist;
	private String genre;
	private long musicId;
	
	public Music () {}
	
	public Music(String title, String artist, String genre, long musicId) {
		super();
		this.title = title;
		this.artist = artist;
		this.genre = genre;
		this.musicId = musicId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public long getMusicId() {
		return musicId;
	}

	public void setMusicId(long musicId) {
		this.musicId = musicId;
	}

	@Override
	public String toString() {
		return "Music [title=" + title + ", artist=" + artist + ", genre=" + genre + ", musicId=" + musicId + "]";
	}
	
}
