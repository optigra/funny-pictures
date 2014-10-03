package org.funny.pictures.generator.api;

import java.awt.Dimension;
import java.io.InputStream;
import com.optigra.funnypictures.model.content.MimeType;

/**
 * A set of input data required to generate an image thumbnail.
 * @author odisseus
 *
 */
public class ThumbnailContext {
	
	private final InputStream templateInputStream;
	
	private final MimeType inputMimeType;
	
	private final Dimension thumbnailDimension;

	/**
	 * Creates a context with supplied data fields.
	 * @param templateInputStream input stream containing template image binary data 
	 * @param inputMimeType MIME type of the template image
	 * @param thumbnailDimension width and height of the thumbnail
	 */
	public ThumbnailContext(final InputStream templateInputStream,
			final MimeType inputMimeType, final Dimension thumbnailDimension) {
		super();
		this.templateInputStream = templateInputStream;
		this.inputMimeType = inputMimeType;
		this.thumbnailDimension = thumbnailDimension;
	}

	public final InputStream getTemplateInputStream() {
		return templateInputStream;
	}

	public final MimeType getInputMimeType() {
		return inputMimeType;
	}

	public final Dimension getThumbnailDimension() {
		return thumbnailDimension;
	}

	@Override
	public String toString() {
		return "ThumbnailContext [templateInputStream=" + templateInputStream
				+ ", inputMimeType=" + inputMimeType + ", thumbnailDimension="
				+ thumbnailDimension + "]";
	}
	

}
