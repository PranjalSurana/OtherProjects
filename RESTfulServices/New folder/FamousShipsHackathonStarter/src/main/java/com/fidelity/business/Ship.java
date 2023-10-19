package com.fidelity.business;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Ship {

	private long id;
	private String name;
	private String nickname;
	private String captain;
	private String description;
	private String type;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Ship ship)) return false;
		return getId() == ship.getId() && Objects.equals(getName(), ship.getName()) && Objects.equals(getNickname(), ship.getNickname()) && Objects.equals(getCaptain(), ship.getCaptain()) && Objects.equals(getDescription(), ship.getDescription()) && Objects.equals(getType(), ship.getType());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getName(), getNickname(), getCaptain(), getDescription(), getType());
	}

	@Override
	public String toString() {
		return "Ship{" +
				"id=" + id +
				", name='" + name + '\'' +
				", nickname='" + nickname + '\'' +
				", captain='" + captain + '\'' +
				", description='" + description + '\'' +
				", type='" + type + '\'' +
				'}';
	}

}