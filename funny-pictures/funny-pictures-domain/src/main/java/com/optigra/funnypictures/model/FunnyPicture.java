package com.optigra.funnypictures.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.optigra.funnypictures.model.thumbnail.FunnyPictureThumbnail;

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
	
	@OneToMany(mappedBy = "funnyPicture")
	private List<FunnyPictureThumbnail> thumbnails;
	
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((footer == null) ? 0 : footer.hashCode());
		result = prime * result + ((header == null) ? 0 : header.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		FunnyPicture other = (FunnyPicture) obj;
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
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
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
		return "FunnyPicture [id=" + id + ", name=" + name + ", url=" + url
				+ ", header=" + header + ", footer=" + footer + "]";
	}

}
