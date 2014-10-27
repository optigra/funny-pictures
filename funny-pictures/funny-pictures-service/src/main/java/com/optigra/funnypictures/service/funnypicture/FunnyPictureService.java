package com.optigra.funnypictures.service.funnypicture;

import com.optigra.funnypictures.model.FunnyPicture;
import com.optigra.funnypictures.pagination.PagedResult;
import com.optigra.funnypictures.pagination.PagedSearch;

/**
 * Interface for Service later, that handles all operations.
 * for FunnyPicture Entity
 * @author ivanursul
 *
 */
public interface FunnyPictureService {

	/**
	 * Method for getting funny pictures with pagination.
	 * @param pagedSearch parameter, that has all parameters for search
	 * @return paged result of Funny Pictures with
	 * count, limit, offset, and appropriate entities.
	 */
	PagedResult<FunnyPicture> getFunnyPictures(PagedSearch<FunnyPicture> pagedSearch);

	/**
	 * Method for creating  funny picture entity.
	 * @param funnyPicture
	 * @return The same Funny Picture, that comes from parameters + with
	 * generated identity field.
	 */
	FunnyPicture createFunnyPicture(FunnyPicture funnyPicture);

	/**
	 * Get's funny picture by identifier.
	 * @param id
	 * @return Funny Picture entity.
	 */
	FunnyPicture getFunnyPicture(Long id);
	
}
