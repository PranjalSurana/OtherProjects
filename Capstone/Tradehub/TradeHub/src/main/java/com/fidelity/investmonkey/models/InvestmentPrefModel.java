package com.fidelity.investmonkey.models;
import com.fidelity.investmonkey.exception.*;
public class InvestmentPrefModel {
	private String clientId, investmentPurpose, incomeCategory, riskTolerance;
	private int lengthOfInvestment;
	private boolean roboAdvisor;
	
	public InvestmentPrefModel(String clientId, String investmentPurpose, String incomeCategory, String riskTolerance,
			int lengthOfInvestment, boolean roboAdvisor) {
		super();
		if(clientId == null || clientId.length()==0) {
			throw new NullException("ClientId cannot be empty");
		}
		if(investmentPurpose == null || investmentPurpose.length()==0) {
			throw new NullException("Investment Purpose cannot be empty");
		}
		if(incomeCategory == null || incomeCategory.length()==0) {
			throw new NullException("Income Category cannot be empty");
		}
		if(riskTolerance == null || riskTolerance.length()==0) {
			throw new NullException("Risk Tolerance cannot be empty");
		}
		if(lengthOfInvestment==0) {
			throw new ZeroValueException("Length of investment cannot be empty");
		}
		if(lengthOfInvestment < 0) {
			throw new NegativeValueException("Length of investment cannot be negative");
		}
		if(!roboAdvisor) {
			throw new IllegalArgumentException("Please Accept the terms and conditions before Proceeding");
		}
		this.clientId = clientId;
		this.investmentPurpose = investmentPurpose;
		this.incomeCategory = incomeCategory;
		this.riskTolerance = riskTolerance;
		this.lengthOfInvestment = lengthOfInvestment;
		this.roboAdvisor = roboAdvisor;
	}
	
	public String getClientId() {
		return clientId;
	}
	
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
	public String getInvestmentPurpose() {
		return investmentPurpose;
	}
	
	public void setInvestmentPurpose(String investmentPurpose) {
		this.investmentPurpose = investmentPurpose;
	}
	
	public String getIncomeCategory() {
		return incomeCategory;
	}
	
	public void setIncomeCategory(String incomeCategory) {
		this.incomeCategory = incomeCategory;
	}
	
	public String getRiskTolerance() {
		return riskTolerance;
	}
	
	public void setRiskTolerance(String riskTolerance) {
		this.riskTolerance = riskTolerance;
	}
	
	public int getLengthOfInvestment() {
		return lengthOfInvestment;
	}
	
	public void setLengthOfInvestment(int lengthOfInvestment) {
		this.lengthOfInvestment = lengthOfInvestment;
	}
	
	public boolean isRoboAdvisor() {
		return roboAdvisor;
	}
	
	public void setRoboAdvisor(boolean roboAdvisor) {
		this.roboAdvisor = roboAdvisor;
	}
	
}
