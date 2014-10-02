package com.optigra.funnypictures.service.picture;

import com.optigra.funnypictures.model.Picture;
import com.optigra.funnypictures.pagination.PagedResult;
import com.optigra.funnypictures.pagination.PagedSearch;

/**
 * Interface for Service later, that handles all operations.
 * for Picture Entity
 * @author ivanursul
 *
 */
public interface PictureService {

	/**
	 * Method, that retrieves paged result for Picture entity.
	 * @param pagedSearch parameter, that has all parameters for search
	 * @return paged result of Picture entities.This result will include 
	 * pagedSearch.getLimit() entities, starting from pagedSearch.getOffset()
	 * position.
	 */
	PagedResult<Picture> getPictures(PagedSearch<Picture> pagedSearch);

	/**
	 * Method for creating Picture entity.
	 * @param picture entity.
	 * @return The same entity, that comes from parameter + with
	 * generated identity field.
	 */
	Picture createPicture(Picture picture);

	/**
	 * Get's picture by identity.
	 * @param id identity.
	 * @return Picture entity.
	 */
	Picture getPicture(Long id);

	/**
	 * Method for updating picture entity.
	 * @param picture Appropriate entity.
	 */
	void updatePicture(Picture picture);
	
	/**
	 * Method for deleting entity.
	 * @param picture Appropriate entity.
	 */
	void deletePicture(Picture picture);
	
}

