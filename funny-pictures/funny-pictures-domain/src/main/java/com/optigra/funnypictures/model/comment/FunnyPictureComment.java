package com.optigra.funnypictures.model.comment;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.optigra.funnypictures.model.FunnyPicture;

/**
 * Funny picture comment.
 * @author ivanursul
 *
 */
@Entity
@Table(name = "funny_picture_comment")
public class FunnyPictureComment extends Comment {
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "parent_id")
	private FunnyPictureComment parent;
	
	@ManyToOne
	@JoinColumn(name = "funny_picture_id")
	private FunnyPicture funnyPicture;

	public FunnyPicture getFunnyPicture() {
		return funnyPicture;
	}

	public void setFunnyPicture(final FunnyPicture funnyPicture) {
		this.funnyPicture = funnyPicture;
	}

	public FunnyPictureComment getParent() {
		return parent;
	}

	public void setParent(final FunnyPictureComment parent) {
		this.parent = parent;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((funnyPicture == null) ? 0 : funnyPicture.hashCode());
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
		FunnyPictureComment other = (FunnyPictureComment) obj;
		if (funnyPicture == null) {
			if (other.funnyPicture != null) {
				return false;
			}
		} else if (!funnyPicture.equals(other.funnyPicture)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "FunnyPictureComment [funnyPicture=" + funnyPicture + "]";
	}
	
}
