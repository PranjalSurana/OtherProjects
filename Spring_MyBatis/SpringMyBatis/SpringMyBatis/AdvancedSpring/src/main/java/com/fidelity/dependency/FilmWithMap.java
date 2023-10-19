package com.fidelity.dependency;

import java.util.Map;

public class FilmWithMap {
	private Map<String, Actor> cast;
	private String title;

	public Map<String, Actor> getCast() {
		return cast;
	}
	
	public void setCast(Map<String, Actor> cast) {
		this.cast = cast;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
}
