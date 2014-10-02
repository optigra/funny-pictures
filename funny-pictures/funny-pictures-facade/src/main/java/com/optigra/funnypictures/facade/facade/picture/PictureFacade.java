package com.optigra.funnypictures.facade.facade.picture;

import com.optigra.funnypictures.facade.resources.picture.PictureResource;
import com.optigra.funnypictures.facade.resources.search.PagedRequest;
import com.optigra.funnypictures.facade.resources.search.PagedResultResource;

/**
 * Facade for picture.
 * 
 * @author rostyslav
 *
 */
public interface PictureFacade {
	/**
	 * Method returns paged result for requested pictures.
	 * 
	 * @param pagedRequest
	 *            request with offset and limit
	 * @return Paged result with picture resource
	 */
	PagedResultResource<PictureResource> getPictures(PagedRequest pagedRequest);

	/**
	 * Method for creating picture.
	 * 
	 * @param pictureResource
	 *            to create Picture
	 * @return created PictureResource
	 */
	PictureResource createPicture(PictureResource pictureResource);

	/**
	 * Method for updating picture.
	 * 
	 * @param id
	 *            picture ID
	 * @param pictureResource
	 *            PictureResource to update
	 */
	void updatePicture(Long id, PictureResource pictureResource);

	/**
	 * Get picture by id.
	 * 
	 * @param id
	 *            picture ID
	 * @return PictureResource
	 */
	PictureResource getPicture(Long id);

	/**
	 * Delete picture by id.
	 * 
	 * @param id
	 *            picture id
	 */
	void deletePicture(Long id);

}
