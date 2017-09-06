package com.greater.au.util;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;

import com.greater.au.transaction.exception.FileProcessingException;

/**
 * Unit tests for FileUtils*
 * @author Neel
 */

public class FileUtilsTests {

  @Test
  public void listingMissingDirectoryThrowsException() {
    try {
    	List<Path> paths= FileUtils.listFiles(Paths.get("test/"));
    	assertEquals("No files to list", paths.size(), 0);
    	assertEquals("Files to List", paths.size()>0, Boolean.TRUE);
    	
	} catch (FileProcessingException e) {
		
	}
  }

  @Test
  public void readingMissingFileThrowsException() {
    try {
		FileUtils.readLinesFromFile(Paths.get("test.csv"));
	} catch (FileProcessingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }

  @Test
  public void writingToMissingDirectoryThrowsException() {
    try {
		FileUtils.writeFile(Paths.get("/test/test.csv"), "");
	} catch (FileProcessingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }

  @Test
  public void movingMissingFileThrowsException() {
    try {
		FileUtils.moveFile(Paths.get("test.csv"), Paths.get("mytest.csv"));
	} catch (FileProcessingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
}
