package com.optigra.funnypictures.service.comment;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.optigra.funnypictures.dao.comment.FunnyPictureCommentDao;
import com.optigra.funnypictures.extractor.ParametersExtractor;
import com.optigra.funnypictures.model.comment.FunnyPictureComment;
import com.optigra.funnypictures.pagination.PagedResult;
import com.optigra.funnypictures.pagination.PagedSearch;

/**
 * Default funny picture comments service.
 * @author ivanursul
 *
 */
@Service("funnyPictureCommentService")
public class DefaultFunnyPictureCommentService implements FunnyPictureCommentService {

	@Resource(name = "funnyPictureParametersExtractor")
	private ParametersExtractor<FunnyPictureComment> parametersExtractor;
	
	@Resource(name = "funnyPictureCommentDao")
	private FunnyPictureCommentDao funnyPictureCommentDao;
	
	@Override
	public void createComment(final FunnyPictureComment comment) {
		funnyPictureCommentDao.save(comment);
	}

	@Override
	public PagedResult<FunnyPictureComment> getComments(final PagedSearch<FunnyPictureComment> pagedSearch) {
		Map<String, Object> parameters = parametersExtractor.getParameters(pagedSearch.getEntity());
		pagedSearch.setParameters(parameters);
		
		return funnyPictureCommentDao.getEntities(pagedSearch);
	}

}
