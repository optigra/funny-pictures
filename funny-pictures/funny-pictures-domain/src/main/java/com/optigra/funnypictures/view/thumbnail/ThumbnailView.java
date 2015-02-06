package com.optigra.funnypictures.view.thumbnail;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

import com.optigra.funnypictures.model.thumbnail.ThumbnailType;
import com.optigra.funnypictures.view.View;

/**
 * Abstract ThumbnailView entity .
 * @author oleh.zasadnyy
 *
 */
@MappedSuperclass
public abstract class ThumbnailView extends View {
	private static final long serialVersionUID = 1L;

	@Column(name = "url")
	private String url;

	@Column(name = "type")
	@Enumerated(EnumType.STRING)
	private ThumbnailType thumbnailType;

	public String getUrl() {
		return url;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

	public ThumbnailType getThumbnailType() {
		return thumbnailType;
	}

	public void setThumbnailType(final ThumbnailType thumbnailType) {
		this.thumbnailType = thumbnailType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((thumbnailType == null) ? 0 : thumbnailType.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ThumbnailView other = (ThumbnailView) obj;
		if (thumbnailType != other.thumbnailType) {
			return false;
		}
		if (url == null) {
			if (other.url != null) {
				return false;
			}
		} else if (!url.equals(other.url)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "ThumbnailView [url=" + url + ", thumbnailType=" + thumbnailType
				+ "]";
	}
}
