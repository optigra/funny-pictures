package com.optigra.funnypictures.dao.funntypicture;

import com.optigra.funnypictures.dao.Dao;
import com.optigra.funnypictures.model.FunnyPicture;
import com.optigra.funnypictures.pagination.PagedResult;
import com.optigra.funnypictures.pagination.PagedSearch;

/**
 * DAO interface with the method to get PagedResult with FunnyPicture type.
 * 
 * @author oyats
 *
 */
public interface FunnyPictureDao extends Dao<FunnyPicture, Long> {

	/**
	 * Method to get the PagedResult with type of FunnyPictures.
	 * 
	 * @param pagedSearch
	 *            PagedSearch parameter.
	 * @return PagedResult.
	 */
	PagedResult<FunnyPicture> getFunnyPictures(PagedSearch<FunnyPicture> pagedSearch);

}
