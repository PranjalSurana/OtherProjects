package com.fidelity.circular;

public class Role {
	private Film film;
	private String characterName;
	private Actor playedBy;
	
	public Role(Film film, String characterName, Actor playedBy) {
		super();
		this.film = film;
		this.characterName = characterName;
		this.playedBy = playedBy;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((characterName == null) ? 0 : characterName.hashCode());
		result = prime * result + ((film == null) ? 0 : film.hashCode());
		result = prime * result + ((playedBy == null) ? 0 : playedBy.hashCode());
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
		Role other = (Role) obj;
		if (characterName == null) {
			if (other.characterName != null)
				return false;
		} else if (!characterName.equals(other.characterName))
			return false;
		if (film == null) {
			if (other.film != null)
				return false;
		} else if (!film.equals(other.film))
			return false;
		if (playedBy == null) {
			if (other.playedBy != null)
				return false;
		} else if (!playedBy.equals(other.playedBy))
			return false;
		return true;
	}
	
}
