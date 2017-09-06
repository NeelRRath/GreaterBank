package com.greater.au.transaction.manager.file;

import java.nio.file.Path;
import java.util.List;

import com.greater.au.transaction.exception.FileProcessingException;
import com.greater.au.transaction.vo.TransactionFileVO;

public interface FileManager {
	public TransactionFileVO readFile(Path path) throws FileProcessingException;

	public List<TransactionFileVO> getAllPendingFiles(String path) throws FileProcessingException;
}
