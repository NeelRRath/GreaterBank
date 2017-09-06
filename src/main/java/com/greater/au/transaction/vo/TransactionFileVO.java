package com.greater.au.transaction.vo;

import java.io.Serializable;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * This value object blueprint represents a single customer transaction file.
 * 
 * @author Neel
 */
public class TransactionFileVO implements Serializable {

	private static final long serialVersionUID = 2682563400454103196L;

	private Path path;
	private List<TransactionVO> transactions = new ArrayList<>();
	private Integer numberOfSkippedTransactions = 0;

	public TransactionFileVO(Path path) {
		super();
		this.path = path;
	}

	public Path getPath() {
		return path;
	}

	public void setPath(Path path) {
		this.path = path;
	}

	public Integer getNumberOfSkippedTransactions() {
		return numberOfSkippedTransactions;
	}

	public void setNumberOfSkippedTransactions(Integer numberOfSkippedTransactions) {
		this.numberOfSkippedTransactions = numberOfSkippedTransactions;
	}

	public void setTransactions(List<TransactionVO> transactions) {
		this.transactions = transactions;
	}

	public List<TransactionVO> getTransactions() {
		return transactions;
	}

	/**
	 * Add a single {@link Transaction} to this file.
	 * 
	 * If the transaction is null, it will be skipped and the total skipped
	 * transaction count will be incremented.
	 * 
	 * @param transactionVO
	 *            the transaction to add for processing
	 */
	public void addTransaction(TransactionVO transaction) {
		if (null == transaction) {
			numberOfSkippedTransactions++;
			return;
		}
		transactions.add(transaction);
	}

	/**
	 * @return the total number of unique customer account numbers in the
	 *         Transaction file
	 */
	public Long getTotalNumberOfAccounts() {
		return transactions.stream()
		// .map(TransactionVO::getCustomerAccountNumber)
			.distinct().count();
	}

	/**
	 * @return the total credit amount for all accounts in the Transaction file
	 */
	public Double getTotalCreditAmount() {
		return transactions.stream().filter(t -> t.getTransactionAmount() > 0).mapToDouble(TransactionVO::getTransactionAmount).sum();

	}

	/**
	 * @return the total Debit amount for all accounts in the Transaction file
	 */
	public Double getTotalDebitAmount() {
		return transactions.stream().filter(t -> t.getTransactionAmount() < 0).mapToDouble(t -> Math.abs(t.getTransactionAmount())).sum();
	}

	/**
	 * Generate a report from the processed Transaction file: The report prints
	 * - The name of the file processed - The number of accounts processed - The
	 * total credit amount - The total debit amount - The number of lines that
	 * were skipped
	 * 
	 * @return the report as a string
	 */
	public String generateReport() {
		String report = "File Processed: " + getPath().getFileName().toString() 
			+ "\r\nTotal Accounts: " + String.format("%,d", getTotalNumberOfAccounts()) 
			+ "\r\nTotal Credits : " + String.format("$%,.2f", getTotalCreditAmount()) 
			+ "\r\nTotal Debits  : " + String.format("$%,.2f", getTotalDebitAmount()) 
			+ "\r\nSkipped Transactions: " + getNumberOfSkippedTransactions();
		return report;
	}
}
