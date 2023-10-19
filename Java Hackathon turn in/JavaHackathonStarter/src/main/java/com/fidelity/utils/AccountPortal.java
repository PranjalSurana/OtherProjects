package com.fidelity.utils;

import java.math.BigDecimal;
import java.util.List;

public class AccountPortal {
	
	private ClientIdValidator clientId;
	private int capacity;
	private List<Account> portfolio;
	
	public AccountPortal(int capacity, List<Account> portfolio) {
		this.clientId = new ClientIdValidator();
		this.capacity = capacity;
		this.portfolio = portfolio;
	}
	
	public void addAccount(Account account) {	
		
		// Validate clientID before assigning it to an account
		// Check if number of accounts doesn't exceed the limit
		if(account == null) {
			throw new IllegalStateException("Account cannot be null");
		}
        if (portfolio.size() >= capacity) {
            throw new IllegalStateException("List size exceeds the maximum allowed size");
        }
		portfolio.add(account);
	}
	
	public Account getAccount(String accountNumber) {
		if(accountNumber == null || accountNumber == "")
			throw new IllegalStateException("Account Number Empty");
		Account account;
		for(Account a: portfolio) {
			if(a.getAccountNumber() == accountNumber) {
				return a;
			}
		}
		return null;
	}
	
	public List<Account> getPortfolio() {
		return portfolio;
	}
	
	public BigDecimal getTotalBalance() {
		BigDecimal total = BigDecimal.ZERO;
		for(Account account: portfolio) {
			BigDecimal balance = account.calculateCurrentBalance();
			total = total.add(balance);
		}
		return total;
	}
	
	public int getMaximumNumberOfAccounts() {
		return portfolio.size();
	}
	
	public int getRemainingSpaceForAccounts() {
		return capacity - getMaximumNumberOfAccounts();
	}
	
	public void removeAccount(String accountNumber) {
		if(accountNumber == null) {
			throw new IllegalArgumentException("Account number can't be null");
		}
		for (Account account : portfolio) {
			if (accountNumber.equals(account.getAccountNumber())) {
				portfolio.remove(account);
				return;
			}
		}
		throw new IllegalArgumentException("account portal does not contain account " + accountNumber);
	}

}