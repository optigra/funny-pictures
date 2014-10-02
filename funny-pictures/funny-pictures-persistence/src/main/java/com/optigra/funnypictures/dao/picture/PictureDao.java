package com.optigra.funnypictures.dao.picture;

import com.optigra.funnypictures.dao.Dao;
import com.optigra.funnypictures.model.Picture;
import com.optigra.funnypictures.pagination.PagedResult;
import com.optigra.funnypictures.pagination.PagedSearch;

/**
 * PictureDao interface with method for getting pictures.
 * 
 * @author oyats
 *
 */
public interface PictureDao extends Dao<Picture, Long> {

	/**
	 * Method for getting pictures.
	 * 
	 * @param pagedSearch
	 *            PagedSearch object with pagination, entities, queries and
	 *            parameters.
	 * @return PagedResult object with entity and pagination.
	 */
	PagedResult<Picture> getPictures(PagedSearch<Picture> pagedSearch);

}
