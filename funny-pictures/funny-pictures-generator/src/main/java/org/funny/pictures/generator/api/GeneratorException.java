package org.funny.pictures.generator.api;

/**
 * A runtime exception that can be thrown by image generators.
 * @author odisseus
 *
 */
public class GeneratorException extends RuntimeException {

	private static final long serialVersionUID = -5664636869348368615L;

	/**
	 * Creates a new instance of GeneratorException.
	 * @param message the detail message
	 */
	public GeneratorException(final String message) {
		super(message);
	}

	/**
	 * Creates a new instance of GeneratorException.
	 * @param cause the cause. (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public GeneratorException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Creates a new instance of GeneratorException.
	 * @param message the detail message
	 * @param cause the cause. (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public GeneratorException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Creates a new instance of GeneratorException.
	 * @param message the detail message
	 * @param cause the cause. (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
	 * @param enableSuppression whether or not suppression is enabled or disabled
	 * @param writableStackTrace whether or not suppression is enabled or disabled
	 */
	public GeneratorException(final String message, final Throwable cause,
			final boolean enableSuppression, final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
