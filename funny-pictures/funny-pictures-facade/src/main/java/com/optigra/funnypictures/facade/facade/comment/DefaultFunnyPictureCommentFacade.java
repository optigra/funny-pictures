package com.optigra.funnypictures.facade.facade.comment;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.optigra.funnypictures.facade.converter.Converter;
import com.optigra.funnypictures.facade.resources.ApiResource;
import com.optigra.funnypictures.facade.resources.comment.FunnyPictureCommentResource;
import com.optigra.funnypictures.facade.resources.search.PagedRequest;
import com.optigra.funnypictures.facade.resources.search.PagedResultResource;
import com.optigra.funnypictures.model.comment.FunnyPictureComment;
import com.optigra.funnypictures.pagination.PagedResult;
import com.optigra.funnypictures.pagination.PagedSearch;
import com.optigra.funnypictures.service.comment.FunnyPictureCommentService;

/**
 * Funny Picture Comment facade.
 * @author ivanursul
 *
 */
@Transactional
@Component("funnyPictureCommentFacade")
public class DefaultFunnyPictureCommentFacade implements FunnyPictureCommentFacade {
	private static final Logger LOG = LoggerFactory.getLogger(DefaultFunnyPictureCommentFacade.class);

	@Resource(name = "funnyPictureCommentService")
	private FunnyPictureCommentService funnyPictureCommentService;

	@Resource(name = "pagedRequestConverter")
	private Converter<PagedRequest<FunnyPictureCommentResource>, PagedSearch<FunnyPictureComment>> pagedRequestConverter;

	@Resource(name = "pagedSearchConverter")
	private Converter<PagedResult<?>, PagedResultResource<? extends ApiResource>> pagedResultConverter;

	@Resource(name = "funnyPictureCommentConverter")
	private Converter<FunnyPictureComment, FunnyPictureCommentResource> funnyPictureCommentConverter;

	@Resource(name = "funnyPictureCommentResourceConverter")
	private Converter<FunnyPictureCommentResource, FunnyPictureComment> funnyPictureCommentResourceConverter;
	
	@Resource(name = "insertConverter")
	private Converter<FunnyPictureCommentResource, FunnyPictureComment> insertConverter;

	@Resource(name = "updateConverter")
	private Converter<FunnyPictureCommentResource, FunnyPictureComment> updateConverter;
	
	@Override
	public FunnyPictureCommentResource createComment(final FunnyPictureCommentResource funnyPictureCommentResource) {
		LOG.info("Creating funny picture comment: {}", funnyPictureCommentResource);
		
		FunnyPictureComment comment =  new FunnyPictureComment();
		
		funnyPictureCommentResourceConverter.convert(funnyPictureCommentResource, comment);
		insertConverter.convert(funnyPictureCommentResource, comment);
		
		funnyPictureCommentService.createComment(comment);
		
		return funnyPictureCommentConverter.convert(comment);
	}

	@Override
	public PagedResultResource<FunnyPictureCommentResource> getComments(final PagedRequest<FunnyPictureCommentResource> pagedRequest) {
		LOG.info("Getting paged search: {}", pagedRequest);
		
		PagedSearch<FunnyPictureComment> pagedSearch = pagedRequestConverter.convert(pagedRequest);
		pagedSearch.setEntity(funnyPictureCommentResourceConverter.convert(pagedRequest.getResource()));
		
		PagedResult<FunnyPictureComment> pagedResult = funnyPictureCommentService.getComments(pagedSearch);
		
		List<FunnyPictureCommentResource> resources = funnyPictureCommentConverter.convertAll(pagedResult.getEntities());
		
		PagedResultResource<FunnyPictureCommentResource> pagedResultResource = 
				new PagedResultResource<FunnyPictureCommentResource>("/comments/funnies");
		pagedResultResource.setEntities(resources);
		pagedResultConverter.convert(pagedResult, pagedResultResource);
		
		return pagedResultResource;
	}
	
}
