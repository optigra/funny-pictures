package org.funny.pictures.generator.api;

import java.io.InputStream;

import com.optigra.funnypictures.model.content.MimeType;

public class AdviceMemeContext {
	
	private final InputStream templateInputStream;
	
	private final MimeType mimeType;
	
	private final String topCaption;
	
	private final String bottomCaption;

	public AdviceMemeContext(InputStream templateInputStream, MimeType mimeType,
			String topCaption, String bottomCaption) {
		super();
		this.templateInputStream = templateInputStream;
		this.mimeType = mimeType;
		this.topCaption = topCaption;
		this.bottomCaption = bottomCaption;
	}

	public InputStream getTemplateInputStream() {
		return templateInputStream;
	}

	public MimeType getMimeType() {
		return mimeType;
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
				+ ", mimeType=" + mimeType.getType() + ", topCaption=" + topCaption
				+ ", bottomCaption=" + bottomCaption + "]";
	}

	
}
