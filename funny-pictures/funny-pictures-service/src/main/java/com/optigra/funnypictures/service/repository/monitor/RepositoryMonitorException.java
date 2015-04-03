package com.optigra.funnypictures.service.repository.monitor;

/**
 * An exception thrown in case of a repository monitor error.
 * @author odisseus
 *
 */
public class RepositoryMonitorException extends RuntimeException {

	private static final long serialVersionUID = -3729443044648775818L;
	
	/**
	 * Creates a new instance of FileSystemMonitorException.
	 * @param message the detail message
	 */
	public RepositoryMonitorException(final String message) {
		super(message);
	}

	/**
	 * Creates a new instance of FileSystemMonitorException.
	 * @param cause the cause. (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public RepositoryMonitorException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Creates a new instance of FileSystemMonitorException.
	 * @param message the detail message
	 * @param cause the cause. (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public RepositoryMonitorException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Creates a new instance of FileSystemMonitorException.
	 * @param message the detail message
	 * @param cause the cause. (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
	 * @param enableSuppression whether or not suppression is enabled or disabled
	 * @param writableStackTrace whether or not suppression is enabled or disabled
	 */
	public RepositoryMonitorException(final String message, final Throwable cause,
			final boolean enableSuppression, final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
