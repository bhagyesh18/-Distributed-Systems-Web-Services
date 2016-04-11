
#include <stdio.h>
#include <rpc/rpc.h>
#include "bankatm.h"


int main (int argc, char *argv[]) {
	
	CLIENT *clnt;
	int  *validateResult;	
	float  *changedAmount;	
	char *host;
	bankDatabase *inputBd; 
	int option; 
	// initilize the structure bankDatabase 
	inputBd = (bankDatabase*)malloc(sizeof(bankDatabase)); 
	host = "localhost";
	do{
			printf("_______________________\n");			
			printf("XYZ ATM MACHINE. \n");
				
			// Create conneciton with server using host name			
			clnt = clnt_create (host, BANKATM,BANKATM_VERS, "udp");
			if (clnt == NULL) {
				clnt_pcreateerror (host);
				exit (1);
			}
			printf("1. Deposit\n");
			printf("2. Withdraw\n");
			printf("3. Balance\n");
			printf("4. Exit\n");

		   	printf("Select Option :");
		    	scanf("%d", &option);


 				    switch(option){
				    case 1:	printf("Enter Account Number & Amount : ");
						scanf("%d %f$\n", &inputBd->accountNumber, &inputBd->amount);
						printf("Password: ");
						scanf("%d", &inputBd->password);
						
						//validate Account Number and Password
						validateResult = validate_1(inputBd, clnt); 
						// check validation result							
						if (*validateResult == 2) { 
							// Deposit RPC function invoke	
							changedAmount = deposit_1(inputBd, clnt); 
							printf("Deposited Successfully, Updated balance is : $%0.02f\n", *changedAmount); 	
						} else if (*validateResult == 1 || *validateResult == 0) {
							printf("Wrong Credentials\n"); 
						}
					    	break;
				    case 2: 
						
						printf("Enter Account Number & Amount : ");
						scanf("%d %f$\n", &inputBd->accountNumber, &inputBd->amount);
						printf("Password: ");
						scanf("%d", &inputBd->password);
					
						//validate Account Number and Password
						validateResult = validate_1(inputBd, clnt); 						
						if (*validateResult == 2) { 
							// Withdraw RPC function invoke
							changedAmount = withdraw_1(inputBd, clnt);
							printf("Withdraw Successfully, Updated balance: is $%0.02f\n", *changedAmount); 
						} else if (*validateResult == 1 || *validateResult == 0) {
							printf("Wrong Credentials\n"); 
						}
				    		break;
    
				   case 3:  
						printf("Account: ");
						scanf("%d", &inputBd->accountNumber);
						printf("Password: ");
						scanf("%d", &inputBd->password);
						//validate Account Number and Password
						validateResult = validate_1(inputBd, clnt); 
						if (*validateResult == 2) { 
							printf("Account Summary/n");
							// summary RPC function invoke						    
							changedAmount = summary_1(inputBd, clnt);
							printf("Account number : %d \n ",inputBd->accountNumber);	
						        printf("Balance: $%0.02f\n", *changedAmount);
						} else if (*validateResult == 1 || *validateResult == 0) {
						    printf("Wrong Credentials\n"); 
						}
 						break;
				}
				    
		}while(option<4);
		clnt_destroy (clnt);
		return;
}
