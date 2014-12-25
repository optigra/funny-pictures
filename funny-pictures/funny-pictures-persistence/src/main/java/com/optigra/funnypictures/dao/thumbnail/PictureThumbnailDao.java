package com.optigra.funnypictures.dao.thumbnail;

import com.optigra.funnypictures.dao.Dao;
import com.optigra.funnypictures.model.thumbnail.PictureThumbnail;
import com.optigra.funnypictures.pagination.PagedResult;
import com.optigra.funnypictures.pagination.PagedSearch;

/**
 * Picture Thumbnail DAo.
 * @author ivanursul
 *
 */
public interface PictureThumbnailDao extends Dao<PictureThumbnail, Long> {
	
	/**
	 * Retrieves a paged list of Picture thumbnails of given thumbnail type
	 * ordered by id of corresponding picture descending.
	 * @param pagedSearch
	 * @return a list of picture thumbnails.
	 */
	PagedResult<PictureThumbnail> getThumbnails(PagedSearch<PictureThumbnail> pagedSearch);

}
