package com.optigra.funnypictures.facade.converter.funny;

import org.springframework.stereotype.Component;

import com.optigra.funnypictures.facade.converter.AbstractConverter;
import com.optigra.funnypictures.facade.resources.picture.FunnyPictureResource;
import com.optigra.funnypictures.model.FunnyPicture;

@Component("funnyPictureConverter")
public class FunnyPictureConverter extends AbstractConverter<FunnyPicture, FunnyPictureResource> {
	
	@Override
	public FunnyPictureResource convert(FunnyPicture source) {
		return convert(source, new FunnyPictureResource());
	}
	
	@Override
	public FunnyPictureResource convert(FunnyPicture source, FunnyPictureResource target) {
		
		target.setId(source.getId());
		target.setUrl(source.getUrl());
		target.setName(source.getName());
		target.setHeader(source.getHeader());
		target.setFooter(source.getFooter());
		
		return target;
	}
}
