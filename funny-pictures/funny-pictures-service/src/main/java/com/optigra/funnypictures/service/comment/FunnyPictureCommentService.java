package com.optigra.funnypictures.service.comment;

import com.optigra.funnypictures.model.comment.FunnyPictureComment;
import com.optigra.funnypictures.pagination.PagedResult;
import com.optigra.funnypictures.pagination.PagedSearch;

/**
 * Funny Picture Comment Service.
 * @author ivanursul
 *
 */
public interface FunnyPictureCommentService {

	/**
	 * Method for creating comment.
	 * @param comment
	 */
	void createComment(FunnyPictureComment comment);

	/**
	 * Methof for getting paged result.
	 * @param pagedSearch
	 * @return paged result.
	 */
	PagedResult<FunnyPictureComment> getComments(PagedSearch<FunnyPictureComment> pagedSearch);

}
