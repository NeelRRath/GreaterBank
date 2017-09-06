package com.greater.au.transaction.manager.customer.impl;

import java.util.HashMap;
import java.util.Map;

import com.greater.au.transaction.exception.AccountProcessException;
import com.greater.au.transaction.manager.customer.CustomerAccountManager;
import com.greater.au.transaction.vo.CustomerAccountVO;
import com.greater.au.transaction.vo.TransactionFileVO;
import com.greater.au.transaction.vo.TransactionVO;

/**
 * Customer accounts are cached in a HashMap for faster lookup. It improves
 * performance. Get operations are done O(1) If an account is not present in the
 * map, a new instance is added with the initial debit/credit amount. We can
 * have a cached service like JCS Cache.But For the time being it is out of
 * scope.
 * 
 * @author Neel
 */
public class CustomerAccountManagerImpl implements CustomerAccountManager {
	private Map<Integer, CustomerAccountVO> accountMap = new HashMap<>();

	public Map<Integer, CustomerAccountVO> getAccountMap() {
		return accountMap;
	}

	public void setAccountMap(Map<Integer, CustomerAccountVO> accounts) {
		this.accountMap = accounts;
	}

	@Override
	public Map<Integer, CustomerAccountVO> mapAccountNumberFromFile(TransactionFileVO fileVO) throws AccountProcessException {
		if (fileVO == null || fileVO.getTransactions() == null || fileVO.getTransactions().size() == 0) {
			throw new AccountProcessException("No Customer Account details found for processing.");
		}

		for (TransactionVO transaction : fileVO.getTransactions()) {
			if (null != accountMap.get(transaction.getCustomerAccountNumber())) {
				accountMap.get(transaction.getCustomerAccountNumber()).apply(transaction);
			} else {
				CustomerAccountVO account = new CustomerAccountVO(transaction.getCustomerAccountNumber(), 0d);
				accountMap.put(account.getAccountNumber(), account);
			}
		}
		return accountMap;
	}

	@Override
	public Double getAccountBalance(Integer customerAccountNumber) throws AccountProcessException {
		if (null != accountMap.get(customerAccountNumber)) {
			return accountMap.get(customerAccountNumber).getBalance();

		} else {
			throw new AccountProcessException("Customer account " + customerAccountNumber + " does not exist!");
		}

	}

}
