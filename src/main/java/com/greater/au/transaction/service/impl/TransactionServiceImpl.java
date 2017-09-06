package com.greater.au.transaction.service.impl;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.greater.au.transaction.exception.AccountProcessException;
import com.greater.au.transaction.exception.FileProcessingException;
import com.greater.au.transaction.manager.customer.CustomerAccountManager;
import com.greater.au.transaction.manager.file.FileManager;
import com.greater.au.transaction.manager.transaction.TransactionManager;
import com.greater.au.transaction.service.TransactionService;
import com.greater.au.transaction.vo.TransactionFileVO;
import com.greater.au.util.FileUtils;

/**
 /**
 * This component is responsible for running transaction processing work-flow. 
 * The processing happens in four distinct stages:
 *
 * 1. Reading all pending transaction files
 * 2. Applying new transactions to customer accounts
 * 3. Writing a report file for each processed file
 * 4. Archiving each processed file
 * 
 * @author Neel
 */
public class TransactionServiceImpl implements TransactionService {
	private static final Logger LOGGER = LoggerFactory.getLogger(TransactionServiceImpl.class);
	private TransactionManager transactionManager;
	private CustomerAccountManager customerAccountManager;
	private FileManager fileManager;

	private String pendingDirectory;
	private String reportsDirectory;
	private String archiveDirectory;

	public String getPendingDirectory() {
		return pendingDirectory;
	}

	public void setPendingDirectory(String pendingDirectory) {
		this.pendingDirectory = pendingDirectory;
	}

	public String getReportsDirectory() {
		return reportsDirectory;
	}

	public void setReportsDirectory(String reportsDirectory) {
		this.reportsDirectory = reportsDirectory;
	}

	public String getArchiveDirectory() {
		return archiveDirectory;
	}

	public void setArchiveDirectory(String archiveDirectory) {
		this.archiveDirectory = archiveDirectory;
	}

	public FileManager getFileManager() {
		return fileManager;
	}

	public void setFileManager(FileManager fileManager) {
		this.fileManager = fileManager;
	}

	public TransactionManager getTransactionManager() {
		return transactionManager;
	}

	public void setTransactionManager(TransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	public CustomerAccountManager getCustomerAccountManager() {
		return customerAccountManager;
	}

	public void setCustomerAccountManager(CustomerAccountManager customerAccountManager) {
		this.customerAccountManager = customerAccountManager;
	}

	@Override
	public void processTransaction() throws AccountProcessException {
		// Read all pending transaction files
		List<TransactionFileVO> transactionFiles = null;
		try {
			transactionFiles = getFileManager().getAllPendingFiles(getPendingDirectory());
		} catch (FileProcessingException e) {
			throw new AccountProcessException(e.getMessage());
		}
		if (null != transactionFiles) {
			for (TransactionFileVO file : transactionFiles) {
				// Apply transactions from to customer accounts
				getCustomerAccountManager().mapAccountNumberFromFile(file);

				// Write the report, then archive the file
				try {
					writeReport(file);
				} catch (FileProcessingException e) {
					LOGGER.info("Error while writing the customer transaction file");
				}
				try {
					archiveFile(file);
				} catch (FileProcessingException e) {
					LOGGER.info("Error while archiving the customer transaction file");
				}

			}

		}
	}

	/**
	 * Write a report file summarising the given {@link TransactionFile}.
	 *
	 * @param file
	 *            the {@link TransactionFile} to be written
	 */
	private void writeReport(TransactionFileVO file) throws FileProcessingException {
		String filename = file.getPath().getFileName().toString();

		// It is assumed that the files are well-named in advance
		String datetime = filename.replace("finance_customer_transactions-", "").replace(".csv", "");
		Path path = Paths.get(getReportsDirectory(), "finance_customer_transactions_report-" + datetime + ".txt");

		LOGGER.info("Writing report to {}", path);
		FileUtils.writeFile(path, file.generateReport());
	}

	/**
	 * Archive the given {@link TransactionFile}. An archived file represents
	 * one that has already been processed, and will therefore not be processed
	 * more than once.
	 *
	 * @param file
	 *            the {@link TransactionFile} to be archived
	 */
	private void archiveFile(TransactionFileVO file) throws FileProcessingException {
		LOGGER.info("Archiving transaction file {}", file.getPath().toString());
		FileUtils.moveFile(file.getPath(), Paths.get(getArchiveDirectory(), file.getPath().getFileName().toString()));
	}

}
