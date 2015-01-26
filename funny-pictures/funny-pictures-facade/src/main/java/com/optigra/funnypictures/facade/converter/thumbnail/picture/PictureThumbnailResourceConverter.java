package com.optigra.funnypictures.facade.converter.thumbnail.picture;

import org.springframework.stereotype.Component;

import com.optigra.funnypictures.facade.converter.AbstractConverter;
import com.optigra.funnypictures.facade.resources.thumbnail.PictureThumbnailResource;
import com.optigra.funnypictures.model.Picture;
import com.optigra.funnypictures.model.thumbnail.PictureThumbnail;

/**
 * Converter, that converts from Resource to Entity.
 * @author oleh.zasadnyy
 *
 */
@Component("pictureThumbnailResourceConverter")
public class PictureThumbnailResourceConverter extends AbstractConverter<PictureThumbnailResource, PictureThumbnail> {

	@Override
	public PictureThumbnail convert(PictureThumbnailResource source,
			PictureThumbnail target) {
		target.setId(source.getId());
		Picture picture = new Picture();
		picture.setId(source.getPictureId());
		target.setPicture(picture);
		target.setThumbnailType(source.getThumbnailType());
		target.setUrl(source.getUrl());
		
		return target;
	}

	@Override
	public PictureThumbnail convert(PictureThumbnailResource source) {
		return convert(source, new PictureThumbnail());
	}

}
