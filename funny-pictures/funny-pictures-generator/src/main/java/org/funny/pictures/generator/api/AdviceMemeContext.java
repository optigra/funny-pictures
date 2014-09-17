package org.funny.pictures.generator.api;

import java.io.InputStream;

public class AdviceMemeContext {
	
	private final InputStream templateInputStream;
	
	private final Object mimeType;
	
	private final String topCaption;
	
	private final String bottomCaption;

	public AdviceMemeContext(InputStream templateInputStream, Object mimeType,
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

	public Object getMimeType() {
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
				+ ", mimeType=" + mimeType + ", topCaption=" + topCaption
				+ ", bottomCaption=" + bottomCaption + "]";
	}

	
}
