package com.fidelity.business;


/**
 * TimeZoneInfo is a Data Transfer Object
 * which stores formatted time and date information
 * 
 * @author ROI Instructor
 *
 */

public class TimeZoneInfo {
	private String timeDate;
	private String timeZone;

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public String getTimeDate() {
		return timeDate;
	}

	public void setTimeDate(String timeDate) {
		this.timeDate = timeDate;
	}
	
}
