package com.greater.au.transaction.exception;

/***
 *
 * @author Neel
 *
 */
public class FileProcessingException extends Exception {

	private static final long serialVersionUID = 1L;
	private String errorCode;

	/**
	 * Creates an instance of FileProcessingException with specified errorCode and message.
	 * 
	 * @param errorCode
	 * @param message
	 */
	public FileProcessingException(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	/**
	 * Creates an instance of FileProcessingException with specified errorCode and
	 * Exception reference.
	 * 
	 * @param errorCode
	 * @param e
	 */
	public FileProcessingException(String errorCode, Exception e) {
		super(e);
		this.errorCode = errorCode;
	}

	// Getter and setter methods of errorCode.
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public FileProcessingException() {
		super();
	}

	public FileProcessingException(String message) {
		super(message);
	}

	public FileProcessingException(Throwable t) {
		super(t);
	}

	public FileProcessingException(String message, Throwable t) {
		super(message, t);
	}

	public FileProcessingException(String errorCode, String message, Exception e) {
		super(message, e);
		this.errorCode = errorCode;
	}
}
