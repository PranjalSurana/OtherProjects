package com.fidelity.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImportantService {
	@Autowired
	private StringProvider sp;

	public ImportantService() {

	}
	
	public ImportantService(StringProvider sp) {
		this.sp = sp;
	}
	
	public String provide() {
		return sp.provide();
	}

}
