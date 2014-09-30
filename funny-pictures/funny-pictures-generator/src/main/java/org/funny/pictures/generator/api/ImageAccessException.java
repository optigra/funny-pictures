package org.funny.pictures.generator.api;

public class ImageAccessException extends RuntimeException {

	private static final long serialVersionUID = -8443009517861390941L;
	
	public ImageAccessException(String message) {
		super(message);
	}

	public ImageAccessException(Throwable cause) {
		super(cause);
	}

	public ImageAccessException(String message, Throwable cause) {
		super(message, cause);
	}

	public ImageAccessException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
