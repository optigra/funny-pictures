package com.optigra.funnypictures.facade.facade.comment;

import com.optigra.funnypictures.facade.resources.comment.FunnyPictureCommentResource;
import com.optigra.funnypictures.facade.resources.search.PagedRequest;
import com.optigra.funnypictures.facade.resources.search.PagedResultResource;

/**
 * Funny Picture comment facade.
 * @author ivanursul
 *
 */
public interface FunnyPictureCommentFacade {

	/**
	 * Method for getting comments.
	 * @param pagedRequest
	 * @return paged result.
	 */
	PagedResultResource<FunnyPictureCommentResource> getComments(PagedRequest<FunnyPictureCommentResource> pagedRequest);

	/**
	 * Method for creating funny picture comments.
	 * @param funnyPictureCommentResource
	 * @return comment with generated id.
	 */
	FunnyPictureCommentResource createComment(FunnyPictureCommentResource funnyPictureCommentResource);

}
