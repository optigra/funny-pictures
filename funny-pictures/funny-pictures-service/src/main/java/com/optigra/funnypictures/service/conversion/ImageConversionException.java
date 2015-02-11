package com.optigra.funnypictures.service.conversion;

/**
 * A runtime exception that can be thrown if image conversion fails.
 * @author odisseus
 *
 */
public class ImageConversionException extends RuntimeException {

	private static final long serialVersionUID = 4542418924951507730L;

	/**
	 * Creates a new instance of ImageConversionException.
	 * @param message the detail message
	 */
	public ImageConversionException(final String message) {
		super(message);
	}

	/**
	 * Creates a new instance of ImageConversionException.
	 * @param cause the cause. (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public ImageConversionException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Creates a new instance of ImageConversionException.
	 * @param message the detail message
	 * @param cause the cause. (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public ImageConversionException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Creates a new instance of ImageConversionException.
	 * @param message the detail message
	 * @param cause the cause. (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
	 * @param enableSuppression whether or not suppression is enabled or disabled
	 * @param writableStackTrace whether or not suppression is enabled or disabled
	 */
	public ImageConversionException(final String message, final Throwable cause,
			final boolean enableSuppression, final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
