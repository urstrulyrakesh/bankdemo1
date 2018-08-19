package com.capgemini.test;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.capgemini.exceptions.InsufficientBalanceException;
import com.capgemini.exceptions.InsufficientInitialAmountException;
import com.capgemini.exceptions.InvalidAccountNumberException;
import com.capgemini.model.Account;
import com.capgemini.repository.AccountRepository;
import com.capgemini.service.AccountService;
import com.capgemini.service.AccountServiceImpl;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
public class AccountTest {

	AccountService accountService;
	
	@Mock
	AccountRepository accountRepository;
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		accountService = new AccountServiceImpl(accountRepository);
	}

	/*
	 * create account
	 * 1.when the amount is less than 500 then system should throw exception
	 * 2.when the valid info is passed account should be created successfully
	 */
	
	@Test(expected=com.capgemini.exceptions.InsufficientInitialAmountException.class)
	public void whenTheAmountIsLessThan500SystemShouldThrowException() throws InsufficientInitialAmountException
	{
		accountService.createAccount(101, 400);
	}
	
	@Test
	public void whenTheValidInfoIsPassedAccountShouldBeCreatedSuccessfully() throws InsufficientInitialAmountException
	{
		Account account =new Account();
		account.setAccountNumber(101);
		account.setAmount(5000);
		when(accountRepository.save(account)).thenReturn(true);
		assertEquals(account, accountService.createAccount(101, 5000));
	}
	
	@Test(expected=com.capgemini.exceptions.InvalidAccountNumberException.class)
	public void whenInvalidAccNumInDisplay() throws InvalidAccountNumberException
	{
		Account account1= new Account();
		account1.setAccountNumber(120);
		account1.setAmount(1000);
		when(accountRepository.searchAccount(account1.getAccountNumber())).thenReturn(account1);
		assertEquals(account1,accountService.Display(121));
	}

	@Test
	public void whenTheValidInputIsPassedDiaplay() throws InvalidAccountNumberException 
	{
		Account account1 =new Account();
		account1.setAccountNumber(1001);
		account1.setAmount(2000);
		when(accountRepository.searchAccount(account1.getAccountNumber())).thenReturn(account1);
		assertEquals(account1, accountService.Display(1001));
	}
	
	@Test
	public void whenTheValidInputIsPassedDeposit() throws InvalidAccountNumberException 
	{
		Account account1 =new Account();
		account1.setAccountNumber(1002);
		account1.setAmount(2000);
		when(accountRepository.searchAccount(account1.getAccountNumber())).thenReturn(account1);
		accountService.Deposit(1002,1000);
		assertEquals(3000,account1.getAmount());
	}
	
	@Test(expected=com.capgemini.exceptions.InvalidAccountNumberException.class)
	public void whenTheInValidInputIsPassedDeposit() throws InvalidAccountNumberException 
	{
		Account account2 =new Account();
		account2.setAccountNumber(1001);
		account2.setAmount(2000);
		when(accountRepository.searchAccount(account2.getAccountNumber())).thenReturn(account2);
		accountService.Deposit(1011, 2000);
		assertEquals(4000,account2.getAmount());
	}
	
	@Test(expected=com.capgemini.exceptions.InsufficientBalanceException.class)
	public void whenInsuficientBalanceCalledInWithdraw() throws InvalidAccountNumberException, InsufficientBalanceException
	{
		Account account2 =new Account();
		account2.setAccountNumber(1001);
		account2.setAmount(2000);
		when(accountRepository.searchAccount(account2.getAccountNumber())).thenReturn(account2);
		accountService.Withdraw(1001, 2000);
		assertEquals(0,account2);
	}
    
	@Test
	public void whenSuficientBalanceCalledInWithdraw() throws InvalidAccountNumberException, InsufficientBalanceException
	{
		Account account2 =new Account();
		account2.setAccountNumber(1001);
		account2.setAmount(2000);
		when(accountRepository.searchAccount(account2.getAccountNumber())).thenReturn(account2);
		accountService.Withdraw(1001, 200);
		assertEquals(1800,account2.getAmount());
	}
	
	@Test(expected=com.capgemini.exceptions.InvalidAccountNumberException.class)
	public void whenInvalidAccNoCalledInWithdraw() throws InvalidAccountNumberException, InsufficientBalanceException
	{
		Account account2 =new Account();
		account2.setAccountNumber(1001);
		account2.setAmount(2000);
		when(accountRepository.searchAccount(account2.getAccountNumber())).thenReturn(account2);
		accountService.Withdraw(1002, 200);
		assertEquals(1800, account2.getAmount());
	}
}
