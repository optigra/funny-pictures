package com.optigra.funnypictures.facade.converter.comments.funny;

import org.springframework.stereotype.Component;

import com.optigra.funnypictures.facade.converter.AbstractConverter;
import com.optigra.funnypictures.facade.resources.comment.FunnyPictureCommentResource;
import com.optigra.funnypictures.model.FunnyPicture;
import com.optigra.funnypictures.model.comment.FunnyPictureComment;

/**
 * Funny Picture Comment Resource Converter.
 * @author ivanursul
 *
 */
@Component("funnyPictureCommentResourceConverter")
public class FunnyPictureCommentResourceConverter extends AbstractConverter<FunnyPictureCommentResource, FunnyPictureComment> {

	@Override
	public FunnyPictureComment convert(final FunnyPictureCommentResource source, final FunnyPictureComment target) {
		
		target.setAuthor(source.getAuthor());
		target.setComment(source.getComment());
		
		if (source.getParentId() != null) {
			FunnyPictureComment parent = new FunnyPictureComment();
			parent.setId(source.getParentId());
			
			target.setParent(parent);
		}
		
		if (source.getFunnyPictureId() != null) {
			FunnyPicture funnyPicture = new FunnyPicture();
			funnyPicture.setId(source.getFunnyPictureId());
			
			target.setFunnyPicture(funnyPicture);
		}
		
		return target;
	}

	@Override
	public FunnyPictureComment convert(final FunnyPictureCommentResource source) {
		return convert(source, new FunnyPictureComment());
	}

}
