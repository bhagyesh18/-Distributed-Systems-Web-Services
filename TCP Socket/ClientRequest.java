
import java.io.Serializable;

public class ClientRequest implements Serializable {
	String operation;
	String accountNumber;
	String password;
	int amount;

	public ClientRequest(String operation, String accountNumber, String password, int amount) {
		super();
		this.operation = operation;
		this.accountNumber = accountNumber;
		this.password = password;
		this.amount = amount;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

}
