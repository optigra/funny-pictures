package com.optigra.funnypictures.model.thumbnail;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.optigra.funnypictures.model.Picture;

/**
 * Picture Thumbnail entity.
 * @author ivanursul
 *
 */
@Entity
@Table(name = "picture_thumbnail")
public class PictureThumbnail extends Thumbnail {
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "picture_id")
	private Picture picture;

	public Picture getPicture() {
		return picture;
	}

	public void setPicture(final Picture picture) {
		this.picture = picture;
	}

}
