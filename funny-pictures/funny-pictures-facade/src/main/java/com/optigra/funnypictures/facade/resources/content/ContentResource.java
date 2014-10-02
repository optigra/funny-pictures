package com.optigra.funnypictures.facade.resources.content;

import java.io.InputStream;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.optigra.funnypictures.model.content.MimeType;

/**
 * Model for storing content.
 * 
 * @author rostyslav
 *
 */
public class ContentResource {

	private String path;

	@JsonIgnore
	// TODO: IP - think about better way to ignore field for serialization
	private InputStream contentStream;

	private MimeType mimeType;

	public String getPath() {
		return path;
	}

	public void setPath(final String path) {
		this.path = path;
	}

	public InputStream getContentStream() {
		return contentStream;
	}

	public void setContentStream(final InputStream contentStream) {
		this.contentStream = contentStream;
	}

	public MimeType getMimeType() {
		return mimeType;
	}

	public void setMimeType(final MimeType mimeType) {
		this.mimeType = mimeType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result)
				+ ((contentStream == null) ? 0 : contentStream.hashCode());
		result = (prime * result)
				+ ((mimeType == null) ? 0 : mimeType.hashCode());
		result = (prime * result) + ((path == null) ? 0 : path.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ContentResource other = (ContentResource) obj;
		if (contentStream == null) {
			if (other.contentStream != null) {
				return false;
			}
		} else if (!contentStream.equals(other.contentStream)) {
			return false;
		}
		if (mimeType != other.mimeType) {
			return false;
		}
		if (path == null) {
			if (other.path != null) {
				return false;
			}
		} else if (!path.equals(other.path)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "ContentResource [path=" + path + ", mimeType=" + mimeType + "]";
	}
}
