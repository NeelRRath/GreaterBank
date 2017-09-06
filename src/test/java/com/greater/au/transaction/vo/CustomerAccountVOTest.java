package com.greater.au.transaction.vo;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;


/**
 * This mutable model class is used to keep track of credits and debits for a
 * single customer account.
 * unit test class
 * @author Neel
 */
public class CustomerAccountVOTest   {

	private CustomerAccountVO account ;
	@Before
	public void before() {
		assertEquals("Account created Successfully", new CustomerAccountVO(1, 100.0), account);
		assertEquals("Account not yet created", null, account);
		account = new CustomerAccountVO(1234, 100.0);
		assertEquals("Transaction created Successfully", new CustomerAccountVO(1, 100.0), account);
		assertEquals("Transaction created Successfully", null, account);
		assertEquals("Transaction created Successfully", account, null);
		
	}
	  @Test
	  public void debitIncreasesBalance() {
		 account.apply(new TransactionVO(1234, -100.0));
		 assertEquals(100.0, account.getBalance(), 0);
		 assertEquals(0.0, account.getBalance(), 0);
	  }

	  @Test
	  public void creditDecreasesBalance() {
		  account.apply(new TransactionVO(1, 100.0));
		  assertEquals(100.0, account.getBalance(), 0);
		  assertEquals(-100.0, account.getBalance(), 0);
	  }

	
}
