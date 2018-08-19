package com.capgemini.service;

import com.capgemini.exceptions.InsufficientBalanceException;
import com.capgemini.exceptions.InsufficientInitialAmountException;
import com.capgemini.exceptions.InvalidAccountNumberException;
import com.capgemini.model.Account;
import com.capgemini.repository.AccountRepository;

public class AccountServiceImpl implements AccountService {
	
	/* (non-Javadoc)
	 * @see com.capgemini.service.AccountService#createAccount(int, int)
	 */
	
	AccountRepository accountRepository;
	
	
	public AccountServiceImpl(AccountRepository accountRepository) {
		super();
		this.accountRepository = accountRepository;
	}


	@Override
	public Account createAccount(int accountNumber,int amount) throws InsufficientInitialAmountException
	{
		int a,b,c;
		if(amount<500)
		{
			throw new InsufficientInitialAmountException();
		}
		Account account = new Account();
		account.setAccountNumber(accountNumber);
		account.setAmount(amount);
		 
		if(accountRepository.save(account))
		{
			return account;
		}
	     
		return null;
		
	}


	@Override
	public Account Deposit(int accountNumber, int amount) throws InvalidAccountNumberException {
		// TODO Auto-generated method stub
		Account acc= new Account();
		acc = accountRepository.searchAccount(accountNumber);
		if(acc == null)
		{
			throw new InvalidAccountNumberException();
		}
		acc.setAmount(acc.getAmount() + amount);
		if(accountRepository.save(acc))
		{
			return acc;
		}
	     
		return null;
	}


	@Override
	public Account Withdraw(int accountNumber, int amount) throws InvalidAccountNumberException, InsufficientBalanceException {
		// TODO Auto-generated method stub
		Account acc1= new Account();
		acc1 = accountRepository.searchAccount(accountNumber);
		if(acc1 == null)
		{
			throw new InvalidAccountNumberException();
		}
		if((acc1.getAmount() - amount) < 500)
		{
			throw new InsufficientBalanceException();
		}
		acc1.setAmount(acc1.getAmount() - amount);
		if(accountRepository.save(acc1))
		{
			return acc1;
		}
		return null;
	}


	@Override
	public Account Display(int accountNumber) throws InvalidAccountNumberException {
		// TODO Auto-generated method stub
		Account account2 = new Account();
		account2 = accountRepository.searchAccount(accountNumber);
		if(account2 == null)
		{
			throw new InvalidAccountNumberException();
		}
		
		return account2;
	}
	
	

}
