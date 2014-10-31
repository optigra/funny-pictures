package com.optigra.funnypictures.content.model;

import com.optigra.funnypictures.model.thumbnail.ThumbnailType;

/**
 * Extended content with mimeType.
 * @author ivanursul
 *
 */
public class ThumbnailContent extends Content {

	private ThumbnailType thumbnailType;

	public ThumbnailType getThumbnailType() {
		return thumbnailType;
	}

	public void setThumbnailType(final ThumbnailType thumbnailType) {
		this.thumbnailType = thumbnailType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((thumbnailType == null) ? 0 : thumbnailType.hashCode());
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
		ThumbnailContent other = (ThumbnailContent) obj;
		if (thumbnailType != other.thumbnailType) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "ThumbnailContent [thumbnailType=" + thumbnailType + "]";
	}

}
