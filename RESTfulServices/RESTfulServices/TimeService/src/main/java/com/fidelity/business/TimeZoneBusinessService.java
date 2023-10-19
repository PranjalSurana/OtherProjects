package com.fidelity.business;

public interface TimeZoneBusinessService {

	TimeZoneInfo getTime();

	TimeZoneInfo getTimeForTimezone(String timezone) throws TimeZoneException;

}