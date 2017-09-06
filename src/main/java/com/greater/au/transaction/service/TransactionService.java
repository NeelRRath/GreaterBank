package com.greater.au.transaction.service;

import com.greater.au.transaction.exception.AccountProcessException;

public interface TransactionService {
	void processTransaction()throws AccountProcessException;
}
