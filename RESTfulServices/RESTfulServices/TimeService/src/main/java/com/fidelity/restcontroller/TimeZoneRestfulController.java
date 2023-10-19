package com.fidelity.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerErrorException;
import org.springframework.web.server.ServerWebInputException;

import com.fidelity.business.TimeZoneBusinessService;
import com.fidelity.business.TimeZoneInfo;

/**
 * TimeZoneRestfulController is a RESTful web service controller that reports 
 * the current time for the local time zone, or for a specified time zone.
 * 
 * @author ROI Instructor Team
 */
@RestController
@RequestMapping("/time")  // http://localhost:8080/time
public class TimeZoneRestfulController {
	@Autowired
	private TimeZoneBusinessService tzservice;

	/**
	 * getCurrentTime() returns the current time for the time zone where the server is located
	 * 
	 * @return  TimeZoneInfo with the current time for the local time zone
	 */
	@GetMapping("/current")  // GET http://localhost:8080/time/current  
	public TimeZoneInfo getCurrentTime() {
		try {
			TimeZoneInfo tzinfo = tzservice.getTime();
			return tzinfo;
		} 
		catch (RuntimeException e) {
			// Report a problem on the server
			throw new ServerErrorException("Server side error", e);
		}

	}

	/**
	 * getTimeForZoneInRequest() returns the time for the specified time zone
	 * The time zone is specified  with a request parameter
	 * 
	 * For example: http://localhost:8080/time?timezone=America/New_York
	 * 
	 * @param timezone - the specified timezone
	 * @return TimeZoneInfo with the current time for the local time zone
	 */
	@GetMapping  // GET http://localhost:8080/time?timezone=America/New_York
	public TimeZoneInfo getTimeForZoneInRequest(@RequestParam String timezone) {
		TimeZoneInfo tz = null;

		try {
			tz = tzservice.getTimeForTimezone(timezone);
		} 
		catch (RuntimeException e) {
			// Report a problem on the server
			throw new ServerErrorException("Server side error", e);
		}

		if (tz == null) {
			// Blame the user for passing an invalid time zone
			throw new ServerWebInputException("Invalid time zone: " + timezone);
		}

		return tz;
	}

	/**
	 * getTimeForZoneInRequest() returns the time for the specified time zone
	 * The time zone is specified  with a path parameter
	 * For example: http://localhost:8080/time/EST
	 * 
	 * @param timezone - the specified time zone
	 * @return TimeZoneInfo with the current time for the local time zone
	 */
	@GetMapping("/{timezone}")
	public TimeZoneInfo getTimeForZoneInPath(@PathVariable String timezone) {
		TimeZoneInfo tz = null;

		try {
			tz = tzservice.getTimeForTimezone(timezone);
		} 
		catch (RuntimeException e) {
			// Report a problem on the server
			throw new ServerErrorException("Server side error", e);
		}

		if (tz == null) {
			// Blame the user for passing an invalid time zone
			throw new ServerWebInputException("Invalid time zone: " + timezone);
		}
		return tz;
	}

}
