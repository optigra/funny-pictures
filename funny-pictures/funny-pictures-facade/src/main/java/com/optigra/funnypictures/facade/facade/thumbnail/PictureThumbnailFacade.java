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
	
	PagedResultResource<PictureThumbnailResource> getPicturesThumbnails(PagedRequest<PictureThumbnailResource> pagedRequest);
	
	PictureThumbnailResource getPictureThumbnail(Long id);

}
