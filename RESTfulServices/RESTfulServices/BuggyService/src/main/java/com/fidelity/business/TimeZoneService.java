package com.fidelity.business;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


/**
 * TimeZoneService is a business service that will 
 * return a formatted string with the time and date 
 * for the local time zone or a specified time zone.
 * 
 * @author ROI Instructor
 *
 */
public class TimeZoneService {
	private static String dateFormat = "yyyy-MM-dd'T'HH:mm:ss";
	private static List<String> timezones;
	
	static {
		String[] zones = TimeZone.getAvailableIDs();
		timezones = new ArrayList<String>();
		timezones.addAll(Arrays.asList(zones));
	}
	
	public TimeZoneInfo getTime() {
		TimeZoneInfo tzinfo = new TimeZoneInfo();
		String time = "";

		SimpleDateFormat isoFormat = new SimpleDateFormat(dateFormat);
		Date current = new Date();
		time = isoFormat.format(current);

		tzinfo.setTimeDate(time);
		
		TimeZone zone = TimeZone.getDefault();		
		tzinfo.setTimeZone(zone.getID());
		
		return tzinfo;
	}
	
	public TimeZoneInfo getTimeForTimezone(String timezone) throws TimeZoneException {
		TimeZoneInfo tzinfo = new TimeZoneInfo();
		String time = "";

		if (timezones.contains(timezone)) {
			TimeZone tz = TimeZone.getTimeZone(timezone);
			SimpleDateFormat isoFormat = new SimpleDateFormat(dateFormat);
			isoFormat.setTimeZone(tz);
			Date current = new Date();
			time = isoFormat.format(current);
			tzinfo.setTimeDate(time);
			tzinfo.setTimeZone(timezone);
		}
		else {
			throw new TimeZoneException(timezone + " is not a legal time zone");
		}

		return tzinfo;
	}
}
