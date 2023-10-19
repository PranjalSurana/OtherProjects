package com.fidelity.investmonkey.service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fidelity.investmonkey.exception.ClientNotFoundException;
import com.fidelity.investmonkey.exception.NoSufficientBalanceException;
import com.fidelity.investmonkey.exception.TradeException;
import com.fidelity.investmonkey.models.Client;
import com.fidelity.investmonkey.models.ClientIdentification;
import com.fidelity.investmonkey.models.Instrument;
import com.fidelity.investmonkey.models.Order;
import com.fidelity.investmonkey.models.Price;
import com.fidelity.investmonkey.models.PriceData;

public class TradeServiceTest {

	@Before
	public void setUp() throws Exception {
		

		PriceData priceData = new PriceData();
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testGetPriceList()
	{
		Instrument instrOne = new Instrument("N123456","CUSIP","46625H100", "STOCK", "JPMorgan Chase & Co. Capital Stock",1000,1);
		Instrument instrTwo = new Instrument("T67890","CUSIP","9128285M8","GOVT","USA, Note 3.125 15nov2028 10Y",10000,1);
		
		
		Price priceOne = new Price(104.75,104.25,"21-AUG-19 10.00.01.042000000 AM GMT",instrOne);
		Price priceTwo = new Price(1.03375,1.03325,"21-AUG-19 10.00.01.042000000 AM GMT",instrTwo);
		List<Price> prices = new ArrayList<Price>();
		prices.add(priceOne);
		prices.add(priceTwo);
		TradeService tradeService = new TradeService();
		tradeService.setPriceList(prices);
		List<Price> result = tradeService.getListOfInstruments();
		assertEquals(result.get(0).getAskPrice(),priceOne.getAskPrice(),0);
	}
	
	@Test
	public void testGetPriceListNullList()
	{
		TradeService tradeService = new TradeService();
		List<Price> result = tradeService.getListOfInstruments();
		assertEquals(result,null);
	}
	
	@Test
	public void testGenerateTradeSuccess()
	{
		Instrument instrOne = new Instrument("N123456","CUSIP","46625H100", "STOCK", "JPMorgan Chase & Co. Capital Stock",1000,1);
		Instrument instrTwo = new Instrument("T67890","CUSIP","9128285M8","GOVT","USA, Note 3.125 15nov2028 10Y",10000,1);
		
		
		Price priceOne = new Price(104.75,104.25,"21-AUG-19 10.00.01.042000000 AM GMT",instrOne);
		Price priceTwo = new Price(1.03375,1.03325,"21-AUG-19 10.00.01.042000000 AM GMT",instrTwo);
		List<Price> prices = new ArrayList<Price>();
		prices.add(priceOne);
		prices.add(priceTwo);
		TradeService tradeService = new TradeService();
		tradeService.setPriceList(prices);
		List<Price> result = tradeService.getListOfInstruments();
		BalanceService balanceService = new BalanceService();
		PortfolioService portfolioService = new PortfolioService();
		ClientIdentification identification = new ClientIdentification("Aadhar","872357934426");
		Client client = new Client("vishakha","6361724765","123abc","vishakhav965@gmail.com",LocalDate.of(2000, 10, 10),"Bharath","573201","vish123", identification);
		ClientService clientService = new ClientService();
		clientService.addNewClient(client);
		balanceService.pushNewBalance("123abc");
		
		Order order = new Order(instrOne.getInstrumentId(),10,priceOne.getBidPrice(),"B","123abc","123xyz");
		
		double remainingBalance = tradeService.generateTrade(order, priceOne,balanceService,portfolioService,clientService);
		
		assertEquals(remainingBalance,9.999989575E8,0);
	}
	
	@Test
	public void testGenerateTradePortfolioHoldingsAdded()
	{
		Instrument instrOne = new Instrument("N123456","CUSIP","46625H100", "STOCK", "JPMorgan Chase & Co. Capital Stock",1000,1);
		Instrument instrTwo = new Instrument("T67890","CUSIP","9128285M8","GOVT","USA, Note 3.125 15nov2028 10Y",10000,1);
		
		
		Price priceOne = new Price(104.75,104.25,"21-AUG-19 10.00.01.042000000 AM GMT",instrOne);
		Price priceTwo = new Price(1.03375,1.03325,"21-AUG-19 10.00.01.042000000 AM GMT",instrTwo);
		List<Price> prices = new ArrayList<Price>();
		prices.add(priceOne);
		prices.add(priceTwo);
		TradeService tradeService = new TradeService();
		tradeService.setPriceList(prices);
		List<Price> result = tradeService.getListOfInstruments();
		BalanceService balanceService = new BalanceService();
		PortfolioService portfolioService = new PortfolioService();
		ClientIdentification identification = new ClientIdentification("Aadhar","872357934426");
		Client client = new Client("vishakha","6361724765","123abc","vishakhav965@gmail.com",LocalDate.of(2000, 10, 10),"Bharath","573201","vish123", identification);
		ClientService clientService = new ClientService();
		clientService.addNewClient(client);
		balanceService.pushNewBalance("123abc");
		
		Order order = new Order(instrOne.getInstrumentId(),10,priceOne.getBidPrice(),"B","123abc","123xyz");
		
		double remainingBalance = tradeService.generateTrade(order, priceOne,balanceService,portfolioService,clientService);
		
		assertEquals(portfolioService.getHoldings().get(0).getInstrumentId(),instrOne.getInstrumentId());
	}
	
	@Test
	public void testGenerateTradePortfolioHoldingsUpdated()
	{
		Instrument instrOne = new Instrument("N123456","CUSIP","46625H100", "STOCK", "JPMorgan Chase & Co. Capital Stock",1000,1);
		Instrument instrTwo = new Instrument("T67890","CUSIP","9128285M8","GOVT","USA, Note 3.125 15nov2028 10Y",10000,1);
		
		
		Price priceOne = new Price(104.75,104.25,"21-AUG-19 10.00.01.042000000 AM GMT",instrOne);
		Price priceTwo = new Price(1.03375,1.03325,"21-AUG-19 10.00.01.042000000 AM GMT",instrTwo);
		List<Price> prices = new ArrayList<Price>();
		prices.add(priceOne);
		prices.add(priceTwo);
		TradeService tradeService = new TradeService();
		tradeService.setPriceList(prices);
		List<Price> result = tradeService.getListOfInstruments();
		BalanceService balanceService = new BalanceService();
		PortfolioService portfolioService = new PortfolioService();
		ClientIdentification identification = new ClientIdentification("Aadhar","872357934426");
		Client client = new Client("vishakha","6361724765","123abc","vishakhav965@gmail.com",LocalDate.of(2000, 10, 10),"Bharath","573201","vish123", identification);
		ClientService clientService = new ClientService();
		clientService.addNewClient(client);
		balanceService.pushNewBalance("123abc");
		
		Order order = new Order(instrOne.getInstrumentId(),10,priceOne.getBidPrice(),"B","123abc","123xyz");
		Order orderTwo = new Order(instrOne.getInstrumentId(),10,priceTwo.getBidPrice(),"B","123abc","123aaa");

		double remainingBalanceOne = tradeService.generateTrade(order, priceOne,balanceService,portfolioService,clientService);
		double remainingBalanceTwo = tradeService.generateTrade(orderTwo, priceTwo, balanceService, portfolioService, clientService);
		assertEquals(portfolioService.getHoldings().size(),2);
		assertEquals(portfolioService.getHoldings().get(0).getQuantity(),20);
	}
	
	@Test
	public void testGenerateTradeQuantityLessThanMinimum()
	{
		Instrument instrOne = new Instrument("N123456","CUSIP","46625H100", "STOCK", "JPMorgan Chase & Co. Capital Stock",1000,10);
		Instrument instrTwo = new Instrument("T67890","CUSIP","9128285M8","GOVT","USA, Note 3.125 15nov2028 10Y",10000,1);
		
		
		Price priceOne = new Price(104.75,104.25,"21-AUG-19 10.00.01.042000000 AM GMT",instrOne);
		Price priceTwo = new Price(1.03375,1.03325,"21-AUG-19 10.00.01.042000000 AM GMT",instrTwo);
		List<Price> prices = new ArrayList<Price>();
		prices.add(priceOne);
		prices.add(priceTwo);
		TradeService tradeService = new TradeService();
		tradeService.setPriceList(prices);
		List<Price> result = tradeService.getListOfInstruments();
		BalanceService balanceService = new BalanceService();
		PortfolioService portfolioService = new PortfolioService();
		ClientIdentification identification = new ClientIdentification("Aadhar","872357934426");
		Client client = new Client("vishakha","6361724765","123abc","vishakhav965@gmail.com",LocalDate.of(2000, 10, 10),"Bharath","573201","vish123", identification);
		ClientService clientService = new ClientService();
		clientService.addNewClient(client);
		balanceService.pushNewBalance("123abc");
		
		Order order = new Order(instrOne.getInstrumentId(),1,priceOne.getBidPrice(),"B","123abc","123xyz");
		assertThrows(TradeException.class, () -> {
		double remainingBalance = tradeService.generateTrade(order, priceOne,balanceService,portfolioService,clientService);
		});
	}
	
	@Test
	public void testGenerateTradeQuantityGreaterThanMaximum()
	{
		Instrument instrOne = new Instrument("N123456","CUSIP","46625H100", "STOCK", "JPMorgan Chase & Co. Capital Stock",1000,10);
		Instrument instrTwo = new Instrument("T67890","CUSIP","9128285M8","GOVT","USA, Note 3.125 15nov2028 10Y",10000,1);
		
		
		Price priceOne = new Price(104.75,104.25,"21-AUG-19 10.00.01.042000000 AM GMT",instrOne);
		Price priceTwo = new Price(1.03375,1.03325,"21-AUG-19 10.00.01.042000000 AM GMT",instrTwo);
		List<Price> prices = new ArrayList<Price>();
		prices.add(priceOne);
		prices.add(priceTwo);
		TradeService tradeService = new TradeService();
		tradeService.setPriceList(prices);
		List<Price> result = tradeService.getListOfInstruments();
		BalanceService balanceService = new BalanceService();
		PortfolioService portfolioService = new PortfolioService();
		ClientIdentification identification = new ClientIdentification("Aadhar","872357934426");
		Client client = new Client("vishakha","6361724765","123abc","vishakhav965@gmail.com",LocalDate.of(2000, 10, 10),"Bharath","573201","vish123", identification);
		ClientService clientService = new ClientService();
		clientService.addNewClient(client);
		balanceService.pushNewBalance("123abc");
		
		Order order = new Order(instrOne.getInstrumentId(),10000,priceOne.getBidPrice(),"B","123abc","123xyz");
		assertThrows(TradeException.class, () -> {
		double remainingBalance = tradeService.generateTrade(order, priceOne,balanceService,portfolioService,clientService);
		});
	}
	
	@Test
	public void testGenerateTradeInvalidClient()
	{
		Instrument instrOne = new Instrument("N123456","CUSIP","46625H100", "STOCK", "JPMorgan Chase & Co. Capital Stock",1000,1);
		Instrument instrTwo = new Instrument("T67890","CUSIP","9128285M8","GOVT","USA, Note 3.125 15nov2028 10Y",10000,1);
		
		
		Price priceOne = new Price(104.75,104.25,"21-AUG-19 10.00.01.042000000 AM GMT",instrOne);
		Price priceTwo = new Price(1.03375,1.03325,"21-AUG-19 10.00.01.042000000 AM GMT",instrTwo);
		List<Price> prices = new ArrayList<Price>();
		prices.add(priceOne);
		prices.add(priceTwo);
		TradeService tradeService = new TradeService();
		tradeService.setPriceList(prices);
		List<Price> result = tradeService.getListOfInstruments();
		BalanceService balanceService = new BalanceService();
		PortfolioService portfolioService = new PortfolioService();
		ClientIdentification identification = new ClientIdentification("Aadhar","872357934426");
		Client client = new Client("vishakha","6361724765","123abc","vishakhav965@gmail.com",LocalDate.of(2000, 10, 10),"Bharath","573201","vish123", identification);
		ClientService clientService = new ClientService();
		clientService.addNewClient(client);
		balanceService.pushNewBalance("123abc");
		
		Order order = new Order(instrOne.getInstrumentId(),10,priceOne.getBidPrice(),"B","123xyz","123xyz");
		
		assertThrows(ClientNotFoundException.class, () -> {
		double remainingBalance = tradeService.generateTrade(order, priceOne,balanceService,portfolioService,clientService);
		});
	}
	
	@Test
	public void testGenerateTradeDifferenceLessThan5Percent()
	{
		Instrument instrOne = new Instrument("N123456","CUSIP","46625H100", "STOCK", "JPMorgan Chase & Co. Capital Stock",1000,1);
		Instrument instrTwo = new Instrument("T67890","CUSIP","9128285M8","GOVT","USA, Note 3.125 15nov2028 10Y",10000,1);
		
		
		Price priceOne = new Price(100004.75,104.25,"21-AUG-19 10.00.01.042000000 AM GMT",instrOne);
		Price priceTwo = new Price(1.03375,1.03325,"21-AUG-19 10.00.01.042000000 AM GMT",instrTwo);
		List<Price> prices = new ArrayList<Price>();
		prices.add(priceOne);
		prices.add(priceTwo);
		TradeService tradeService = new TradeService();
		tradeService.setPriceList(prices);
		List<Price> result = tradeService.getListOfInstruments();
		BalanceService balanceService = new BalanceService();
		PortfolioService portfolioService = new PortfolioService();
		ClientIdentification identification = new ClientIdentification("Aadhar","872357934426");
		Client client = new Client("vishakha","6361724765","123abc","vishakhav965@gmail.com",LocalDate.of(2000, 10, 10),"Bharath","573201","vish123", identification);
		ClientService clientService = new ClientService();
		clientService.addNewClient(client);
		
		balanceService.pushNewBalance("123abc");
		
		Order order = new Order(instrOne.getInstrumentId(),10,priceOne.getBidPrice(),"B","123abc","123xyz");
		assertThrows(TradeException.class, () -> {
		double remainingBalance = tradeService.generateTrade(order, priceOne,balanceService,portfolioService,clientService);
		});
	}

	@Test
	public void testGenerateTradewhenBalanceLessThanTargetPrice()
	{
		Instrument instrOne = new Instrument("N123456","CUSIP","46625H100", "STOCK", "JPMorgan Chase & Co. Capital Stock",1000,1);
		Instrument instrTwo = new Instrument("T67890","CUSIP","9128285M8","GOVT","USA, Note 3.125 15nov2028 10Y",10000,1);
		
		
		Price priceOne = new Price(10000000004.75,10000000004.25,"21-AUG-19 10.00.01.042000000 AM GMT",instrOne);
		Price priceTwo = new Price(1.03375,1.03325,"21-AUG-19 10.00.01.042000000 AM GMT",instrTwo);
		List<Price> prices = new ArrayList<Price>();
		prices.add(priceOne);
		prices.add(priceTwo);
		TradeService tradeService = new TradeService();
		tradeService.setPriceList(prices);
		List<Price> result = tradeService.getListOfInstruments();
		BalanceService balanceService = new BalanceService();
		PortfolioService portfolioService = new PortfolioService();
		ClientIdentification identification = new ClientIdentification("Aadhar","872357934426");
		Client client = new Client("vishakha","6361724765","123abc","vishakhav965@gmail.com",LocalDate.of(2000, Month.OCTOBER, 10),"Bharath","573201","vish123", identification);
		ClientService clientService = new ClientService();
		clientService.addNewClient(client);
		balanceService.pushNewBalance("123abc");
		
		Order order = new Order(instrOne.getInstrumentId(),10,priceOne.getBidPrice(),"B","123abc","123xyz");
		
		assertThrows(NoSufficientBalanceException.class, () -> {
			double remainingBalance = tradeService.generateTrade(order, priceOne,balanceService,portfolioService,clientService);

		});
	}
}
