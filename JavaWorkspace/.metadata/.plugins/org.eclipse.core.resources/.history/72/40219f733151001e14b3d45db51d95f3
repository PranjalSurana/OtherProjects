package com.fidelity.hackathon.freight;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class FreightShip {
	
	private int maxCapacity;
	private List<CrewMember> crew;
	
	public FreightShip(int maxCapacity, List<CrewMember> crew) {
		this.maxCapacity = maxCapacity;
		this.crew = crew;
	}
	
	public void addCrewMember(CrewMember c) {
		if(c.equals(null))
			throw new IllegalStateException("Crew Member cannot be null");
		if(crew.size() >= maxCapacity)
			throw new IllegalStateException("Crew Limit Exceeded!");
		crew.add(c);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(crew, maxCapacity);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FreightShip other = (FreightShip) obj;
		return Objects.equals(crew, other.crew) && maxCapacity == other.maxCapacity;
	}

	public void removeCrewMember(int id) {
		if(getCrew().size() == 0)
			throw new IllegalStateException("No Crew Members are present to be deletd");
		if(id < 0)
			throw new IllegalStateException("Invalid ID");
		for(CrewMember c: getCrew()) {
			if(c.id == id) {
				getCrew().remove(id);
			}
		}
		throw new IllegalStateException("ID doesn't exist!");
	}
	
	public int getMaxCapacity() {
		return maxCapacity;
	}
	
	public int getRemainingCapacity() {
		return maxCapacity - getCrew().size();
	}
	
	public int getCurrentCrewNumber() {
		return getCrew().size();
	}
	
	public BigDecimal calcTotalPayroll() {
		BigDecimal total = BigDecimal.valueOf(0);
		for (CrewMember c : getCrew()) {
			total = total.add(c.calcPay(LocalDate.now()));
		}
		return total;
	}

	public List<CrewMember> getCrew() {
		return crew;
	}

}