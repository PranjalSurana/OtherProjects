package com.fidelity.investmonkey.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fidelity.investmonkey.models.*; 
public class AddInvestmentPreferanceTest {
	private AddInvestmentPreferance service;
	private Client client;
	private ClientIdentification indianIdentification;
	private InvestmentPrefModel pref1, pref2, pref3, pref4, pref5;
	@BeforeEach
	void setUp() throws Exception {
		service=new AddInvestmentPreferance();
		client =new Client("krishna","shenoy","9663321676","ABC123","jd@gmail.com",LocalDate.of(2001, 8, 27),"India","570011","ks123",indianIdentification);
		pref1 = new InvestmentPrefModel("ABC123", "Education", "0 to 20,000", "Average", 10, true);
		pref2 = new InvestmentPrefModel("ABC123", "Retirement", "20,000 to 40,000", "Average", 15, true);
		pref3 = new InvestmentPrefModel("ABC123", "Grow Wealth", "40,000 to 60,000", "Average", 20, true);
		pref4 = new InvestmentPrefModel("XYZ123", "Secure Wealth", "70,000 to 80,000", "Average", 25, true);
		pref5 = new InvestmentPrefModel("ABC123", "Meet Financial Goal", "0 to 20,000", "Average", 30, false);
	}

	@AfterEach
	void tearDown() throws Exception {
		pref1 = null;
		pref2 = null;
		pref3 = null;
		pref4 = null;
		pref5 = null;
	}
	
	
	
}
