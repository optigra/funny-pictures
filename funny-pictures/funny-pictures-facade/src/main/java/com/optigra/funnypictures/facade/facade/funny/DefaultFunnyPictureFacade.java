package com.optigra.funnypictures.facade.facade.funny;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.optigra.funnypictures.facade.converter.Converter;
import com.optigra.funnypictures.facade.resources.ApiResource;
import com.optigra.funnypictures.facade.resources.picture.FunnyPictureResource;
import com.optigra.funnypictures.facade.resources.search.PagedRequest;
import com.optigra.funnypictures.facade.resources.search.PagedResultResource;
import com.optigra.funnypictures.model.FunnyPicture;
import com.optigra.funnypictures.pagination.PagedResult;
import com.optigra.funnypictures.pagination.PagedSearch;
import com.optigra.funnypictures.service.funnypicture.FunnyPictureService;

@Component("funnyPictureFacade")
public class DefaultFunnyPictureFacade implements FunnyPictureFacade {

	private static final Logger LOG = LoggerFactory.getLogger(DefaultFunnyPictureFacade.class);

	@Resource(name = "funnyPictureService")
	private FunnyPictureService funnyPictureService;

	@Resource(name = "pagedRequestConverter")
	private Converter<PagedRequest, PagedSearch<FunnyPicture>> pagedRequestConverter;

	@Resource(name = "pagedSearchConverter")
	private Converter<PagedResult<?>, PagedResultResource<? extends ApiResource>> pagedResultConverter;

	@Resource(name = "funnyPictureConverter")
	private Converter<FunnyPicture, FunnyPictureResource> funnyPictureConverter;

	@Override
	public PagedResultResource<FunnyPictureResource> getFunnies(PagedRequest pagedRequest) {

		LOG.info("Get funnies by paged request: {}", pagedRequest);

		PagedSearch<FunnyPicture> pagedSearch = pagedRequestConverter.convert(pagedRequest);

		PagedResult<FunnyPicture> pagedResult = funnyPictureService.getFunnyPictures(pagedSearch);

		List<FunnyPictureResource> resources = funnyPictureConverter.convertAll(pagedResult.getEntities());

		PagedResultResource<FunnyPictureResource> pagedResultResource = new PagedResultResource<>("/funnies");
		pagedResultResource.setEntities(resources);
		pagedResultConverter.convert(pagedResult, pagedResultResource);

		return pagedResultResource;
	}

	@Override
	public FunnyPictureResource createFunnyPicture(FunnyPictureResource funny) {
		throw new UnsupportedOperationException();
	}
}
