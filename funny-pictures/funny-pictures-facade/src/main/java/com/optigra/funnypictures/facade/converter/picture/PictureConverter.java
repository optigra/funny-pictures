package com.optigra.funnypictures.facade.converter.picture;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.optigra.funnypictures.facade.converter.AbstractConverter;
import com.optigra.funnypictures.facade.resources.picture.PictureResource;
import com.optigra.funnypictures.model.Picture;

@Component("pictureConverter")
public class PictureConverter extends AbstractConverter<Picture, PictureResource> {

	@Override
	public PictureResource convert(Picture source, PictureResource target) {
		Assert.notNull(source);
		
		target.setId(source.getId());
		target.setName(source.getName());
		target.setUrl(source.getUrl());
		
		return target;
	}

	@Override
	public PictureResource convert(Picture source) {
		Assert.notNull(source);
		
		PictureResource result = new PictureResource();

		result = convert(source, result);

		return result;
	}
}
