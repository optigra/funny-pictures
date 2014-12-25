package com.optigra.funnypictures.service.thumbnail;

import com.optigra.funnypictures.model.thumbnail.PictureThumbnail;
import com.optigra.funnypictures.pagination.PagedResult;
import com.optigra.funnypictures.pagination.PagedSearch;

/**
 * Interface that declares most of operations for Picture Thumbnail.
 * @author ivanursul
 *
 */
public interface PictureThumbnailService {

	/**
	 * Stores a picture thumbnail (the object, not the image itself).
	 * @param pictureThumbnail
	 */
	void createPictureThumbnail(PictureThumbnail pictureThumbnail);
	
	/**
	 * Retrieves a paged list of picture thumbnails.
	 * @param pagedSearch search conditions that should specify results offset and limit.
	 * @return a section of the result list
	 */
	PagedResult<PictureThumbnail> getPictureThumbnails(PagedSearch<PictureThumbnail> pagedSearch);

}
