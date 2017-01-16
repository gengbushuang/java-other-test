package com.algorithm.image;

public class ImageException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6999877211494768068L;

	public ImageException() {
		super();
	}

	public ImageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ImageException(String message, Throwable cause) {
		super(message, cause);
	}

	public ImageException(String message) {
		super(message);
	}

	public ImageException(Throwable cause) {
		super(cause);
	}

}
