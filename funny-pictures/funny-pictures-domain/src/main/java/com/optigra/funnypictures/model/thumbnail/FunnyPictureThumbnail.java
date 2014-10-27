package com.optigra.funnypictures.model.thumbnail;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.optigra.funnypictures.model.FunnyPicture;
import com.optigra.funnypictures.model.Model;

/**
 * Entity, that describes relation between FunnyPicture and
 * Thumbnail entities.
 * @author ivanursul
 *
 */
@Entity
@Table(name = "funny_picture_thumbnail")
public class FunnyPictureThumbnail extends Model {
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "thumbnail_id")
	private Thumbnail thumbnail;
	
	@ManyToOne
	@JoinColumn(name = "funny_picture_id")
	private FunnyPicture funnyPicture;
	
	public Thumbnail getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(final Thumbnail thumbnail) {
		this.thumbnail = thumbnail;
	}

	public FunnyPicture getFunnyPicture() {
		return funnyPicture;
	}

	public void setFunnyPicture(final FunnyPicture funnyPicture) {
		this.funnyPicture = funnyPicture;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((funnyPicture == null) ? 0 : funnyPicture.hashCode());
		result = prime * result
				+ ((thumbnail == null) ? 0 : thumbnail.hashCode());
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
		FunnyPictureThumbnail other = (FunnyPictureThumbnail) obj;
		if (funnyPicture == null) {
			if (other.funnyPicture != null) {
				return false;
			}
		} else if (!funnyPicture.equals(other.funnyPicture)) {
			return false;
		}
		if (thumbnail == null) {
			if (other.thumbnail != null) {
				return false;
			}
		} else if (!thumbnail.equals(other.thumbnail)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "FunnyPictureThumbnail [thumbnail=" + thumbnail
				+ ", funnyPicture=" + funnyPicture + "]";
	}

}
