package com.fidelity.business;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Exhibit {
	private String name;
	private boolean permanent;
	private BigDecimal cost;	

	public Exhibit(String name, BigDecimal cost, boolean permanent) {
		this.name = name;
		this.cost = cost.setScale(2, RoundingMode.HALF_UP);
		this.permanent = permanent;
	}

	public String getName() {
		return name;
	}

	public boolean isPermanent() {
		return permanent;
	}

	public BigDecimal getCost() {
		return cost;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Exhibit other = (Exhibit) obj;
		if (cost == null) {
			if (other.cost != null)
				return false;
		} else if (!cost.equals(other.cost))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (permanent != other.permanent)
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cost == null) ? 0 : cost.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (permanent ? 1231 : 1237);
		return result;
	}
	
	@Override
	public String toString() {
		return "Exhibit [name=" + name + ", permanent=" + permanent + ", cost=" + cost + "]";
	}

	public Exhibit(){
	}

}
