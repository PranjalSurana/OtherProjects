package com.fidelity.investmonkey.service;

import java.util.ArrayList;
import java.util.List;

import com.fidelity.investmonkey.exception.ClientNotFoundException;
import com.fidelity.investmonkey.models.Balance;

public class BalanceService 
{
	List<Balance> balances = new ArrayList<Balance>();
	
	public void pushNewBalance(String clientId)
	{
		balances.add(new Balance(clientId));
	}
	
	public Balance getClientBalance(String clientId)
	{
	    for(Balance balance:balances)
	    {
	    	if(balance.getClientId().equals(clientId))
	    	{
	    		return balance;
	    	}
	    }
	    throw new ClientNotFoundException("Client with ID: "+clientId+" not found");
	  }
	
	public Balance updateClientBalance(String clientId,String direction,double amount)
	{
		for(Balance balance:balances)
		{
			if(balance.getClientId().equals(clientId))
	    	{
				if(direction.equals("B"))
				{
				double bal = balance.getBalance()-amount;
	    		balance.setBalance(bal);
	    		return balance;
				}
				if(direction=="S")
				{
					double bal = balance.getBalance()+amount;
					balance.setBalance(bal);
					return balance;
				}
	    	}
		}
		 throw new ClientNotFoundException("Client with ID: "+clientId+" not found");
	}
}
