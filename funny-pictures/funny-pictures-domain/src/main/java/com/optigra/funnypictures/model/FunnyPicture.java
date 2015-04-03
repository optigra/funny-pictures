package com.optigra.funnypictures.model;

import com.optigra.funnypictures.model.tag.Tag;
import com.optigra.funnypictures.model.thumbnail.FunnyPictureThumbnail;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

/**
 * Entity, that describes generated picture with text.
 * @author ivanursul
 *
 */
@Entity
@Table(name = "funny_picture")
public class FunnyPicture implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "url")
	private String url;
	
	@Column(name = "header")
	private String header;
	
	@Column(name = "footer")
	private String footer;
	
	@ManyToOne
	@JoinColumn(name = "picture_id", nullable = false)
	private Picture picture;
	
	@OneToMany(mappedBy = "funnyPicture", cascade = CascadeType.ALL)
	private List<FunnyPictureThumbnail> thumbnails;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "funny_picture_tag",
			joinColumns = @JoinColumn(name = "funny_picture_id"),
			inverseJoinColumns = @JoinColumn(name = "tag_id")
	)
	private List<Tag> tags;
	
	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
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

	public Picture getPicture() {
		return picture;
	}

	public void setPicture(final Picture picture) {
		this.picture = picture;
	}

	public List<FunnyPictureThumbnail> getThumbnails() {
		return thumbnails;
	}

	public void setThumbnails(final List<FunnyPictureThumbnail> thumbnails) {
		this.thumbnails = thumbnails;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(final List<Tag> tags) {
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

		FunnyPicture that = (FunnyPicture) o;

		if (id != null ? !id.equals(that.id) : that.id != null) {
			return false;
		}
		if (name != null ? !name.equals(that.name) : that.name != null) {
			return false;
		}
		if (url != null ? !url.equals(that.url) : that.url != null) {
			return false;
		}
		if (header != null ? !header.equals(that.header) : that.header != null) {
			return false;
		}
		if (footer != null ? !footer.equals(that.footer) : that.footer != null) {
			return false;
		}
		if (picture != null ? !picture.equals(that.picture) : that.picture != null) {
			return false;
		}
		if (thumbnails != null ? !thumbnails.equals(that.thumbnails) : that.thumbnails != null) {
			return false;
		}
		return !(tags != null ? !tags.equals(that.tags) : that.tags != null);

	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (url != null ? url.hashCode() : 0);
		result = 31 * result + (header != null ? header.hashCode() : 0);
		result = 31 * result + (footer != null ? footer.hashCode() : 0);
		result = 31 * result + (picture != null ? picture.hashCode() : 0);
		result = 31 * result + (thumbnails != null ? thumbnails.hashCode() : 0);
		result = 31 * result + (tags != null ? tags.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "FunnyPicture [id=" + id + ", name=" + name + ", url=" + url
				+ ", header=" + header + ", footer=" + footer + "]";
	}

}
