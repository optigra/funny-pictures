package com.optigra.funnypictures.facade.resources.thumbnail;

import java.text.MessageFormat;

import com.optigra.funnypictures.facade.resources.ApiResource;
import com.optigra.funnypictures.model.thumbnail.ThumbnailType;

/**
 * 
 * @author ivanursul
 *
 */
public class PictureThumbnailResource extends ApiResource {

	private Long id;
    
	private Long pictureId;
	
	private String url;
	
	private ThumbnailType thumbnailType;
	
	@Override
	public String getUri() {
		return MessageFormat.format("/pictures/thumbnails/{0}", id);
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public Long getPictureId() {
		return pictureId;
	}

	public void setPictureId(final Long pictureId) {
		this.pictureId = pictureId;
	}

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
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((pictureId == null) ? 0 : pictureId.hashCode());
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
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		PictureThumbnailResource other = (PictureThumbnailResource) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (pictureId == null) {
			if (other.pictureId != null) {
				return false;
			}
		} else if (!pictureId.equals(other.pictureId)) {
			return false;
		}
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
		return "PictureThumbnailResource [id=" + id + ", pictureId="
				+ pictureId + ", url=" + url + ", thumbnailType="
				+ thumbnailType + "]";
	}

}
