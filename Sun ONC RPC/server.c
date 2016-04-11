
#include <rpc/rpc.h>
#include <stdio.h>
#include "bankatm.h"

// If appStatus is zero, it is called first when server gets started,
// else server is already running. 
static int appStatus = 0; 
static int  res;

bankDatabase *bankDataList[3]; 


/*
Desc: This function validates the account number and password. even on first time server starting
it creates sample data also.
param: bankDatabase, struct svc_req
return: float
*/
int *validate_1_svc(bankDatabase *bd, struct svc_req *clientRequest) {	
	
	int i; 
	if(appStatus == 0) {	

			bankDataList[0] = (bankDatabase*)malloc(sizeof(bankDatabase));
			bankDataList[0]->accountNumber = 1000;
			bankDataList[0]->password = 12345; 
			bankDataList[0]->totalBalance = 45.90; 			
			bankDataList[0]->amount = 0; 
			
			bankDataList[1] = (bankDatabase*)malloc(sizeof(bankDatabase));
			bankDataList[1]->accountNumber = 2000;
			bankDataList[1]->password = 12345; 
			bankDataList[1]->totalBalance = 900.96; 			
			bankDataList[1]->amount = 0;

			bankDataList[2] = (bankDatabase*)malloc(sizeof(bankDatabase));
			bankDataList[2]->accountNumber = 3000;
			bankDataList[2]->password = 12345; 
			bankDataList[2]->totalBalance = 678.10; 			
			bankDataList[2]->amount = 0;
			
			appStatus = 1; 
	}

	i=0;
	while(i<3)
	{
		if (bankDataList[i]->accountNumber == bd->accountNumber) { 
				if (bankDataList[i]->password == bd->password) {
					res = 2;         
					break; }
				else {
					res = 1;
					break; }				
		}
		else 
		 	res = 0; 
		i++;
	}

	return &res;
}

/*
Desc: get the index from array of sturcture for account number
param: bankDatabase, struct svc_req
return: float
*/
int getBankData(bankDatabase *bd)
{
	int i=0;
	for(i=0;i<3;i++)
	{
			if (bankDataList[i]->accountNumber == bd->accountNumber){ 
				return i;	}
	}
	return;
}


/*
Desc: Deposit Dollars into specific Account 
param: bankDatabase, struct svc_req
return: float
*/
float *deposit_1_svc(bankDatabase *bd, struct svc_req *clientRequest) {	
	int index;	
	index=getBankData(bd);	
	if(bd->amount > 0) {
		printf("Deposit Successful!\n"); 
		bankDataList[index]->totalBalance += bd->amount; 
		return &(bankDataList[index]->totalBalance);
	}
}


/*
Desc: withdraw Dollars from specific Account 
param: bankDatabase, struct svc_req
return: float
*/
float *withdraw_1_svc(bankDatabase *bd, struct svc_req *clientRequest) {	
	int index;	
	index=getBankData(bd);	

	if(bankDataList[index]->totalBalance >= bd->amount) { 
		printf("Withdraw successful!\n"); 
		bankDataList[index]->totalBalance -= bd->amount;
		return &(bankDataList[index]->totalBalance); 
	}
	return 0;
}


/*
Desc: Account summary of specific account
param: bankDatabase, struct svc_req
return: float
*/
float *summary_1_svc(bankDatabase *bd, struct svc_req *clientRequest) {
	int index;	
	index=getBankData(bd);	
	printf("Account Summary \nTotal Balance: %f \n", bankDataList[index]->totalBalance); 
	return &(bankDataList[index]->totalBalance);	
}

