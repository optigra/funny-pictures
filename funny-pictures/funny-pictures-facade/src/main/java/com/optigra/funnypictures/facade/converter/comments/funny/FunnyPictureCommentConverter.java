package com.optigra.funnypictures.facade.converter.comments.funny;

import org.springframework.stereotype.Component;

import com.optigra.funnypictures.facade.converter.AbstractConverter;
import com.optigra.funnypictures.facade.resources.comment.FunnyPictureCommentResource;
import com.optigra.funnypictures.model.comment.FunnyPictureComment;
/**
 * Funny Picture Comment Converter.
 * @author ivanursul
 *
 */
@Component("funnyPictureCommentConverter")
public class FunnyPictureCommentConverter extends AbstractConverter<FunnyPictureComment, FunnyPictureCommentResource> {

	@Override
	public FunnyPictureCommentResource convert(final FunnyPictureComment source, final FunnyPictureCommentResource target) {
		
		target.setAuthor(source.getAuthor());
		target.setComment(source.getComment());
		target.setId(source.getId());
		
		if (source.getParent() != null) {
			target.setParentId(source.getParent().getId());
		}
		
		if (source.getFunnyPicture() != null) {
			target.setFunnyPictureId(source.getFunnyPicture().getId());
		}
		
		return target;
	}

	@Override
	public FunnyPictureCommentResource convert(final FunnyPictureComment source) {
		return convert(source, new FunnyPictureCommentResource());
	}

}
