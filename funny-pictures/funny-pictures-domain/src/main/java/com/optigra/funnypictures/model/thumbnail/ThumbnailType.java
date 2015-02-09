package com.optigra.funnypictures.model.thumbnail;


/**
 * Enumeration, that describes Type of Thumbnail.
 * @author ivanursul
 *
 */
public enum ThumbnailType {
	SMALL(128, 128),
	MEDIUM(256, 256),
	BIG(512, 512),
	RANDOM(0, 0);
	
	private int width;
	private int height;
	
	/**
	 * Constructor, that takes width and height as arguments.
	 * @param width
	 * @param height
	 */
	ThumbnailType(final int width, final int height) {
		this.width = width;
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(final int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(final int height) {
		this.height = height;
	}
	
}
