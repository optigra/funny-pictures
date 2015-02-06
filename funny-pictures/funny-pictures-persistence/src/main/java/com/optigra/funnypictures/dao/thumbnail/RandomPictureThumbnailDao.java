package com.optigra.funnypictures.dao.thumbnail;

import com.optigra.funnypictures.dao.Dao;
import com.optigra.funnypictures.pagination.PagedResult;
import com.optigra.funnypictures.pagination.PagedSearch;
import com.optigra.funnypictures.view.thumbnail.RandomPictureThumbnailView;

/**
 * Random Picture Thumbnail DAo.
 * @author oleh.zasadnyy
 *
 */
public interface RandomPictureThumbnailDao extends Dao<RandomPictureThumbnailView, Long> {
	
	/**
	 * Retrieves a paged list of Picture thumbnails of random thumbnail type
	 * ordered by id of corresponding picture descending.
	 * @param pagedSearch
	 * @return a list of picture thumbnails.
	 */
	PagedResult<RandomPictureThumbnailView> getRandomThumbnails(PagedSearch<RandomPictureThumbnailView> pagedSearch);
}
