package org.funny.pictures.generator.api;

public class GeneratorException extends RuntimeException {

	private static final long serialVersionUID = -5664636869348368615L;

	public GeneratorException(String message) {
		super(message);
	}

	public GeneratorException(Throwable cause) {
		super(cause);
	}

	public GeneratorException(String message, Throwable cause) {
		super(message, cause);
	}

	public GeneratorException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
