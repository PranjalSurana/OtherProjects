package com.fidelity.investmonkey.service;

import java.security.SecureRandom;
import java.util.List;

import com.fidelity.investmonkey.exception.ClientNotFoundException;
import com.fidelity.investmonkey.exception.NoSufficientBalanceException;
import com.fidelity.investmonkey.exception.TradeException;
import com.fidelity.investmonkey.models.Holdings;
import com.fidelity.investmonkey.models.Order;
import com.fidelity.investmonkey.models.Price;
import com.fidelity.investmonkey.models.PriceData;
import com.fidelity.investmonkey.models.Trade;

public class TradeService 
{
	
	 private static final String ALLOWED_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

	    // Method to generate a random alphanumeric string of the specified length
	    public static String generateRandomString(int length) {
	        SecureRandom random = new SecureRandom();
	        StringBuilder randomString = new StringBuilder(length);

	        for (int i = 0; i < length; i++) {
	            int randomIndex = random.nextInt(ALLOWED_CHARACTERS.length());
	            char randomChar = ALLOWED_CHARACTERS.charAt(randomIndex);
	            randomString.append(randomChar);
	        }

	        return randomString.toString();
	    }
	    
	private List<Price> priceList;
	
	PriceData priceData = new PriceData();
	
	public void setPriceList(List<Price> priceList) {
        this.priceList = priceList;
    }
	public List<Price> getPriceList() {
        return priceList;
    }
	
	public List<Price> getListOfInstruments()
	{
		return priceList;
	}
	
	public double generateTrade(Order order, Price price,BalanceService balanceService,PortfolioService portfolioService,ClientService clientService)
	{
		if(clientService.getClientById(order.getClientId())==null)
		{
			throw new ClientNotFoundException("Client with clientId: "+order.getClientId()+" is not regestered");
		}
		double cashValue=order.getQuantity()*order.getTargetPrice();
		if(balanceService.getClientBalance(order.getClientId()).getBalance()<cashValue)
		{
			throw new NoSufficientBalanceException("Your cash balance is less to buy it");
		}
		 double tolerance = 0.05;
		double difference =  Math.abs(price.getAskPrice()-price.getBidPrice());
		double fivePercent = price.getAskPrice() * tolerance;
		if(difference > fivePercent)
		{
			throw new TradeException("Difference is greater that 5%. Trade cannot be processed now");
		}
		if(order.getDirection().equals("B"))
		{
			if(order.getQuantity()<price.getInstrument().getMinQuantity())
			{
				throw new TradeException("Buy Quantity must be atleast "+price.getInstrument().getMinQuantity());
			}
			if(order.getQuantity()>price.getInstrument().getMaxQuantity())
			{
				throw new TradeException("Buy quantity cannot exceed "+price.getInstrument().getMaxQuantity());
			}
		}
		Trade trade= new Trade(generateRandomString(6),order.getQuantity(),order.getTargetPrice(),order.getDirection(),
				order,cashValue,order.getClientId(),order.getInstrumentId());

		balanceService.updateClientBalance(order.getClientId(), order.getDirection(), cashValue);
		if(order.getDirection().equals("B"))
		{
			Holdings holding = new Holdings(order.getInstrumentId(),price.getInstrument().getInstrumentDescription(),
					price.getInstrument().getCategoryId(),order.getTargetPrice(),order.getQuantity(),cashValue);
			portfolioService.addHoldingsToList(holding);
		}
		portfolioService.addTradeToHistory(trade);
		return balanceService.getClientBalance(order.getClientId()).getBalance();
	}
	
}
