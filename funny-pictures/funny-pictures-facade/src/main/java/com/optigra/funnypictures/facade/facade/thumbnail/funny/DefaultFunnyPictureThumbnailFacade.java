package com.optigra.funnypictures.facade.facade.thumbnail.funny;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.optigra.funnypictures.facade.converter.Converter;
import com.optigra.funnypictures.facade.resources.ApiResource;
import com.optigra.funnypictures.facade.resources.search.PagedRequest;
import com.optigra.funnypictures.facade.resources.search.PagedResultResource;
import com.optigra.funnypictures.facade.resources.thumbnail.funny.FunnyPictureThumbnailResource;
import com.optigra.funnypictures.model.thumbnail.FunnyPictureThumbnail;
import com.optigra.funnypictures.pagination.PagedResult;
import com.optigra.funnypictures.pagination.PagedSearch;
import com.optigra.funnypictures.service.thumbnail.funny.FunnyPictureThumbnailService;

/**
 * Default facade for funny pictures thumbnails.
 * 
 * @author oleh.zasadnyy
 *
 */
@Component("funnyPictureThumbnailFacade")
@Transactional
public class DefaultFunnyPictureThumbnailFacade implements
		FunnyPictureThumbnailFacade {

	@Resource(name = "pagedRequestConverter")
	private Converter<PagedRequest, PagedSearch<FunnyPictureThumbnail>> pagedRequestConverter;

	@Resource(name = "pagedSearchConverter")
	private Converter<PagedResult<?>, PagedResultResource<? extends ApiResource>> pagedResultConverter;

	@Resource(name = "funnyPictureThumbnailConverter")
	private Converter<FunnyPictureThumbnail, FunnyPictureThumbnailResource> funnyPictureThumbnailConverter;

	@Resource(name = "funnyPictureThumbnailResourceConverter")
	private Converter<FunnyPictureThumbnailResource, FunnyPictureThumbnail> funnyPictureThumbnailResourceConverter;

	@Resource(name = "funnyPictureThumbnailService")
	private FunnyPictureThumbnailService funnyPictureThumbnailService;

	@Override
	public PagedResultResource<FunnyPictureThumbnailResource> getFunnyPicturesThumbnails(
			final PagedRequest<FunnyPictureThumbnailResource> pagedRequest) {
		// Convert PagedRequest to PagedSearch
		PagedSearch<FunnyPictureThumbnail> pagedSearch = pagedRequestConverter
				.convert(pagedRequest);
		pagedSearch.setEntity(funnyPictureThumbnailResourceConverter
				.convert(pagedRequest.getResource()));

		// Retrieve result pictureService.getPictures(pagedSearch)
		PagedResult<FunnyPictureThumbnail> pagedResult = funnyPictureThumbnailService
				.getFunnyPictureThumbnails(pagedSearch);

		// Convert List<Picture> to List<PictureResource>
		List<FunnyPictureThumbnailResource> resources = funnyPictureThumbnailConverter
				.convertAll(pagedResult.getEntities());

		// Create pagedResultResource
		PagedResultResource<FunnyPictureThumbnailResource> pagedResultResource = new PagedResultResource<>(
				"/funniesthumb");
		pagedResultResource.setEntities(resources);

		// Convert PagedResult to PagedResultResource
		pagedResultConverter.convert(pagedResult, pagedResultResource);
		
		return pagedResultResource;
	}

	@Override
	public FunnyPictureThumbnailResource getFunnyPictureThumbnail(final Long id) {
		FunnyPictureThumbnail funnyPictureThumbnail = funnyPictureThumbnailService
				.getFunnyPictureThumbnail(id);
		return funnyPictureThumbnailConverter.convert(funnyPictureThumbnail);
	}

	@Override
	public PagedResultResource<FunnyPictureThumbnailResource> getFunniesThumbnailForPicture(
			final Long id, final PagedRequest<FunnyPictureThumbnailResource> pagedRequest) {
		
		PagedSearch<FunnyPictureThumbnail> pagedSearch = pagedRequestConverter
				.convert(pagedRequest);
		pagedSearch.setEntity(funnyPictureThumbnailResourceConverter
				.convert(pagedRequest.getResource()));
		
		PagedResult<FunnyPictureThumbnail> pagedResult = funnyPictureThumbnailService.getFunnyPictureThumbnailsByPicture(pagedSearch, id);
		
		List<FunnyPictureThumbnailResource> resources = funnyPictureThumbnailConverter.convertAll(pagedResult.getEntities());
		
		PagedResultResource<FunnyPictureThumbnailResource> pagedResultResource = new PagedResultResource<>("/funnies");
		pagedResultResource.setEntities(resources);
		pagedResultConverter.convert(pagedResult, pagedResultResource);
		
		return pagedResultResource;
	}

}
