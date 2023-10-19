
public class CheckingAccount implements Account {
	
	private String accHolderName;
	private int balance;

	
	CheckingAccount(String accHolderName) {
		this.accHolderName = accHolderName;
	}
	
	@Override
	public void deposit(int amount) {

		if(amount > 0)
			balance += amount;
		
	}

	@Override
	public int getBalance() {
		return balance;
	}

	@Override
	public void withdraw(int amount) {

		if(amount > 0 && amount <= balance)
			balance -= amount;
		else if (amount > balance) {
		}
		else {
		}
			
	}

	public boolean processCheck(Check check) {
		
		if((check.getAccHolderName() == accHolderName) && (check.getCheckAmount() <= balance)) {
			withdraw(check.getCheckAmount());
			System.out.println(check.getAccHolderName());
			return true;
		}
		
		// Need to write all cases - Easy Debugging
		else {		
			return false;
		}
	}

}
