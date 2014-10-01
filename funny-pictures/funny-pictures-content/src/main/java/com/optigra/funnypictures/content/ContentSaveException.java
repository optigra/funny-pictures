package com.optigra.funnypictures.content;

/**
 * This is custom exception, which describes situation of failed save operation.
 * 
 * @author oyats
 * 
 */
public class ContentSaveException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor for ContentSaveException.
	 */
	public ContentSaveException() {
		super();
	}

	/**
	 * Constructor for ContentSaveException using parameters message, cause,
	 * enableSuppression, writableStackTrace.
	 * 
	 * @param message
	 *            Message of exception.
	 * @param cause
	 *            Cause of exception.
	 * @param enableSuppression
	 *            Flag to enable Suppression.
	 * @param writableStackTrace
	 *            Flag to set Stack Trace writable.
	 */
	public ContentSaveException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Constructor for ContentSaveException using parameters message, cause.
	 * 
	 * @param message
	 *            Message of exception.
	 * @param cause
	 *            Cause of exception.
	 */
	public ContentSaveException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor for ContentSaveException using parameters message.
	 * 
	 * @param message
	 *            Message of exception.
	 */
	public ContentSaveException(final String message) {
		super(message);
	}

	/**
	 * Constructor for ContentSaveException using parameters cause.
	 * 
	 * @param cause
	 *            Cause of exception.
	 */
	public ContentSaveException(final Throwable cause) {
		super(cause);
	}
}
