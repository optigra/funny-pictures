package com.optigra.funnypictures.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.optigra.funnypictures.model.thumbnail.PictureThumbnail;

/**
 * Entity, that describes Template for Funny Pictures.
 * @author ivanursul
 *
 */
@Entity
@Table(name = "picture")
public class Picture implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "url")
	private String url;

	@OneToMany(mappedBy = "picture")
	private Set<FunnyPicture> funnyPictures;
	
	@OneToMany(mappedBy = "picture")
	private List<PictureThumbnail> thumbnails;
	
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

	public Set<FunnyPicture> getFunnyPictures() {
		return funnyPictures;
	}

	public List<PictureThumbnail> getThumbnails() {
		return thumbnails;
	}

	public void setThumbnails(final List<PictureThumbnail> thumbnails) {
		this.thumbnails = thumbnails;
	}

	public void setFunnyPictures(final Set<FunnyPicture> funnyPictures) {
		this.funnyPictures = funnyPictures;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((funnyPictures == null) ? 0 : funnyPictures.hashCode());
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
		Picture other = (Picture) obj;
		if (funnyPictures == null) {
			if (other.funnyPictures != null) {
				return false;
			}
		} else if (!funnyPictures.equals(other.funnyPictures)) {
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
		return "Picture [id=" + id + ", name=" + name + ", url=" + url + "]";
	}

}
