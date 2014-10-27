package org.funny.pictures.generator.api;

/**
 * A runtime exception that can be thrown by image reading utilities.
 * @author odisseus
 *
 */
public class ImageAccessException extends RuntimeException {

	private static final long serialVersionUID = -8443009517861390941L;
	
	/**
	 * Creates a new instance of ImageAccessException.
	 * @param message the detail message
	 */
	public ImageAccessException(final String message) {
		super(message);
	}

	/**
	 * Creates a new instance of ImageAccessException.
	 * @param cause the cause. (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public ImageAccessException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Creates a new instance of ImageAccessException.
	 * @param message the detail message
	 * @param cause the cause. (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public ImageAccessException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Creates a new instance of ImageAccessException.
	 * @param message the detail message
	 * @param cause the cause. (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
	 * @param enableSuppression whether or not suppression is enabled or disabled
	 * @param writableStackTrace whether or not suppression is enabled or disabled
	 */
	public ImageAccessException(final String message, final Throwable cause,
			final boolean enableSuppression, final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
