package com.optigra.funnypictures.facade.facade.funny;

import com.optigra.funnypictures.facade.resources.picture.FunnyPictureResource;
import com.optigra.funnypictures.facade.resources.search.PagedRequest;
import com.optigra.funnypictures.facade.resources.search.PagedResultResource;

public interface FunnyPictureFacade {

	PagedResultResource<FunnyPictureResource> getFunnies(PagedRequest pagedRequest);

	FunnyPictureResource createFunnyPicture(FunnyPictureResource funny);
}
