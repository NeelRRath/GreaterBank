package com.greater.au.transaction.service.impl;

import java.util.Map;

import com.greater.au.transaction.exception.AccountProcessException;
import com.greater.au.transaction.manager.customer.CustomerAccountManager;
import com.greater.au.transaction.service.CustomerAccountService;
import com.greater.au.transaction.vo.CustomerAccountVO;
import com.greater.au.transaction.vo.TransactionFileVO;

/**
 * Customer accounts Service
 * 
 * @author Neel
 */
public class CustomerAccountServiceImpl implements CustomerAccountService {

	private CustomerAccountManager customerAccountManager;

	public CustomerAccountManager getCustomerAccountManager() {
		return customerAccountManager;
	}

	public void setCustomerAccountManager(CustomerAccountManager customerAccountManager) {
		this.customerAccountManager = customerAccountManager;
	}

	/**
	 * Applying a single transaction per customer account. *
	 * 
	 * @param TransactionFileVO
	 */
	@Override
	public Map<Integer, CustomerAccountVO> mapAccountNumberFromFile(TransactionFileVO fileVO) throws AccountProcessException {

		return getCustomerAccountManager().mapAccountNumberFromFile(fileVO);

	}

	@Override
	public Double getAccountBalance(Integer customerAccountNumber) throws AccountProcessException {

		return getCustomerAccountManager().getAccountBalance(customerAccountNumber);

	}

}
