package com.optigra.funnypictures.facade.resources.picture;

import com.optigra.funnypictures.facade.resources.ApiResource;
import com.optigra.funnypictures.facade.resources.tag.TagResource;
import com.optigra.funnypictures.facade.resources.thumbnail.funny.FunnyPictureThumbnailResource;

import java.text.MessageFormat;
import java.util.List;

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

    private List<TagResource> tags;

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

    public List<TagResource> getTags() {
        return tags;
    }

    public void setTags(final List<TagResource> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FunnyPictureResource that = (FunnyPictureResource) o;

        if (footer != null ? !footer.equals(that.footer) : that.footer != null) {
            return false;
        }
        if (header != null ? !header.equals(that.header) : that.header != null) {
            return false;
        }
        if (name != null ? !name.equals(that.name) : that.name != null) {
            return false;
        }
        if (tags != null ? !tags.equals(that.tags) : that.tags != null) {
            return false;
        }
        if (template != null ? !template.equals(that.template) : that.template != null) {
            return false;
        }
        if (thumbnails != null ? !thumbnails.equals(that.thumbnails) : that.thumbnails != null) {
            return false;
        }
        if (url != null ? !url.equals(that.url) : that.url != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = url != null ? url.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (header != null ? header.hashCode() : 0);
        result = 31 * result + (footer != null ? footer.hashCode() : 0);
        result = 31 * result + (template != null ? template.hashCode() : 0);
        result = 31 * result + (thumbnails != null ? thumbnails.hashCode() : 0);
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FunnyPictureResource{"
                + "url='" + url + '\''
                + ", name='" + name + '\''
                + ", header='" + header + '\''
                + ", footer='" + footer + '\''
                + ", template=" + template
                + ", thumbnails=" + thumbnails
                + ", tags=" + tags
                + "} " + super.toString();
    }
}
