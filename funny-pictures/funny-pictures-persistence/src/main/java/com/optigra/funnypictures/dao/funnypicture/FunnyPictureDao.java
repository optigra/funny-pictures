package com.optigra.funnypictures.dao.funnypicture;

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
	
	/**
	 * Method to get Paged Result of funny pictures by Picture.
	 * 
	 * @param pagedSearch Parameter by which we will find funnies.
	 * @return Paged result of funny pictures.
	 */
	PagedResult<FunnyPicture> getFunnyPicturesByPicture(PagedSearch<FunnyPicture> pagedSearch);

}
