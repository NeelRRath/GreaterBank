package com.greater.au.transaction.manager.customer;

import java.util.Map;

import com.greater.au.transaction.exception.AccountProcessException;
import com.greater.au.transaction.vo.CustomerAccountVO;
import com.greater.au.transaction.vo.TransactionFileVO;

public interface CustomerAccountManager {
	public Map<Integer, CustomerAccountVO> mapAccountNumberFromFile(TransactionFileVO fileVO) throws AccountProcessException;

	public Double getAccountBalance(Integer customerAccountNumber) throws AccountProcessException;

}
