package com.optigra.funnypictures.facade.converter.thumbnail.funny;

import org.springframework.stereotype.Component;

import com.optigra.funnypictures.facade.converter.AbstractConverter;
import com.optigra.funnypictures.facade.resources.thumbnail.funny.FunnyPictureThumbnailResource;
import com.optigra.funnypictures.model.FunnyPicture;
import com.optigra.funnypictures.model.thumbnail.FunnyPictureThumbnail;

/**
 * Converter, that converts from Resource to Entity.
 * @author oleh.zasadnyy
 *
 */
@Component("funnyPictureThumbnailResourceConverter")
public class FunnyPictureThumbnailResourceConverter extends AbstractConverter<FunnyPictureThumbnailResource, FunnyPictureThumbnail> {

	@Override
	public FunnyPictureThumbnail convert(final FunnyPictureThumbnailResource source,
			final FunnyPictureThumbnail target) {
		target.setId(source.getId());
		FunnyPicture funnyPicture = new FunnyPicture();
		funnyPicture.setId(source.getFunnyPictureId());
		target.setFunnyPicture(funnyPicture);
		target.setThumbnailType(source.getThumbnailType());
		target.setUrl(source.getUrl());
		
		return target;
	}

	@Override
	public FunnyPictureThumbnail convert(final FunnyPictureThumbnailResource source) {
		return convert(source, new FunnyPictureThumbnail());
	}

}
