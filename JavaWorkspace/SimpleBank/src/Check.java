
public class Check {

	private String accHolderName;
	private int checkAmount;
	
	public Check(String accHolderName, int checkAmount) {
		this.accHolderName = accHolderName;
		this.checkAmount = checkAmount;
	}
	
	public String getAccHolderName() {
		return accHolderName;
	}
	
	public int getCheckAmount() {
		return checkAmount;
	}
	
}
