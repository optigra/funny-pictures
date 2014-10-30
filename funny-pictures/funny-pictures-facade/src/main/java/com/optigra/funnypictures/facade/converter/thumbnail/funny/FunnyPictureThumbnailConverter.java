package com.optigra.funnypictures.facade.converter.thumbnail.funny;

import org.springframework.stereotype.Component;

import com.optigra.funnypictures.facade.converter.AbstractConverter;
import com.optigra.funnypictures.facade.resources.thumbnail.funny.FunnyPictureThumbnailResource;
import com.optigra.funnypictures.model.thumbnail.FunnyPictureThumbnail;

/**
 * Converter for Funny Picture thumbnails.
 * @author ivanursul
 *
 */
@Component("funnyPictureThumbnailConverter")
public class FunnyPictureThumbnailConverter extends AbstractConverter<FunnyPictureThumbnail, FunnyPictureThumbnailResource> {

	@Override
	public FunnyPictureThumbnailResource convert(final FunnyPictureThumbnail source, final FunnyPictureThumbnailResource target) {
		
		target.setFunnyPictureId(source.getFunnyPicture().getId());
		target.setId(source.getId());
		target.setThumbnailType(source.getThumbnailType());
		target.setUrl(source.getUrl());
		
		return target;
	}

	@Override
	public FunnyPictureThumbnailResource convert(final FunnyPictureThumbnail source) {
		return convert(source, new FunnyPictureThumbnailResource());
	}

}
