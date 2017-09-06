package com.greater.au.transaction.service.transaction;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.springframework.core.io.ClassPathResource;

import com.greater.au.transaction.exception.FileProcessingException;
import com.greater.au.transaction.manager.customer.impl.CustomerAccountManagerImpl;
import com.greater.au.transaction.manager.file.impl.FileMangerImpl;
import com.greater.au.transaction.manager.transaction.impl.TransactionManagerImpl;
import com.greater.au.transaction.service.impl.TransactionServiceImpl;
import com.greater.au.util.FileUtils;

public class TransactionServiceTest {

	private TransactionServiceImpl service = new TransactionServiceImpl();

	private Path pendingDirectory;

	@Rule
	public TemporaryFolder reportsDir = new TemporaryFolder(Paths.get("/tmp").toFile());
	@Rule
	public TemporaryFolder archiveDir = new TemporaryFolder(Paths.get("/tmp").toFile());

	@Before
	public void before() throws IOException {
		service.setTransactionManager(new TransactionManagerImpl());
		service.setCustomerAccountManager(new CustomerAccountManagerImpl());
		service.setFileManager(new FileMangerImpl());
		pendingDirectory = new ClassPathResource("pending").getFile().toPath();
		service.setPendingDirectory(pendingDirectory.toString());
		service.setReportsDirectory(reportsDir.getRoot().toString());
		service.setArchiveDirectory(archiveDir.getRoot().toString());
	}

	@Test
	public void pendingFilesAreProcessedCorrectly() {
		try {
			List<Path> pending = FileUtils.listFiles(pendingDirectory);

			service.processTransaction();

			// There should be the same number of reports as pending files
			List<Path> reports = FileUtils.listFiles(reportsDir.getRoot().toPath());
			assertEquals(new Long(pending.size()), new Long(reports.size()));
			// assertEquals(12345,transaction.getCustomerAccountNumber().longValue());

			// Ensure filenames are correct
			for (Path path : reports) {
				assertTrue(path.getFileName().toString().matches("finance_customer_transactions_report-.*\\.txt"));
			}

			// There should be the same number of archived filed as the original
			// amount of pending files
			List<Path> archive = FileUtils.listFiles(archiveDir.getRoot().toPath());
			assertEquals(pending.size(), archive.size());

			// There should be no more pending files
			assertEquals(0, FileUtils.listFiles(pendingDirectory).size());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@After
	// Moveing the archived files back to the pending directory
	public void after() {
		try {
			for (Path path : FileUtils.listFiles(archiveDir.getRoot().toPath())) {
				FileUtils.moveFile(path, Paths.get(pendingDirectory.toString(), path.getFileName().toString()));
			}
		} catch (FileProcessingException e) {
			fail(e.getMessage());
		}
	}
}
