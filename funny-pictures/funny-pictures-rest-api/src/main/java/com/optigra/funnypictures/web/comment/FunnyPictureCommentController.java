package com.optigra.funnypictures.web.comment;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.optigra.funnypictures.facade.facade.comment.FunnyPictureCommentFacade;
import com.optigra.funnypictures.facade.resources.comment.FunnyPictureCommentResource;
import com.optigra.funnypictures.facade.resources.search.PagedRequest;
import com.optigra.funnypictures.facade.resources.search.PagedResultResource;
import com.optigra.funnypictures.web.BaseController;

/**
 * Comments controller.
 * @author ivanursul
 *
 */
@RestController
@RequestMapping("/comments/funnies")
public class FunnyPictureCommentController extends BaseController {
	private static final Logger LOG = LoggerFactory.getLogger(FunnyPictureCommentController.class);
	
	@Resource(name = "funnyPictureCommentFacade")
	private FunnyPictureCommentFacade funnyPictureCommentFacade;

	/**
	 * Method for getting comments.
	 * @param offset
	 * @param limit
	 * @param resource
	 * @return paged result.
	 */
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method = RequestMethod.GET)
	public PagedResultResource<FunnyPictureCommentResource> getFunnies(@RequestParam(value = "offset", defaultValue = "0") final Integer offset,
			@RequestParam(value = "limit", defaultValue = "20") final Integer limit,
			final FunnyPictureCommentResource resource) {

		LOG.info("Get funny comments: offset [{}] limit [{}] ", offset, limit);

		PagedRequest<FunnyPictureCommentResource> pagedRequest = new PagedRequest<FunnyPictureCommentResource>(resource, offset, limit);

		return funnyPictureCommentFacade.getComments(pagedRequest);
	}

	/**
	 * Method for creating comment.
	 * @param funnyPictureCommentResource
	 * @return resource.
	 */
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(method = RequestMethod.POST)
	public FunnyPictureCommentResource createFunny(@RequestBody final FunnyPictureCommentResource funnyPictureCommentResource) {
		LOG.info("Create funny picture for {}", funnyPictureCommentResource);

		return funnyPictureCommentFacade.createComment(funnyPictureCommentResource);
	}	
}
