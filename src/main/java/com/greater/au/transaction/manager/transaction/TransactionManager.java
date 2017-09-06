package com.greater.au.transaction.manager.transaction;

import com.greater.au.transaction.exception.AccountProcessException;

public interface TransactionManager {
	void processTransaction()throws AccountProcessException;
	
}
