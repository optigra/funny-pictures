package com.optigra.funnypictures.view.thumbnail;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Picture Thumbnail View entity.
 * @author oleh.zasadnyy
 *
 */
@Entity
@Table(name = "v_random_picture_thumbnail_sm")
public class RandomPictureThumbnailView extends ThumbnailView {
	private static final long serialVersionUID = 1L;
	
	@Column(name = "picture_id")
	private Long pictureId;

	public Long getPictureId() {
		return pictureId;
	}

	public void setPictureId(final Long pictureId) {
		this.pictureId = pictureId;
	}
	
	
}
