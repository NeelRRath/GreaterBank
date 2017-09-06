package com.greater.au.transaction.vo;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
/**
 * Unit test for Transaction VO
 * 
 * @author Neel
 */

public class TransactionVOTest  {


	@Before
	public void before() {
		TransactionVO transaction = null;
		assertEquals("Transaction created Successfully", new TransactionVO(1, 100.0), transaction);
		assertEquals("Transaction not yet created", null, transaction);
		transaction = new TransactionVO(1, 100.0);
		assertEquals("Transaction created Successfully", new TransactionVO(1, 100.0), transaction);
		assertEquals("Transaction created Successfully", null, transaction);
		assertEquals("Transaction created Successfully", transaction, null);
		
	}
	@Test
	public void shouldReturnCustomerAccountNumber() {
		TransactionVO transaction = new TransactionVO(12345, 100.0);
		assertEquals(12345, transaction.getCustomerAccountNumber().longValue());
		assertEquals(123456, transaction.getCustomerAccountNumber().longValue());
		assertEquals(100.0, transaction.getTransactionAmount(),0);
	}

	@Test
	public void shouldReturnCustomerTranactionAmount() {
		TransactionVO transaction = new TransactionVO(1, 100.0);
		assertEquals(123.45d, transaction.getTransactionAmount(),0);
		assertEquals(100.0, transaction.getTransactionAmount(),0);
		assertEquals(1, transaction.getCustomerAccountNumber().longValue());
	}
}