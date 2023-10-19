package com.fidelity.business;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.stereotype.Service;


/**
 * TimeZoneBusinessServiceImpl is a business service that will 
 * return a TimeZoneInfo object with the time and date 
 * for the local time zone or a specified time zone.
 * 
 * @author ROI Instructor
 *
 */
@Service
public class TimeZoneBusinessServiceImpl implements TimeZoneBusinessService {
	private static String dateFormat = "yyyy-MM-dd'T'HH:mm:ss";
	private static List<String> timezones;
	
	// Create a list of legal time zones
	static {
		String[] zones = TimeZone.getAvailableIDs();
		timezones = new ArrayList<String>();
		timezones.addAll(Arrays.asList(zones));
	}
	
	/**
	 * @return the current time in a TimeZoneInfo object
	 */
	@Override
	public TimeZoneInfo getTime() {
		TimeZone timezone = TimeZone.getDefault();	
		
		TimeZoneInfo tzinfo = createTimeZoneInfo(timezone);
				
		return tzinfo;
	}
	
	/**
	 * @return the time for the specified time zone in a TimeZoneInfo object
	 * @throws a TimeZoneException for an illegal time zone
	 */
	@Override
	public TimeZoneInfo getTimeForTimezone(String tz) {
		TimeZoneInfo tzinfo = new TimeZoneInfo();

		if (timezones.contains(tz)) {
			TimeZone timezone = TimeZone.getTimeZone(tz);

			tzinfo = createTimeZoneInfo(timezone);

			return tzinfo;
		}
		else {
			throw new TimeZoneException(tz + " is not a legal time zone");
		}
	}
	
	/**
	 * Returns a TimeZoneInfo object initialized with the 
	 * information for the specified time zone
	 * 
	 * @param timezone the specified time zone
	 * @return a TimeZoneInfo object with the time zone information
	 */
	private TimeZoneInfo createTimeZoneInfo(TimeZone timezone) {
		TimeZoneInfo tzinfo = new TimeZoneInfo();
		
		SimpleDateFormat isoFormat = new SimpleDateFormat(dateFormat);
		isoFormat.setTimeZone(timezone);
		Date current = new Date();
		String time = isoFormat.format(current);
		tzinfo.setTimeDate(time);
		tzinfo.setTimeZone(timezone.getID());

		return tzinfo;
	}
}
