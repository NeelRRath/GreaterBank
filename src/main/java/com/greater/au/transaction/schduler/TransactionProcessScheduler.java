package com.greater.au.transaction.schduler;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.stereotype.Component;

import com.greater.au.transaction.service.TransactionService;

/**
 * This TransactionProcessScheduler component is responsible for processing the
 * customer transaction at predefined time intervals.
 *
 * The scheduler makes the following assumptions:
 *
 * - The time zone is always UTC - Transaction files take 1 minute or less to be
 * received
 *
 * Based on the above, the scheduler runs at 06:02am and 21:02pm each day.
 *
 * @author Neel
 * */
@Component
@ComponentScan("com.greater.au")
public class TransactionProcessScheduler {
	private static final Logger log = LoggerFactory.getLogger(TransactionProcessScheduler.class);
	private TransactionService transactionService;

	public TransactionService getTransactionService() {
		return transactionService;
	}

	public void setTransactionService(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	public static Logger getLog() {
		return log;
	}

	@Schedules({ @Scheduled(cron = "0 48 23 * * ?"), // 06:03am
		@Scheduled(cron = "0 25 0 * * ?"), // 21:03pm
	})
	public void executeTransactionProcessor() {
		log.info("inside TransactionProcessScheduler, Starting transaction processing, time is {}", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mmX").withZone(ZoneOffset.UTC).format(Instant.now()));
		try {
			getTransactionService().processTransaction();
		} catch (Exception e) {
			log.error("Error processing transactions: {}: {}", e.getMessage(), e.getCause());
		}
	}

}
