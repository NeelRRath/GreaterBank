package com.greater.au.transaction.schduler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.greater.au.AppMain;

/**
 * Integration tests for Transaction Process Scheduler within the Spring context
 *
 * @author Neel
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppMain.class, properties = { "processing.baseDirectory=/", "processing.pendingDirectory=/tmp" })
public class TransactionProcessSchdulerTest {
	@Autowired
	private TransactionProcessScheduler scheduler;

	@Test
	public void schedulerRuns() {
		// scheduler = new TransactionProcessScheduler();
		// scheduler.setTransactionService(new TransactionServiceImpl());
		scheduler.executeTransactionProcessor();
	}
}
