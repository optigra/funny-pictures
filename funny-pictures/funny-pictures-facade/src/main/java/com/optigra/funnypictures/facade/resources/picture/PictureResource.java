package com.optigra.funnypictures.facade.resources.picture;

import java.text.MessageFormat;
import java.util.List;

import com.optigra.funnypictures.facade.resources.ApiResource;
import com.optigra.funnypictures.facade.resources.thumbnail.PictureThumbnailResource;

/**
 * Picture model with id, name and picture url.
 * 
 * @author rostyslav
 *
 */
public class PictureResource extends ApiResource {

	private String name;
	
	private String url;
	
	private List<PictureThumbnailResource> thumbnails;

	@Override
	public String getUri() {
		return MessageFormat.format("/pictures/{0}", getId());
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

	public List<PictureThumbnailResource> getThumbnails() {
		return thumbnails;
	}

	public void setThumbnails(final List<PictureThumbnailResource> thumbnails) {
		this.thumbnails = thumbnails;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((thumbnails == null) ? 0 : thumbnails.hashCode());
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
		PictureResource other = (PictureResource) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (thumbnails == null) {
			if (other.thumbnails != null) {
				return false;
			}
		} else if (!thumbnails.equals(other.thumbnails)) {
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
		return "PictureResource [name=" + name + ", url=" + url
				+ ", thumbnails=" + thumbnails + "]";
	}

}
