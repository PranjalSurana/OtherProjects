package com.fidelity.circular;

import java.util.List;

public class Film {
	private List<Role> cast;
	private String title;
	
	public Film(List<Role> cast, String title) {
		super();
		this.cast = cast;
		this.title = title;
	}

	public Film() {
		super();

	}
	
	public void setCast(List<Role> cast) {
		this.cast = cast;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Role> getCast() {
		return cast;
	}

	public String getTitle() {
		return title;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cast == null) ? 0 : cast.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Film other = (Film) obj;
		if (cast == null) {
			if (other.cast != null)
				return false;
		} else if (!cast.equals(other.cast))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
}
