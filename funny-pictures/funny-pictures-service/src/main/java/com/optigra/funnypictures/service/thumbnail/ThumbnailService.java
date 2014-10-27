package com.optigra.funnypictures.service.thumbnail;

import com.optigra.funnypictures.model.thumbnail.Thumbnail;

/**
 * Interface, that describes thumbnail service layer.
 * @author ivanursul
 *
 */
public interface ThumbnailService {

	/**
	 * Method for creating new thumbnail.
	 * @param thumbnail
	 */
	void createThumbnail(Thumbnail thumbnail);

}
