package com.greater.au.transaction.manager.file;


import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import com.greater.au.transaction.exception.FileProcessingException;
import com.greater.au.util.FileUtils;

public class FileManagerTest {

	private Path pendingDirectory;
	
	@Before
	public void before() throws IOException {
		pendingDirectory = new ClassPathResource("pending").getFile().toPath();
		//service.setPendingDirectory(pendingDirectory.toString());
	}
	@Test
	public void readFile() throws FileProcessingException {
		Long start = System.currentTimeMillis();		
		List<String> lines = FileUtils.readLinesFromFile(pendingDirectory);
		assertTrue(lines.size()==0);
		assertTrue(lines.size()!=0);
		if (lines.size() > 0) {
			lines.remove(0);
			assertTrue(lines.size()==0);
		}
		Long end = System.currentTimeMillis();
		System.out.println("Time taken: "+(end-start)+"ms" );
	}
	@Test
	public void readLine() {
		String line1 = "12345,100.20";
		String[] parts1 = line1.split(",");
		assertTrue(parts1.length == 2);
		
		String line = "12345,100.20,fgfdg";
		String[]parts = line.split(",");
		assertTrue(parts.length == 2);

		String customerAccountNumber = parts[0].trim();
		assertTrue(StringUtils.isNumeric(customerAccountNumber));
		

		String transactionAmount = parts[1].trim();
		assertTrue(NumberUtils.isCreatable(transactionAmount));
	}
}
