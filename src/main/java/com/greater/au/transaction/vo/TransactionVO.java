package com.greater.au.transaction.vo;

import java.io.Serializable;

/**
 * This class represents a single, immutable transaction for a single customer
 * account.
 * 
 * @author Neel
 */

public class TransactionVO implements Serializable{
	
	private static final long serialVersionUID = 2682563400454103196L;
	private Integer customerAccountNumber;
	private Double transactionAmount;

	public Integer getCustomerAccountNumber() {
		return customerAccountNumber;
	}

	public Double getTransactionAmount() {
		return transactionAmount;
	}

	public TransactionVO(Integer customerAccountNumber, Double transactionAmount) {
		super();
		this.customerAccountNumber = customerAccountNumber;
		this.transactionAmount = transactionAmount;
	}

}