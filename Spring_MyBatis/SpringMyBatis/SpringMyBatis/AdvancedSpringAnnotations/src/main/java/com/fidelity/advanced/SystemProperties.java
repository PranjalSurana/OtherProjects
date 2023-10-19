package com.fidelity.advanced;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("props")
public class SystemProperties {

	String country;
	String winDir;

	public String getCountry() {
		return country;
	}

	@Value("#{ systemProperties['user.country'] }")
	public void setCountry(String country) {
		this.country = country;
	}

	public String getWinDir() {
		return winDir;
	}

	@Value("#{ environment['SystemRoot'] }")
	public void setWinDir(String winDir) {
		this.winDir = winDir;
	}

}
