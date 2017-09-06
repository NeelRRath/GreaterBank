package com.greater.au.transaction.exception;


/***
 *
 * @author Neel
 *
 */
public class AccountProcessException extends Exception {

	private static final long serialVersionUID = 1L;
	private String errorCode;

	/**
	 * Creates an instance of AccountProcessException with specified errorCode and message.
	 * 
	 * @param errorCode
	 * @param message
	 */
	public AccountProcessException(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	/**
	 * Creates an instance of AccountProcessException with specified errorCode and
	 * Exception reference.
	 * 
	 * @param errorCode
	 * @param e
	 */
	public AccountProcessException(String errorCode, Exception e) {
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

	public AccountProcessException() {
		super();
	}

	public AccountProcessException(String message) {
		super(message);
	}

	public AccountProcessException(Throwable t) {
		super(t);
	}

	public AccountProcessException(String message, Throwable t) {
		super(message, t);
	}

	public AccountProcessException(String errorCode, String message, Exception e) {
		super(message, e);
		this.errorCode = errorCode;
	}
}
