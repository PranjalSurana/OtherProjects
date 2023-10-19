package com.fidelity.dependency;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("no-way")
public class Film {
	private List<Actor> cast;
	private String title;
	
	@Autowired
	public void setCast(List<Actor> cast) {
		this.cast = cast;
	}

	// Set value from properties file
	@Value("${title}")
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
