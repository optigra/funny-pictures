package com.optigra.funnypictures.facade.facade.thumbnail.funny;

import com.optigra.funnypictures.facade.resources.search.PagedRequest;
import com.optigra.funnypictures.facade.resources.search.PagedResultResource;
import com.optigra.funnypictures.facade.resources.thumbnail.funny.FunnyPictureThumbnailResource;

/**
 * Facade for funny picture.
 * 
 * @author oleh.zasadnyy
 *
 */
public interface FunnyPictureThumbnailFacade {
	
	PagedResultResource<FunnyPictureThumbnailResource> getFunnyPicturesThumbnails(PagedRequest<FunnyPictureThumbnailResource> pagedRequest);
	
	FunnyPictureThumbnailResource getFunnyPictureThumbnail(Long id);

}
