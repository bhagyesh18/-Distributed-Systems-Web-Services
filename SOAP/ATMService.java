package hw6;

/*
  
 # Description :
    The service class is an implementation of the methods in the Interface class. 
    This class is annotated with @WebService which is to mark an endpoint 
    implementation as implementing a web service. 

 # The @WebService has two properties set: 
    - endpointInterface: is the qualified name of the service endpoint interface.
    - targetNamespace: is the XML namespace of the WSDL and some of the XML 
        elements generated from this web service. 

 # Instruction and Server Description :
   Implement the Remote interface class and override each method with @override and @ webmethod 
   annotation.
   use @weService annotation to specify the class as SOAP Web service Remote Implementation class.
   define the end point interface name and target namespace for the SOAP web service. 

 # How to run the application?
    - The Interface, Service, and Publish files need to be compiled.
    - After a successful compilation of the above files, we’ll start the service
    - Compile and run the client file.
 
 */
import java.util.ArrayList;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(endpointInterface = "hw6.IATMService", targetNamespace="http://server.assignment6/")
public class ATMService implements IATMService {

    //structure to store bank account data
    static class BankData {

        int accountNumber;
        String password;
        double currentBalance;

        BankData(int accountNumber, String password, double currentBalance) {
            this.accountNumber = accountNumber;
            this.password = password;
            this.currentBalance = currentBalance;
        }
    }

    static ArrayList<BankData> bankDataList = null;

    static String userPassword = "";

    // Default constructor to initilize the temporary data.
    ATMService() {
        //populate account info for each customer
    	setTempData();
    }

    /**
     * Local method to initialize customers account info Not exposed to web
     * service
     */
    public static void setTempData() {
        bankDataList = new ArrayList<>();
        BankData bankData = new BankData(123456, "Hello", 600.0);
        bankDataList.add(bankData);
        bankData = new BankData(7891011, "Yello", 3000.0);
        bankDataList.add(bankData);
        bankData = new BankData(121314, "Hello123", 10000.0);
        bankDataList.add(bankData);
    }

    /**
     * Method to authenticate a user to access account.
     * This is Web method and Overide this method as it is part of SOAP Web service
     * @param accountNumber
     * @param password
     * @return
     */
    @WebMethod
    @Override
    public boolean authenticate(int accountNumber, String password) {

        if (getBankData(accountNumber, password) > -1) {
            userPassword = password;
            return true;
        } else //no match
        {
            userPassword = "";
            return false;
        }
    }

    /**
     * Deposits an amount to the existing balance
     * This is Web method and Overide this method as it is part of SOAP Web service
     * @param accountNumber
     * @param amount
     * @return
     */
    @WebMethod
    @Override
    public boolean deposit(int accountNumber, double amount) {
        int accountRecordIndex = getBankData(accountNumber);
        if (accountRecordIndex > -1) {
            //create a local copy of the account record to update
            BankData bd = bankDataList.get(accountRecordIndex);
            //get the current balance and add the new amount;
            bd.currentBalance = bankDataList.get(accountRecordIndex).currentBalance + amount;
            //update the record with the new balance
            bankDataList.set(accountRecordIndex, bd);
            return true;
        }
        return false;
    }

    /**
     * Deducts an amount from an account
     * This is Web method and Overide this method as it is part of SOAP Web service
     * @param accountNumber
     * @param amount
     * @return boolean 
     */
    @WebMethod
    @Override
    public boolean withdraw(int accountNumber, double amount) {
        int accountRecordIndex = getBankData(accountNumber);
        if (accountRecordIndex > -1) {
            //create a local copy of the account record
            BankData bd = bankDataList.get(accountRecordIndex);
            //get the current balance and subtract the new amount;
            bd.currentBalance = bankDataList.get(accountRecordIndex).currentBalance - amount;
            //update the record with the new balance
            bankDataList.set(accountRecordIndex, bd);

            return true;
        }

        return false;
    }

    /**
     * Generate account summary
     * This is Web method and Overide this method as it is part of SOAP Web service
     * @param accountNumber
     * @return String
     */
    @WebMethod
    @Override
    public String summary(int accountNumber) {
        int accountRecordIndex = getBankData(accountNumber);
        if (accountRecordIndex > -1) {
            return "Account number : " + accountNumber
                    + "  Current balance : $" + bankDataList.get(accountRecordIndex).currentBalance;
        }
        return " Error: Unable to generate summary!";
    }

    /**
     * get the bank data from bankDatalist for requested account number
     * 
     * @param accountNumber
     * @param password
     * @return integer index of the matching account number
     */
    public static int getBankData(int accountNumber, String password) {

        //loop through each account and find a match
        for (int i = 0; i < bankDataList.size(); i++) {
            if (accountNumber == bankDataList.get(i).accountNumber
                    && password.equals(bankDataList.get(i).password)) {
                return i;
            }
        }

        //if we hit this line, no account is found.
        return -1;

    }

    /**
     * Overloaded method to return account record index. Assumes the user is
     * authenticated
     *
     * @param accountNumber
     * @return
     */
    public static int getBankData(int accountNumber) {
        return getBankData(accountNumber, userPassword);
    }

}
