package com.greater.au.transaction.vo;

import static org.junit.Assert.assertEquals;

import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;
/**
 * Unit test for customer TransactionFileVO.
 * 
 * @author Neel
 */

public class TransactionFileVOTest {

	private TransactionFileVO file;

	@Before
	public void before() {
		file = new TransactionFileVO(Paths.get("mytest.csv"));
	}

	@Test
	public void checkIfNullTransactionSkipped() {
		file.addTransaction(null);
		assertEquals(1, file.getNumberOfSkippedTransactions().longValue());
		assertEquals(0, file.getTotalNumberOfAccounts().longValue());
	}

	@Test
	public void multipleTransactionsPerCustomer() {
		file.addTransaction(new TransactionVO(1, 100.0));
		file.addTransaction(new TransactionVO(1, 100.0));
		file.addTransaction(new TransactionVO(1, -50.0));

		assertEquals(1, file.getTotalNumberOfAccounts().longValue());
		assertEquals(200.0, file.getTotalCreditAmount(), 0);
		assertEquals(50.0, file.getTotalDebitAmount(), 0);
	}

	@Test
	public void totalCreditAmount() {
		file.addTransaction(new TransactionVO(1, 100.0));
		file.addTransaction(new TransactionVO(2, 50.0));

		assertEquals(2l, file.getTotalNumberOfAccounts().longValue());
		assertEquals(150.0, file.getTotalCreditAmount(), 0);
		assertEquals(0, file.getTotalDebitAmount(), 0);
	}

	@Test
	public void totalDebitAmount() {
		file.addTransaction(new TransactionVO(1, -100.0));
		file.addTransaction(new TransactionVO(2, -50.0));

		assertEquals(2, file.getTotalNumberOfAccounts().longValue());
		assertEquals(0, file.getTotalCreditAmount(), 0);
		assertEquals(150.0, file.getTotalDebitAmount(), 0);
	}
}
