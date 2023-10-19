package com.fidelity.investmonkey.service;
import com.fidelity.investmonkey.exception.NullException;
import com.fidelity.investmonkey.models.*;
import java.util.ArrayList;
import java.util.List;
public class AddInvestmentPreferance {
	private List<InvestmentPrefModel> listinvestmentpref = new ArrayList<InvestmentPrefModel>();
	
	
}
public boolean find(String clientId) {
		for(InvestmentPrefModel model: listinvestmentpref) {
			if(model.getClientId()==clientId) {
				return true;
			}
		}
		return false;
	}
	
	public void addInvestmentPref(InvestmentPrefModel investPref) {
		if(investPref == null) {
			throw new NullException("Investment Preferance cannot be null");
		}
		if(!find(investPref.getClientId())) {
			throw new IllegalArgumentException("Client Not found");
		}
		listinvestmentpref.add(investPref);
	}