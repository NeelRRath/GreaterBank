package com.greater.au.transaction.manager.file.impl;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.greater.au.transaction.exception.FileProcessingException;
import com.greater.au.transaction.manager.file.FileManager;
import com.greater.au.transaction.vo.TransactionFileVO;
import com.greater.au.transaction.vo.TransactionVO;
import com.greater.au.util.FileUtils;

/**
 * Read the customer transaction files at given Path
 *
 * @author Neel
 */
public class FileMangerImpl implements FileManager {
	private static final Logger LOGGER = LoggerFactory.getLogger(FileMangerImpl.class);

	/**
	 * Read the customer transaction file at given Path and process it into a
	 * TransactionFileVO object.
	 *
	 * @param Path - the path to the customer transaction file
	 * @return a TransactionFileVO instance representing the contents of the customer transaction file
	 */
	@Override
	public TransactionFileVO readFile(Path path) throws FileProcessingException {
		LOGGER.info("Before processing transaction File");
		TransactionFileVO transactionFile = new TransactionFileVO(path);
		Long start = System.currentTimeMillis();
		List<String> lines = FileUtils.readLinesFromFile(path);
		// Skip the header line
		if (lines.size() > 0) {
			lines.remove(0);
		}
		for (String line : lines) {
			transactionFile.addTransaction(readLine(line));
		}
		Long end = System.currentTimeMillis();
		LOGGER.info("Processed {} transactions in {}ms", lines.size(), end - start);
		return transactionFile;
	}

	/**
	 * Process a single line from a customer transaction file and return a
	 * TransactionVO instance representing the transaction. The line
	 * should be of the following format:
	 *
	 * account number(123443219), balance(100.00)
	 *
	 * where the first comma-delimited token is a customer account number, and
	 * the second is a transaction amount.
	 *
	 * If the customer account number is non-numeric or the format is violated
	 * in any other way, the transaction will be skipped (null returned).
	 *
	 * @param line the transaction line to process
	 * @return a TransactionVO object, or null if the transaction is to be  skipped
	 */
	private TransactionVO readLine(String line) {
		String[] parts = line.split(",");
		if (parts.length != 2) {
			return null;
		}

		String customerAccountNumber = parts[0].trim();
		if (!StringUtils.isNumeric(customerAccountNumber)) {
			return null;
		}

		String transactionAmount = parts[1].trim();
		if (!NumberUtils.isCreatable(transactionAmount)) {
			return null;
		}
		return new TransactionVO(Integer.parseInt(customerAccountNumber), Double.parseDouble(transactionAmount));
	}
	/**
	   * Read all pending transaction files into  TransactionFileVO instances.
	   *
	   * This method assumes that @param path contains only customer
	   * transaction files and no other types of file.
	   */
	@Override
	public List<TransactionFileVO> getAllPendingFiles(String pendingDirectory) throws FileProcessingException {
		LOGGER.info("Loading all pending customer transactions files");
	    List<TransactionFileVO> transactionFiles = new ArrayList<>();
	    for (Path path : FileUtils.listFiles(Paths.get(pendingDirectory))) {
	      transactionFiles.add(readFile(path));
	    }
	    return transactionFiles;
	}
}
