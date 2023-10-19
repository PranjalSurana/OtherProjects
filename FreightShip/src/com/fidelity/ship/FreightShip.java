package com.fidelity.ship;

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
		if(c == null)
			throw new IllegalStateException("Crew Member cannot be null");
		if(crew.size() >= maxCapacity)
			throw new IllegalStateException("Crew Limit Exceeded!");
		crew.add(c);
	}

	public void removeCrewMember(int id) {
		if(crew.size() == 0)
			throw new IllegalStateException("No Crew Members are present to be deletd");
		if(id < 0)
			throw new IllegalStateException("Invalid ID");
		for(CrewMember c: crew) {
			if(c.id == id) {
				crew.remove(id);
				return;
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
		return crew.size();
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