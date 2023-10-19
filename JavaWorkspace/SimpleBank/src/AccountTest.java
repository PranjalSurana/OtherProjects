import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AccountTest {

//	@Test
//	void test() {
//		fail("Not yet implemented");
//	}
	
	@Test
	void canCreateAcountWithNoArgsConstructor() {
		
		// Arrange
		Account account = new CheckingAccount(null);
		
		// Act
		
		
		// Assert
		assertNotNull(account);
		
	}
	
	@Test
	void depositCannotTakeNegativeAmount() {
		
		// Arrange
		Account account = new CheckingAccount(null);
		int amount = -100;
		
		// Act
		account.deposit(amount);
		
		// Assert
		assertEquals(account.getBalance(), 0);
		
	}
	
	@Test()
	void withdrawCannotBeNegativeAmount() {

		Account account = new CheckingAccount(null);
		int amount = -50;
		
		account.withdraw(amount);
		
		assertEquals(account.getBalance(), 0);
		
	}
	
	@Test()
	void withdrawCannotBeMoreThanBalance() {

		Account account = new CheckingAccount(null);
		account.deposit(10);
		int amount = 50;
		
		account.withdraw(amount);
		
		assertEquals(account.getBalance(), 10);
		
	}

	@Test
	void withdrawSuccess() {

		Account account = new CheckingAccount(null);
		account.deposit(100);
		int amount = 50;
		
		account.withdraw(amount);
		
		assertEquals(account.getBalance(), 50);
		
	}
	
	@Test
	void processCheckFailWithWrongHolderName() {
		
		CheckingAccount account = new CheckingAccount("XYZ");
		account.deposit(1000);
		Check check = new Check("ABC", 100);
		
		assertFalse(account.processCheck(check));
		
	}
	
	@Test
	void depositIncreasesBalanceSpecificBalance() {
		
		Account account = new CheckingAccount("XYZ");
		int amount = 1000;
		account.deposit(amount);
		
		assertEquals(account.getBalance(), amount);
		
	}
	
}
