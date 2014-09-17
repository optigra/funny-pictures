package org.funny.pictures.generator.api;

import java.io.InputStream;

import com.optigra.funnypictures.model.content.MimeType;

public class ImageHandle {
	
	private final InputStream imageInputStream;
	
	private final MimeType imageFormat;

	public ImageHandle(InputStream imageInputStream, MimeType imageFormat) {
		super();
		this.imageInputStream = imageInputStream;
		this.imageFormat = imageFormat;
	}

	public InputStream getImageInputStream() {
		return imageInputStream;
	}

	public MimeType getImageFormat() {
		return imageFormat;
	}

	@Override
	public String toString() {
		return "AdviceMemePicture [imageInputStream=" + imageInputStream
				+ ", imageFormat=" + imageFormat + "]";
	}
	

}
