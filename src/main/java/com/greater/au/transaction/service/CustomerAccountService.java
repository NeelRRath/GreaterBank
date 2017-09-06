package com.greater.au.transaction.service;

import java.util.Map;

import com.greater.au.transaction.exception.AccountProcessException;
import com.greater.au.transaction.vo.CustomerAccountVO;
import com.greater.au.transaction.vo.TransactionFileVO;

public interface CustomerAccountService {
	public Map<Integer, CustomerAccountVO> mapAccountNumberFromFile(TransactionFileVO fileVO) throws AccountProcessException;

	public Double getAccountBalance(Integer customerAccountNumber) throws AccountProcessException;
}
