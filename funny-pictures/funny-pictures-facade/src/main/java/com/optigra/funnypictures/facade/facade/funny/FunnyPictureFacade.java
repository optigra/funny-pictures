package com.optigra.funnypictures.facade.facade.funny;

import com.optigra.funnypictures.facade.resources.picture.FunnyPictureResource;
import com.optigra.funnypictures.facade.resources.search.PagedRequest;
import com.optigra.funnypictures.facade.resources.search.PagedResultResource;

/**
 * Interface for funny picture facade.
 * 
 * @author rostyslav
 *
 */
public interface FunnyPictureFacade {
	/**
	 * Method returns paged result for requested funny pictures.
	 * 
	 * @param pagedRequest
	 *            request with offset and limit
	 * @return Paged result with funny picture resource
	 */
	PagedResultResource<FunnyPictureResource> getFunnies(PagedRequest pagedRequest);

	/**
	 * Method create funny picture.
	 * 
	 * @param funny
	 *            funny picture to create.
	 * @return created funny picture.
	 */
	FunnyPictureResource createFunnyPicture(FunnyPictureResource funny);
	
	/**
	 * Get funny picture by identifier.
	 * 
	 * @param id Funny Picture idetifier
	 *            funny picture to create.
	 * @return FunnyPictureResource.
	 */
	FunnyPictureResource getFunnyPicture(Long id);
	
	/**
	 * Get funnies for picture.
	 * 
	 * @param id Picture identifier.
	 * @param pagedRequest Paged request.
	 * @return Paged result of funnies.
	 */
	PagedResultResource<FunnyPictureResource> getFunniesForPicture(Long id, PagedRequest pagedRequest);
}
