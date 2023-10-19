package com.fidelity.dependency;

import java.util.List;

public class Film {
	private List<Actor> cast;
	private String title;
	
	public void setCast(List<Actor> cast) {
		this.cast = cast;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Actor> getCast() {
		return cast;
	}

	public String getTitle() {
		return title;
	}
}
