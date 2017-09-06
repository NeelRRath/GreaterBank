package com.greater.au.util;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.greater.au.transaction.exception.FileProcessingException;

/**
 * Our own File Utility class. It has static methods for reading or writing
 * files on the file system.
 *
 * @author Neel
 */
public class FileUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(FileUtils.class);

	/**
	 * this method will list all files in the given directory path, excluding
	 * sub-directories. throws FileProcessingException
	 * 
	 * @param path
	 *            the directory to be listed
	 * @return a list of discovered files
	 * 
	 */
	public static List<Path> listFiles(Path path) throws FileProcessingException {
		LOGGER.info("Loading files from directory at {}", path);
		try (Stream<Path> paths = Files.walk(path)) {
			return paths.filter(Files::isRegularFile).collect(toList());
		} catch (IOException e) {
			throw new FileProcessingException("Error reading files from " + path, e);
		}
	}

	/**
	 * Read the file at the given path and return a list of strings representing
	 * the lines of the file.
	 *
	 * @param path - the file to be read
	 * @return the file contents, one string per line
	 */
	public static List<String> readLinesFromFile(Path path) throws FileProcessingException {
		LOGGER.info("Loading lines from file at {}", path);

		try (Stream<String> lines = Files.lines(path)) {
			return lines.collect(toList());
		} catch (IOException e) {
			throw new FileProcessingException("Error reading file " + path, e);
		}
		/*
		try {
            RandomAccessFile aFile = new RandomAccessFile("c:/temp/my-large-file.csv", "r");
            FileChannel inChannel = aFile.getChannel();
            MappedByteBuffer buffer = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
            
            for (int i = 0; i < buffer.limit(); i++) {
				byte read = buffer.get();
				//System.out.print((char)read);
				
			}
            aFile.close();
            
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
		
		*/
		
		
	}

	/**
	 * Write the given content as a file to the given path.
	 * @param path - the path at which to write the file
	 * @param content - the file content to write
	 */
	public static void writeFile(Path path, String content) throws FileProcessingException {
		LOGGER.info("Writing file at {}", path);
		try {
			Files.write(path, content.getBytes());
		} catch (IOException e) {
			throw new FileProcessingException("Error writing file " + path, e);
		}
	}

	/**
	 * Move a file from one position on the file system to another.
	 *
	 * @param from the source file path
	 * @param to the target file path
	 */
	public static void moveFile(Path from, Path to) throws FileProcessingException{
		try {
			Files.move(from, to, REPLACE_EXISTING);
		} catch (IOException e) {
			throw new FileProcessingException("Error moving file from " + from + " to " + to, e);
		}
	}
}
