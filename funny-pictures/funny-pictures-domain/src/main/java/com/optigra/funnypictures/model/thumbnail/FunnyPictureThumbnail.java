package com.optigra.funnypictures.model.thumbnail;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.optigra.funnypictures.model.FunnyPicture;

/**
 * Entity, that describes relation between FunnyPicture and
 * Thumbnail entities.
 * @author ivanursul
 *
 */
@Entity
@Table(name = "funny_picture_thumbnail")
public class FunnyPictureThumbnail extends Thumbnail {
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "funny_picture_id")
	private FunnyPicture funnyPicture;
	
	public FunnyPicture getFunnyPicture() {
		return funnyPicture;
	}

	public void setFunnyPicture(final FunnyPicture funnyPicture) {
		this.funnyPicture = funnyPicture;
	}
}
