package com.optigra.funnypictures.generator.api;

import java.io.InputStream;

import com.optigra.funnypictures.model.content.MimeType;

/**
 * A set of input data required to add an annotation to the image.
 * @author odisseus
 *
 */
public class ImageLabellingContext {
	
	private final InputStream sourceInputStream;
	
	private final MimeType inputMimeType;
	
	private final String annotationText;

	/**
	 * Creates a context with supplied data fields.
	 * @param sourceInputStream input stream containing source image binary data 
	 * @param inputMimeType MIME type of the source image
	 * @param annotationText text of the annotation
	 */
	public ImageLabellingContext(final InputStream sourceInputStream,
			final MimeType inputMimeType, final String annotationText) {
		super();
		this.sourceInputStream = sourceInputStream;
		this.inputMimeType = inputMimeType;
		this.annotationText = annotationText;
	}

	public final InputStream getSourceInputStream() {
		return sourceInputStream;
	}

	public final MimeType getInputMimeType() {
		return inputMimeType;
	}

	public final String getAnnotationText() {
		return annotationText;
	}

	@Override
	public String toString() {
		return "ImageAnnotationContext [sourceInputStream=" + sourceInputStream
				+ ", inputMimeType=" + inputMimeType + ", annotationText="
				+ annotationText + "]";
	}
	

}
