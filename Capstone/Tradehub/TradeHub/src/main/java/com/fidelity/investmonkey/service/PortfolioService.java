package com.fidelity.investmonkey.service;

import java.util.ArrayList;
import java.util.List;

import com.fidelity.investmonkey.exception.ClientNotFoundException;
import com.fidelity.investmonkey.models.Holdings;
import com.fidelity.investmonkey.models.Trade;

public class PortfolioService 
{
	private List<Holdings> holdings = new ArrayList<Holdings>();
	private List<Trade> tradeHistory = new ArrayList<Trade>();
	
	
	public void addHoldingsToList(Holdings holding)
	{
		for(Holdings hld:holdings)
		{
			if(hld.getInstrumentId()==holding.getInstrumentId())
			{
				int quantity = holding.getQuantity()+hld.getQuantity();
				hld.setQuantity(quantity);
			}
		}
		holdings.add(holding);
	}
	
	public Holdings getHoldingByInstrumentId(String instrumentId)
	{
		for(Holdings hld:holdings)
		{
			if(hld.getInstrumentId()==instrumentId)
			{
				return hld; 
			}
		}
		return null;
	}
	
	public void removeHoldingsFromList(Holdings holding)
	{
		for(Holdings hld:holdings)
		{
			if(hld.getInstrumentId()==holding.getInstrumentId())
			{
				int quantity = hld.getQuantity()- holding.getQuantity();
				hld.setQuantity(quantity);
			}
		}
		holdings.remove(getHoldingByInstrumentId(holding.getInstrumentId()));
	}
	
	public List<Holdings> getHoldings()
	{
		return this.holdings;
	}
	
	public void addTradeToHistory(Trade trade)
	{
		tradeHistory.add(0, trade);
	}
	
	public List<Trade> getAllTrades()
	{
		return this.getAllTrades();
	}
	
	public List<Trade> getTradeHistoryOfClient(String clientId,ClientService clientService)
	{
		if(clientService.getClientById(clientId)==null)
		{
			throw new ClientNotFoundException("Client with clientId: "+clientId+" is not registered");
		}
		int i=0;
		List<Trade> clientTradeHistory = new ArrayList<Trade>();
		for(Trade trade:tradeHistory)
		{
			if(trade.getClientId()==clientId && i<100)
			{
				clientTradeHistory.add(trade);
				i++;
			}
		}
		return clientTradeHistory;
	}
}
