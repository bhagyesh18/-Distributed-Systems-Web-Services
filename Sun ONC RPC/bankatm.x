struct bankDatabase {	
	int accountNumber;
	int password; 
	float totalBalance; 
	float amount; 
};

program BANKATM {
	version BANKATM_VERS {		
			int VALIDATE(bankDatabase) =1; 
			float DEPOSIT(bankDatabase) =2;		
			float WITHDRAW(bankDatabase) =3;
			float SUMMARY(bankDatabase) =4;
	} = 1;
} = 0x65372837;
 
