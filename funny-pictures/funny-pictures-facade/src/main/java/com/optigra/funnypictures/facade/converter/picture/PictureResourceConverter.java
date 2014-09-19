package com.optigra.funnypictures.facade.converter.picture;

import org.springframework.stereotype.Component;

import com.optigra.funnypictures.facade.converter.AbstractConverter;
import com.optigra.funnypictures.facade.resources.picture.PictureResource;
import com.optigra.funnypictures.model.Picture;

@Component("pictureResourceConverter")
public class PictureResourceConverter extends AbstractConverter<PictureResource, Picture> {

	@Override
	public Picture convert(PictureResource source, Picture target) {
		target.setId(source.getId());
		target.setName(source.getName());
		target.setUrl(source.getUrl());
		return target;
	}

	@Override
	public Picture convert(PictureResource source) {
		Picture result = new Picture();
		result.setId(source.getId());
		result.setName(source.getName());
		result.setUrl(source.getUrl());
		return result;
	}

}
