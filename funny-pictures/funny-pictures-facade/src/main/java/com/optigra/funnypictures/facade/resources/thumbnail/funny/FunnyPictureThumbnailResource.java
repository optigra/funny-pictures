package com.optigra.funnypictures.facade.resources.thumbnail.funny;

import java.text.MessageFormat;

import com.optigra.funnypictures.facade.resources.ApiResource;
import com.optigra.funnypictures.model.thumbnail.ThumbnailType;

/**
 * Dto for Funny Picture Thumbnail.
 * @author ivanursul
 *
 */
public class FunnyPictureThumbnailResource extends ApiResource {
    
	private Long id;
    
	private Long funnyPictureId;
	
	private String url;
	
	private ThumbnailType thumbnailType;
	
	@Override
	public String getUri() {
		return MessageFormat.format("/funnypicturethumbnails/{0}", id);
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public Long getFunnyPictureId() {
		return funnyPictureId;
	}

	public void setFunnyPictureId(final Long funnyPictureId) {
		this.funnyPictureId = funnyPictureId;
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
		result = prime * result
				+ ((funnyPictureId == null) ? 0 : funnyPictureId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		FunnyPictureThumbnailResource other = (FunnyPictureThumbnailResource) obj;
		if (funnyPictureId == null) {
			if (other.funnyPictureId != null) {
				return false;
			}
		} else if (!funnyPictureId.equals(other.funnyPictureId)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
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
		return "FunnyPictureThumbnailResource [id=" + id + ", funnyPictureId="
				+ funnyPictureId + ", url=" + url + ", thumbnailType="
				+ thumbnailType + "]";
	}

}
