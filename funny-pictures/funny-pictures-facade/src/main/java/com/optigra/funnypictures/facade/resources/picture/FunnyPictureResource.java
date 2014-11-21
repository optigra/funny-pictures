package com.optigra.funnypictures.facade.resources.picture;

import java.text.MessageFormat;
import java.util.List;

import com.optigra.funnypictures.facade.resources.ApiResource;
import com.optigra.funnypictures.facade.resources.thumbnail.funny.FunnyPictureThumbnailResource;

/**
 * Model for funny picture with PictureResource, header and footer texts.
 * 
 * @author rostyslav
 *
 */
public class FunnyPictureResource extends ApiResource {

	private String url;

	private String name;

	private String header;

	private String footer;

	private PictureResource template;

	private List<FunnyPictureThumbnailResource> thumbnails;	
	
	@Override
	public String getUri() {
		return MessageFormat.format("/funnies/{0}", getId());
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(final String header) {
		this.header = header;
	}

	public String getFooter() {
		return footer;
	}

	public void setFooter(final String footer) {
		this.footer = footer;
	}

	public PictureResource getTemplate() {
		return template;
	}

	public void setTemplate(final PictureResource template) {
		this.template = template;
	}

	public List<FunnyPictureThumbnailResource> getThumbnails() {
		return thumbnails;
	}

	public void setThumbnails(final List<FunnyPictureThumbnailResource> thumbnails) {
		this.thumbnails = thumbnails;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((footer == null) ? 0 : footer.hashCode());
		result = prime * result + ((header == null) ? 0 : header.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((template == null) ? 0 : template.hashCode());
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
		FunnyPictureResource other = (FunnyPictureResource) obj;
		if (footer == null) {
			if (other.footer != null) {
				return false;
			}
		} else if (!footer.equals(other.footer)) {
			return false;
		}
		if (header == null) {
			if (other.header != null) {
				return false;
			}
		} else if (!header.equals(other.header)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (template == null) {
			if (other.template != null) {
				return false;
			}
		} else if (!template.equals(other.template)) {
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
		return "FunnyPictureResource [url=" + url + ", name="
				+ name + ", header=" + header + ", footer=" + footer
				+ ", template=" + template + ", thumbnails=" + thumbnails + "]";
	}

}
