package com.optigra.funnypictures.extractor.comment;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.optigra.funnypictures.dao.comment.FunnyPictureCommentDao;
import com.optigra.funnypictures.dao.funnypicture.FunnyPictureDao;
import com.optigra.funnypictures.extractor.AbstractParametersExtractor;
import com.optigra.funnypictures.model.FunnyPicture;
import com.optigra.funnypictures.model.comment.FunnyPictureComment;

/**
 * Funny picture parameters extractor.
 * @author ivanursul
 *
 */
@Component("funnyPictureParametersExtractor")
public class FunnyPictureCommentParametersExtractor extends AbstractParametersExtractor<FunnyPictureComment> {

	@Resource(name = "funnyPictureDao")
	private FunnyPictureDao funnyPictureDao;

	@Resource(name = "funnyPictureCommentDao")
	private FunnyPictureCommentDao funnyPictureCommentDao;
	
	@Override
	public Map<String, Object> getParameters(final FunnyPictureComment entity) {
		Map<String, Object> parameters = new HashMap<String, Object>();

		if (entity.getFunnyPicture() != null) {
			FunnyPicture dbEntity = funnyPictureDao.findById(entity.getFunnyPicture().getId());
			parameters.put("funnyPicture", dbEntity);
		}
		
		addParameter(entity.getParent(), funnyPictureCommentDao, "parent", parameters);

		addParameter(entity.getComment(), "comment", parameters);
		addParameter(entity.getAuthor(), "author", parameters);
		
		return parameters;
	}

}
