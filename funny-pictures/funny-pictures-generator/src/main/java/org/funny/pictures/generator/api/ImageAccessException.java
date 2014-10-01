package org.funny.pictures.generator.api;

/**
 * A runtime exception that can be thrown by image reading utilities.
 * @author odisseus
 *
 */
public class ImageAccessException extends RuntimeException {

	private static final long serialVersionUID = -8443009517861390941L;
	
	public ImageAccessException(final String message) {
		super(message);
	}

	public ImageAccessException(final Throwable cause) {
		super(cause);
	}

	public ImageAccessException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public ImageAccessException(final String message, final Throwable cause,
			final boolean enableSuppression, final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
