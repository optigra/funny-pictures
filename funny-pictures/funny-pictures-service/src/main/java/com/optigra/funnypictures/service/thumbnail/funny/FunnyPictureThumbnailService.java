package com.optigra.funnypictures.service.thumbnail.funny;

import com.optigra.funnypictures.model.thumbnail.FunnyPictureThumbnail;
import com.optigra.funnypictures.pagination.PagedResult;
import com.optigra.funnypictures.pagination.PagedSearch;

/**
 * Interface for funny picture thumbnail.
 * @author ivanursul
 *
 */
public interface FunnyPictureThumbnailService {

	/**
	 * Mthod for creating new thumbnail.
	 * @param funnyPictureThumbnail
	 */
	void createFunnyPictureThumbnail(FunnyPictureThumbnail funnyPictureThumbnail);
	
	/**
	 * Get's funny picture thumbnail by identity.
	 * @param id identity.
	 * @return Picture entity.
	 */
	FunnyPictureThumbnail getFunnyPictureThumbnail(Long id);
	
	/**
	 * Retrieves a paged list of funny picture thumbnails.
	 * @param pagedSearch search conditions that should specify results offset and limit.
	 * @return a section of the result list
	 */
	PagedResult<FunnyPictureThumbnail> getFunnyPictureThumbnails(PagedSearch<FunnyPictureThumbnail> pagedSearch);
	
	/**
	 * Retrieves a paged list of funny picture thumbnails.
	 * @param pagedSearch search conditions that should specify results offset and limit.
	 * @param id of Picture
	 * @return a section of the result list
	 */
	PagedResult<FunnyPictureThumbnail> getFunnyPictureThumbnailsByPicture(PagedSearch<FunnyPictureThumbnail> pagedSearch, final Long id);

}
