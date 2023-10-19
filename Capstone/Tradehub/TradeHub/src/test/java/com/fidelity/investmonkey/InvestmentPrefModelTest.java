package com.fidelity.investmonkey;

import static org.junit.Assert.*;
import com.fidelity.investmonkey.models.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.fidelity.investmonkey.exception.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class InvestmentPrefModelTest {
	
	
	@Before
	public void setUp() throws Exception {
		InvestmentPrefModel investmentPrefModel = new InvestmentPrefModel("100", "Education", "0 to 20,000", "Average", 10, true); 
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreateInstance() {
		InvestmentPrefModel investmentPrefModel = new InvestmentPrefModel("100", "Education", "0 to 20,000", "Average", 10, true); 
		assertNotNull(investmentPrefModel);
	}
	
	@Test
	public void testTermsAndCondition() {
		Exception exception=assertThrows(RuntimeException.class,()->{
			InvestmentPrefModel investmentPrefModel = new InvestmentPrefModel("100", "Education", "0 to 20,000", "Average", 10, false); 
		});
		assertTrue(exception.getMessage().contains("Please Accept the terms and conditions before Proceeding"));
	}
	
	@Test
	public void testNullClientId() {
		Exception exception=assertThrows(NullException.class,()->{
			InvestmentPrefModel investmentPrefModel = new InvestmentPrefModel("", "Education", "0 to 20,000", "Average", 10, true); 
		});
		assertTrue(exception.getMessage().contains("ClientId cannot be empty"));
	}
	
	@Test
	public void testNullPurpose() {
		Exception exception=assertThrows(NullException.class,()->{
			InvestmentPrefModel investmentPrefModel = new InvestmentPrefModel("100", "", "0 to 20,000", "Average", 10, true); 
		});
		assertTrue(exception.getMessage().contains("Investment Purpose cannot be empty"));
	}
	
	@Test
	public void testNullIncomeCategory() {
		Exception exception=assertThrows(NullException.class,()->{
			InvestmentPrefModel investmentPrefModel = new InvestmentPrefModel("100", "Education", "", "Average", 10, true); 
		});
		assertTrue(exception.getMessage().contains("Income Category cannot be empty"));
	}
	
	@Test
	public void testNullRiskTolerance() {
		Exception exception=assertThrows(NullException.class,()->{
			InvestmentPrefModel investmentPrefModel = new InvestmentPrefModel("100", "Education", "0 to 20,000", "", 10, true); 
		});
		assertTrue(exception.getMessage().contains("Risk Tolerance cannot be empty"));
	}
	
	@Test
	public void testZeroLengthOfInvestment() {
		Exception exception=assertThrows(ZeroValueException.class,()->{
			InvestmentPrefModel investmentPrefModel = new InvestmentPrefModel("100", "Education", "0 to 20,000", "Average", 0, true); 
		});
		assertTrue(exception.getMessage().contains("Length of investment cannot be empty"));
	}
	
	@Test
	public void testNegativeLengthOfInvestment() {
		Exception exception=assertThrows(NegativeValueException.class,()->{
			InvestmentPrefModel investmentPrefModel = new InvestmentPrefModel("100", "Education", "0 to 20,000", "Average", -10, true); 
		});
		assertTrue(exception.getMessage().contains("Length of investment cannot be negative"));
	}

}
