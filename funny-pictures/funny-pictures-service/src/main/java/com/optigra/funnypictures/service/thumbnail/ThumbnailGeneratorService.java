package com.optigra.funnypictures.service.thumbnail;

import java.util.List;

import com.optigra.funnypictures.content.model.Content;
import com.optigra.funnypictures.content.model.ThumbnailContent;

/**
 * Interface for thumbnail generator service.
 * @author ivanursul
 *
 */
public interface ThumbnailGeneratorService {

	/**
	 * Method for generating thumbnails.
	 * @param content content.
	 * @return list of thumbnail content.
	 */
	List<ThumbnailContent> generateThumbnails(Content content);

}
