
class BankData {
	String accountNumber;
	String SavingNumber;
	String password;
	int totalBalance;

	public BankData() {

	}

	public BankData(String accountNumber, String password, int totalBalance) {
		super();
		this.accountNumber = accountNumber;
		this.password = password;
		this.totalBalance = totalBalance;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getSavingNumber() {
		return SavingNumber;
	}

	public void setSavingNumber(String savingNumber) {
		SavingNumber = savingNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getTotalBalance() {
		return totalBalance;
	}

	public void setTotalBalance(int totalBalance) {
		this.totalBalance = totalBalance;
	}

}