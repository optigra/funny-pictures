package com.optigra.funnypictures.generator.api;

import java.io.InputStream;

import com.optigra.funnypictures.model.content.MimeType;

/**
 * A set of input data required to generate an advice comic.
 * @author odisseus
 *
 */
public class AdviceMemeContext {
	
	private final InputStream templateInputStream;
	
	private final MimeType inputMimeType;
	
	private final String topCaption;
	
	private final String bottomCaption;

	/**
	 * Creates a context with supplied data fields.
	 * @param templateInputStream input stream containing template image binary data 
	 * @param mimeType MIME type of the template image
	 * @param topCaption text of top caption
	 * @param bottomCaption text of bottom caption
	 */
	public AdviceMemeContext(final InputStream templateInputStream, final MimeType mimeType,
			final String topCaption, final String bottomCaption) {
		this.templateInputStream = templateInputStream;
		this.inputMimeType = mimeType;
		this.topCaption = topCaption;
		this.bottomCaption = bottomCaption;
	}

	public InputStream getTemplateInputStream() {
		return templateInputStream;
	}

	public MimeType getMimeType() {
		return inputMimeType;
	}

	public String getTopCaption() {
		return topCaption;
	}

	public String getBottomCaption() {
		return bottomCaption;
	}

	@Override
	public String toString() {
		return "AdviceMemeContext [templateInputStream=" + templateInputStream
				+ ", mimeType=" + inputMimeType.getType() + ", topCaption=" + topCaption
				+ ", bottomCaption=" + bottomCaption + "]";
	}

	
}
