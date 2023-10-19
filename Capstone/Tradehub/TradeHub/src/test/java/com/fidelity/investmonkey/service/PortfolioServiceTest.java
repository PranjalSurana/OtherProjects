package com.fidelity.investmonkey.service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fidelity.investmonkey.exception.ClientNotFoundException;
import com.fidelity.investmonkey.models.Client;
import com.fidelity.investmonkey.models.ClientIdentification;
import com.fidelity.investmonkey.models.Instrument;
import com.fidelity.investmonkey.models.Order;
import com.fidelity.investmonkey.models.Price;
import com.fidelity.investmonkey.models.Trade;

class PortfolioServiceTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	public void getTradeHistoryOfClientSuccess()
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
		ClientIdentification identificationOne = new ClientIdentification("Aadhar","872357934426");
		ClientIdentification identificationTwo = new ClientIdentification("Aadhar","787623432345");
		Client clientOne = new Client("vishakha","6361724765","123abc","vishakhav965@gmail.com",LocalDate.of(2000, 10, 10),"Bharath","573201","vish123", identificationOne);
		Client clientTwo = new Client("Jitin","9875263452","456abc","jitin965@gmail.com",LocalDate.of(2002, 12, 9),"Bharath","573201","jd123", identificationTwo);
		ClientService clientService = new ClientService();
		clientService.addNewClient(clientOne);
		clientService.addNewClient(clientTwo);
		balanceService.pushNewBalance("123abc");
		balanceService.pushNewBalance("456abc");
		
		Order orderOne = new Order(instrOne.getInstrumentId(),10,priceOne.getBidPrice(),"B","123abc","123xyz");
		Order orderTwo = new Order(instrTwo.getInstrumentId(),100,priceTwo.getBidPrice(),"B","456abc","123wxy");
		
		double remainingBalanceOne = tradeService.generateTrade(orderOne, priceOne,balanceService,portfolioService,clientService);
		double remainingBalanceTwo = tradeService.generateTrade(orderTwo, priceTwo, balanceService, portfolioService, clientService);
		List<Trade> tradeHistory = portfolioService.getTradeHistoryOfClient("123abc",clientService);
		assertEquals(tradeHistory.get(0).getInstrumentId(),orderOne.getInstrumentId());
		assertEquals(tradeHistory.size(),1);
	}
	
	@Test
	public void getTradeHistoryOfClientInvalidClient()
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
		
		Order orderOne = new Order(instrOne.getInstrumentId(),10,priceOne.getBidPrice(),"B","123abc","123xyz");
		Order orderTwo = new Order(instrTwo.getInstrumentId(),100,priceTwo.getBidPrice(),"B","123abc","123wxy");
		
		double remainingBalanceOne = tradeService.generateTrade(orderOne, priceOne,balanceService,portfolioService,clientService);
		double remainingBalanceTwo = tradeService.generateTrade(orderTwo, priceTwo, balanceService, portfolioService, clientService);
		
		assertThrows(ClientNotFoundException.class, () -> {
		List<Trade> tradeHistory = portfolioService.getTradeHistoryOfClient("123xyz",clientService);
		});
	}
	
	@Test
	public void getTradeHistoryOfClientExactHundred()
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
		
		for(int i=0;i<100;i++)
		{
			String orderId = i+"abc";
			Order order = new Order(instrOne.getInstrumentId(),10,priceOne.getBidPrice(),"B","123abc",orderId);
			double remainingBalanceOne = tradeService.generateTrade(order, priceOne,balanceService,portfolioService,clientService);
		}
		
		
		List<Trade> tradeHistory = portfolioService.getTradeHistoryOfClient("123abc",clientService);
		assertEquals(tradeHistory.size(),100);
		assertEquals(tradeHistory.get(0).getOrder().getOrderId(),"99abc");
	}
	
	@Test
	public void getTradeHistoryOfClientGreaterThanHundred()
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
		
		for(int i=0;i<120;i++)
		{
			String orderId = i+"abc";
			Order order = new Order(instrOne.getInstrumentId(),10,priceOne.getBidPrice(),"B","123abc",orderId);
			double remainingBalanceOne = tradeService.generateTrade(order, priceOne,balanceService,portfolioService,clientService);
		}
		
		
		List<Trade> tradeHistory = portfolioService.getTradeHistoryOfClient("123abc",clientService);
		assertEquals(tradeHistory.size(),100);
		assertEquals(tradeHistory.get(0).getOrder().getOrderId(),"119abc");
	}
	
	@Test
	public void getTradeHistoryOfClientLessThanHundred()
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
		
		for(int i=0;i<10;i++)
		{
			String orderId = i+"abc";
			Order order = new Order(instrOne.getInstrumentId(),10,priceOne.getBidPrice(),"B","123abc",orderId);
			double remainingBalanceOne = tradeService.generateTrade(order, priceOne,balanceService,portfolioService,clientService);
		}
		
		
		List<Trade> tradeHistory = portfolioService.getTradeHistoryOfClient("123abc",clientService);
		assertEquals(tradeHistory.size(),10);
		assertEquals(tradeHistory.get(0).getOrder().getOrderId(),"9abc");
	}
	
	@Test
	public void testClientDoesNotHaveAnyTrades()
	{
		BalanceService balanceService = new BalanceService();
		PortfolioService portfolioService = new PortfolioService();
		ClientIdentification identification = new ClientIdentification("Aadhar","872357934426");
		Client client = new Client("vishakha","6361724765","123abc","vishakhav965@gmail.com",LocalDate.of(2000, 10, 10),"Bharath","573201","vish123", identification);
		ClientService clientService = new ClientService();
		clientService.addNewClient(client);
		balanceService.pushNewBalance("123abc");
		
		List<Trade> tradeHistory = portfolioService.getTradeHistoryOfClient("123abc",clientService);
		assertEquals(tradeHistory.size(),0);
	}

}
