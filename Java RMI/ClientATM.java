/*
 * 
   Description & Steps of RMI programming on server side:
   > To use RMI Programming for client-server Communication, Create files such as Interface,
     Interface Implementation, Server file, Client file, client.policy and security.policy
   > For Client Side, create Client java file.
   > Server registry is running on specific port in hosted machine.
   > To connect to the server gather Host name and port
   > Naming.lookup("ATM") will look up for the running service on specified port and return
     the interface which is already implemented. 
   > Using returned Interface, call any methods specified in the interface because those
     method are already implemented somewhere at server side. 
   > Call any methods using returned interface and get return values of the methods.  
   > Create client.policy file for the Security Manager and to get the stub from the server.
     
   Instruction / How To Run :
   > Compile javac ClientATM.java
   > java -Djava.security.policy=client.policy ClientATM
   
   How Client Find the Server:
   When Server is already running with registry on specified port. Client creates the stub 
   with host and port to communicate with server stub. Server return the Interface object
   and using this interface object, Client can call any methods of interface which is 
   actually hosted at server. 
   
   
 */

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.util.Scanner;

public class ClientATM {

	public static void main(String[] args) {
		
		int option=0;
	    Scanner scan=new Scanner(System.in);
	    Scanner scanCredentials=new Scanner(System.in);
		String password,result;
		int accountNumber;
		double amount;
		boolean loginStatus,exit=false;
		String message,input;
		
		try
        {

			// Define host and port of registry assigned on the server.
            java.rmi.registry.Registry registry = LocateRegistry.getRegistry("localhost",1099);
            
            // Get the interface implementation from the Server.
            IBankAtm iBankAtm = (IBankAtm) Naming.lookup("ATM");
            
             do{

    	     System.out.println("------------------------");	 
             System.out.println("ATM MACHINE");
	       	 System.out.println("MENU");
	       	 System.out.println("1- Deposit");
	       	 System.out.println("2- Withdraw");
	       	 System.out.println("3- Account Summary");
	       	 System.out.println("4- Exit");
	       	 System.out.println("------------------------");
	       	 System.out.print(">Enter Your Option ::");
	       	 option=scan.nextInt();
	       	 
	       	 switch(option)
	       	 {
	       	 case 1:	
	       		     // Deposit operation 
		       		 System.out.print(">Enter Account Number & Amount$ : ");
					 input=scanCredentials.nextLine();
					 String[] data=input.split(" ");
					 accountNumber=Integer.parseInt(data[0]);
					 amount=Double.parseDouble(data[1]);
					 
					 System.out.print(">Enter Password :: ");
					 password=scanCredentials.nextLine();
					 
					 loginStatus=iBankAtm.validate(accountNumber, password);
					 if(loginStatus){
						message= iBankAtm.deposit(accountNumber, amount);
					 }else{
						 message="Wrong Credentials";
					 }
					 System.out.println(message);
					 break;
					 
	       	 case 2:	 // Withdraw operation 
			       		 System.out.print(">Enter Account Number & Amount$ : ");
						 input=scanCredentials.nextLine();
						 String[] data1=input.split(" ");
						 accountNumber=Integer.parseInt(data1[0]);
						 amount=Double.parseDouble(data1[1]);
						 System.out.print(">Enter Password :: ");
						 password=scanCredentials.nextLine();
						 loginStatus=iBankAtm.validate(accountNumber, password);
						 
						 if(loginStatus){
							message= iBankAtm.withdraw(accountNumber, amount);
						 }else{
							 message="Wrong Credentials";
						 }
						 System.out.println(message);
						 break;
	       		 
			case 3: 
				    // Account Summary operation 
					System.out.print(">Enter Account Number :: ");
					accountNumber = scan.nextInt();
					System.out.print(">Enter Password :: ");
					password = scanCredentials.nextLine();
					loginStatus = iBankAtm.validate(accountNumber, password);
	
					if (loginStatus) {
						message = iBankAtm.summary(accountNumber);
					} else {
						message = "Wrong Credentials";
					}
					System.out.println(message);
					break;
	       		 	
	       	 case 4:// Program Exit 
	       		    System.out.println("Program Exited");
	       	        System.exit(0);
	       		    break;
	       	 }
	       	 
          }while(option<4);   
        
        }
		catch(Exception e){
			
		}
	}

}
