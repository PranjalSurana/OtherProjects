package main;

import java.math.BigDecimal;
import java.time.LocalDate;

public abstract class CrewMember {
	
	protected int id;
	protected String name;
	protected int payDay;
	
	public CrewMember(int id, String name, int payDay) {
		this.id = id;
		this.name = name;
		this.payDay = payDay;
	}
	
	public abstract BigDecimal calcPay(LocalDate currDay);
//		if(currDay.getDayOfMonth() < payDay)
//			return new BigDecimal(0.0);
//		else

}