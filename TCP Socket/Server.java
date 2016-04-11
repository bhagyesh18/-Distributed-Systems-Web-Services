

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * @author Bhagyesh & Yonas
 * Server Class is Server. It is Hosted at Localhost and Port 4045. 
 * accept the request from client in Object form and respond each request using stream.
 */
public class Server{
	
	static ArrayList<BankData> bankDataList;
	
	// PORT Number 
	static int port=4045;
		
    /**
     * Method Validates Account and Password 
     * @param clientRequest
     * @return boolean
     */
    public static boolean validate(ClientRequest clientRequest){
    	boolean flag=false;
    	for(int i=0;i<bankDataList.size();i++){
    		BankData bankd=new BankData();
    		if(bankDataList.get(i).getAccountNumber().equals(clientRequest.getAccountNumber())){
    			bankd=bankDataList.get(i);
    			if(bankd.password.equals((clientRequest.getPassword())))
    			{
    				return true;
    			}
    		}
    	}
    	return false;
    }
    
    
    /**
     * Add Temp Data using Model BankData
     * @return BankDataList of type Arraylist<BankData>
     */
    public static ArrayList<BankData> addData(){
    		ArrayList<BankData> bankDataList=new ArrayList<BankData>();
			BankData bankData=new BankData("123456","Hello",600);
			bankDataList.add(bankData);
			bankData= new BankData("7891011","Yello",3000);
			bankDataList.add(bankData);
			bankData =new BankData("121314","Hello123",10000);
			bankDataList.add(bankData);
	 	    return bankDataList;
    }
    
    
    /**
     * update the database of BankData 
     * @param clientRequest
     * @param BankData
     * @param bd
     */
    public static void updateData(ClientRequest clientRequest,BankData bd){
    	for(int i=0;i<bankDataList.size();i++){
			if(bankDataList.get(i).getAccountNumber().equals(clientRequest.getAccountNumber())){
				bankDataList.get(i).setTotalBalance(bd.getTotalBalance());
			}
    	}
    }
    
    /**
     * Get the BankData Entity from the ArrayList<BankData>
     * @param clientRequest
     * @return BankData
     */
    public static BankData getBankData(ClientRequest clientRequest){
    	BankData bankData=new BankData();
    	for(int i=0;i<bankDataList.size();i++){
			if(bankDataList.get(i).getAccountNumber().equals(clientRequest.getAccountNumber())){
				bankData=bankDataList.get(i);
			}
		}
    	return bankData;
    }
    
    
	/**
	 * Main Method
	 * @param args
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException {
			
			int amount,balance;
			ServerSocket serverSideSockerObj;
		    ClientRequest clientRequest;
		    boolean login=true;
		   
			serverSideSockerObj = new ServerSocket(port);
	
			bankDataList=addData();
			Socket socketOfServer = serverSideSockerObj.accept();
	           
        	DataOutputStream outputStreamTOclient = new DataOutputStream(socketOfServer.getOutputStream());
        	outputStreamTOclient.writeUTF("Connection with Bank Server Successfull\nWelcome To XYZ BANK");
        	InputStream is = socketOfServer.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(is);
		    
			while((clientRequest = (ClientRequest) ois.readObject())!=null)
		      {
		         try
		         {
		        	// Check the request object is null or not. 
					if (clientRequest != null) {
						
						 // Validate Account number and password 
						 if(clientRequest.getOperation().equals("Validate")){
							if(validate(clientRequest)){
								outputStreamTOclient.writeUTF("Correct");
								login=true;
							}else{
								outputStreamTOclient.writeUTF("Incorrect");
								login=false;
							}
						  }
						
						
						// If User is verified  
						if(login){
							
							// Deposit operation 
							if(clientRequest.getOperation().equals("Deposit")){
								balance=0;
								BankData bd=getBankData(clientRequest);
								balance=bd.getTotalBalance();
								balance=balance+clientRequest.getAmount();
								bd.setTotalBalance(balance);
								updateData(clientRequest,bd);	
								System.out.println("New Balance for Account Number ("+ clientRequest.getAccountNumber() +")is : "+balance);
								outputStreamTOclient.writeUTF("Amount has been Deposited Succcesfully \nUpdated Balance of Account "+ clientRequest.getAccountNumber() +" is : "+balance);
							}
							
							// WithDraw operation 
							if(clientRequest.getOperation().equals("Withdraw")){
								balance=0;
								BankData bankd=getBankData(clientRequest);
								balance=bankd.getTotalBalance();
								balance=balance-clientRequest.getAmount();
								bankd.setTotalBalance(balance);
								updateData(clientRequest, bankd);
								System.out.println("New Balance for Account Number ("+ clientRequest.getAccountNumber() +")is : "+balance);
								outputStreamTOclient.writeUTF("Amount has been Withdraw Succcesfully \nUpdated Balance Of Account "+ clientRequest.getAccountNumber() +" is : "+balance);
							}
						
							// Summary operation 
							if(clientRequest.getOperation().equals("Summary")){
								balance=0;
								BankData bankda=getBankData(clientRequest);
								balance=bankda.getTotalBalance();
								System.out.println("Balance for Account Number ("+ clientRequest.getAccountNumber() +")is : "+balance);
								outputStreamTOclient.writeUTF("Account Number : "+ clientRequest.getAccountNumber() +"\nBalance : "+balance);
						    }
								
							// Logged out operation 
							if(clientRequest.getOperation().equals("EXIT")){
								outputStreamTOclient.writeUTF("");
							}
						}
					}
		         }catch(Exception e){
		        	 System.out.println(e.getMessage());
		         }
		       	
		      }
			serverSideSockerObj.close();
			socketOfServer.close();
	}

}
