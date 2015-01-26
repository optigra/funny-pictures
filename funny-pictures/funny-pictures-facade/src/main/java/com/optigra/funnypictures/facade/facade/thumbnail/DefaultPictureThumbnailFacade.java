package com.optigra.funnypictures.facade.facade.thumbnail;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.optigra.funnypictures.facade.converter.Converter;
import com.optigra.funnypictures.facade.resources.ApiResource;
import com.optigra.funnypictures.facade.resources.search.PagedRequest;
import com.optigra.funnypictures.facade.resources.search.PagedResultResource;
import com.optigra.funnypictures.facade.resources.thumbnail.PictureThumbnailResource;
import com.optigra.funnypictures.model.thumbnail.PictureThumbnail;
import com.optigra.funnypictures.pagination.PagedResult;
import com.optigra.funnypictures.pagination.PagedSearch;
import com.optigra.funnypictures.service.thumbnail.PictureThumbnailService;

/**
 * Default implementation of ThumbnailFacade.
 * 
 * @author oleh.zasadnyy
 *
 */
@Component("pictureThumbnailFacade")
@Transactional
public class DefaultPictureThumbnailFacade implements PictureThumbnailFacade {

	@Resource(name = "pagedRequestConverter")
	private Converter<PagedRequest, PagedSearch<PictureThumbnail>> pagedRequestConverter;

	@Resource(name = "pagedSearchConverter")
	private Converter<PagedResult<?>, PagedResultResource<? extends ApiResource>> pagedResultConverter;

	@Resource(name = "pictureThumbnailConverter")
	private Converter<PictureThumbnail, PictureThumbnailResource> pictureThumbnailConverter;

	@Resource(name = "pictureThumbnailResourceConverter")
	private Converter<PictureThumbnailResource, PictureThumbnail> pictureThumbnailResourceConverter;
	
	@Resource(name = "pictureThumbnailService")
	private PictureThumbnailService pictureThumbnailService;

	@Override
	public PagedResultResource<PictureThumbnailResource> getPicturesThumbnails(
			final PagedRequest<PictureThumbnailResource> pagedRequest) {
		// Convert PagedRequest to PagedSearch
		PagedSearch<PictureThumbnail> pagedSearch = pagedRequestConverter
				.convert(pagedRequest);
		pagedSearch.setEntity(pictureThumbnailResourceConverter.convert(pagedRequest.getResource()));

		// Retrieve result pictureService.getPictures(pagedSearch)
		PagedResult<PictureThumbnail> pagedResult = pictureThumbnailService
				.getPictureThumbnails(pagedSearch);

		// Convert List<Picture> to List<PictureResource>
		List<PictureThumbnailResource> resources = pictureThumbnailConverter
				.convertAll(pagedResult.getEntities());

		// Create pagedResultResource
		PagedResultResource<PictureThumbnailResource> pagedResultResource = new PagedResultResource<>(
				"/picturesthumb");
		pagedResultResource.setEntities(resources);

		// Convert PagedResult to PagedResultResource
		pagedResultConverter.convert(pagedResult, pagedResultResource);

		return pagedResultResource;
	}

	@Override
	public PictureThumbnailResource getPictureThumbnail(final Long id) {
		PictureThumbnail pictureThumbnail = pictureThumbnailService
				.getPictureThumbnail(id);
		return pictureThumbnailConverter.convert(pictureThumbnail);
	}

}
