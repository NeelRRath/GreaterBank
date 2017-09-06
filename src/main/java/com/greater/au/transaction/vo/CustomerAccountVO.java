package com.greater.au.transaction.vo;

import java.io.Serializable;

/**
 * This mutable model class is used to keep track of credits and debits for a
 * single customer account.
 *
 * @author Neel
 */
public class CustomerAccountVO implements Serializable {

	private static final long serialVersionUID = 2682563400454103196L;
	private Integer accountNumber;
	private Double balance = 0.0;

	public CustomerAccountVO(Integer accountNumber, Double balance) {
		super();
		this.accountNumber = accountNumber;
		this.balance = balance;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getAccountNumber() {
		return accountNumber;
	}

	public Double getBalance() {
		return balance;
	}

	/**
	 * This method manages Userstotal balance 
	 * against all its credit or debit transactions
	 * 
	 * Negative transaction amounts represent a debit against a customer
	 * account, and the customer's account balance is increased.
	 *
	 * Positive transaction amounts represent a credit against a customer
	 * account, and the customer's balance is decreased.
	 * 
	 * @author Neel
	 * @param TransactionVO
	 */
	public void apply(TransactionVO transaction) {
		if (transaction.getTransactionAmount() < 0) {
			balance += Math.abs(transaction.getTransactionAmount());
		} else {
			balance -= transaction.getTransactionAmount();
		}
	}
}
