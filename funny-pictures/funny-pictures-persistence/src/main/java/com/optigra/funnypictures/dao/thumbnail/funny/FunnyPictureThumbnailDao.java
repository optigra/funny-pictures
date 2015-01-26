package com.optigra.funnypictures.dao.thumbnail.funny;

import com.optigra.funnypictures.dao.Dao;
import com.optigra.funnypictures.model.thumbnail.FunnyPictureThumbnail;
import com.optigra.funnypictures.pagination.PagedResult;
import com.optigra.funnypictures.pagination.PagedSearch;

/**
 * Dao interface for Funny Picture Thumbnail.
 * @author ivanursul
 *
 */
public interface FunnyPictureThumbnailDao extends Dao<FunnyPictureThumbnail, Long> {
	PagedResult<FunnyPictureThumbnail> getThumbnails(PagedSearch<FunnyPictureThumbnail> pagedSearch);
}
