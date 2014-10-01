package org.funny.pictures.generator.api;

import java.io.InputStream;

import com.optigra.funnypictures.model.content.MimeType;

/**
 * A handle to binary image data.
 * @author odisseus
 *
 */
public class ImageHandle {
	
	private final InputStream imageInputStream;
	
	private final MimeType imageFormat;

	/**
	 * Creates a new image handle.
	 * @param imageInputStream input stream with binary image data
	 * @param imageFormat image MIME type
	 */
	public ImageHandle(final InputStream imageInputStream, final MimeType imageFormat) {
		super();
		this.imageInputStream = imageInputStream;
		this.imageFormat = imageFormat;
	}

	/**
	 * Returns image data as an input stream.
	 * @return
	 */
	public InputStream getImageInputStream() {
		return imageInputStream;
	}

	/**
	 * Returns image MIME type.
	 * @return
	 */
	public MimeType getImageFormat() {
		return imageFormat;
	}

	@Override
	public String toString() {
		return "ImageHandle [imageInputStream=" + imageInputStream
				+ ", imageFormat=" + imageFormat + "]";
	}
	

}
