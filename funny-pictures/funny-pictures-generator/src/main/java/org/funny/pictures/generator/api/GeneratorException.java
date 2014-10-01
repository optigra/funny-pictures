package org.funny.pictures.generator.api;

/**
 * A runtime exception that can be thrown by image generators.
 * @author odisseus
 *
 */
public class GeneratorException extends RuntimeException {

	private static final long serialVersionUID = -5664636869348368615L;

	public GeneratorException(final String message) {
		super(message);
	}

	public GeneratorException(final Throwable cause) {
		super(cause);
	}

	public GeneratorException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public GeneratorException(final String message, final Throwable cause,
			final boolean enableSuppression, final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
