package com.optigra.funnypictures.dao.thumbnail.funny;

import com.optigra.funnypictures.dao.Dao;
import com.optigra.funnypictures.model.thumbnail.FunnyPictureThumbnail;
import com.optigra.funnypictures.pagination.PagedResult;
import com.optigra.funnypictures.pagination.PagedSearch;

/**
 * Dao interface for Funny Picture Thumbnail.
 * @author oleh.zasadnyy
 *
 */
public interface FunnyPictureThumbnailDao extends Dao<FunnyPictureThumbnail, Long> {
	
	/**
	 * Retrieves a paged list of Funny Picture thumbnails of given thumbnail type
	 * ordered by id of corresponding picture descending.
	 * @param pagedSearch
	 * @return a list of funny picture thumbnails.
	 */
	PagedResult<FunnyPictureThumbnail> getThumbnails(PagedSearch<FunnyPictureThumbnail> pagedSearch);
}
