package com.optigra.funnypictures.generator.api;

import java.io.InputStream;
import java.util.Collections;
import java.util.Map;
import java.awt.Rectangle;

import com.optigra.funnypictures.model.content.MimeType;

/**
 * A set of input data required to generate a comic with an arbitrary number of rectangular speech blocks.
 * @author odisseus
 *
 */
public class ComicContext {
	
	private final InputStream templateInputStream;
	
	private final MimeType inputMimeType;
	
	private final Map<Rectangle, String> textBlocks; 

	/**
	 * Creates a context with supplied data fields.
	 * @param templateInputStream input stream containing template image binary data 
	 * @param mimeType MIME type of the template image
	 * @param textBlocks a map of text blocks to insert
	 */
	public ComicContext(final InputStream templateInputStream, final MimeType mimeType,
			final Map<Rectangle, String> textBlocks) {
		this.templateInputStream = templateInputStream;
		this.inputMimeType = mimeType;
		//TODO consider using Guava's ImmutableMap
		this.textBlocks = textBlocks;
	}

	public InputStream getTemplateInputStream() {
		return templateInputStream;
	}

	public MimeType getMimeType() {
		return inputMimeType;
	}

	public Map<Rectangle, String> getTextBlocks() {
		//TODO consider using Guava's ImmutableMap
		return Collections.unmodifiableMap(textBlocks);
	}

	@Override
	public String toString() {
		return "ComicContext [templateInputStream=" + templateInputStream
				+ ", inputMimeType=" + inputMimeType + ", textBlocks="
				+ textBlocks + "]";
	}

	
}
