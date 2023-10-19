package com.fidelity.restcontroller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerErrorException;
import org.springframework.web.server.ServerWebInputException;

import com.fidelity.business.TimeZoneException;
import com.fidelity.business.TimeZoneInfo;
import com.fidelity.business.TimeZoneService;

@RestController
@RequestMapping("/time")
public class TimeRestController {
	private TimeZoneService tzservice;

	public TimeRestController() {
		tzservice = new TimeZoneService();
	}

	@GetMapping("/current")  // produces JSON by default
	public TimeZoneInfo getTime() {
		TimeZoneInfo tz = null;
		try {
			tz = tzservice.getTime();
		} 
		catch (RuntimeException e) {
			throw new ServerErrorException("Server side error", e);
		}

		return tz;
	}

	@GetMapping("/zone/{timezone}")  // produces JSON by default
	public TimeZoneInfo getTimeForTimeZone(@PathVariable("timezone") String timezone) throws TimeZoneException {
		TimeZoneInfo tz = null;
		
		try { 
			tz = tzservice.getTimeForTimezone(timezone);
		}
		catch (RuntimeException e) {
			throw new ServerErrorException("Server side error", e);
		}
		if (tz == null) {
			throw new ServerWebInputException("Invalid timezone: " + timezone);
		}
		return tz;
	}
}
