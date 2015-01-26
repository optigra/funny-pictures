package com.optigra.funnypictures.facade.facade.thumbnail;

import com.optigra.funnypictures.facade.resources.search.PagedRequest;
import com.optigra.funnypictures.facade.resources.search.PagedResultResource;
import com.optigra.funnypictures.facade.resources.thumbnail.PictureThumbnailResource;

/**
 * Facade for picture.
 * 
 * @author oleh.zasadnyy
 *
 */
public interface PictureThumbnailFacade {
	
	/**
	 * Method returns paged result for requested pictures thumbnails.
	 * 
	 * @param pagedRequest
	 *            request with offset and limit
	 * @return Paged result with picture thumbnail resource
	 */
	PagedResultResource<PictureThumbnailResource> getPicturesThumbnails(PagedRequest<PictureThumbnailResource> pagedRequest);
	
	/**
	 * Get picture thumbnail by identifier.
	 * 
	 * @param id Picture identifier
	 *            funny picture to create.
	 * @return PictureThumbnailResource.
	 */
	PictureThumbnailResource getPictureThumbnail(Long id);

}
