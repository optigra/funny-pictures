package com.optigra.funnypictures.facade.converter.funny;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.optigra.funnypictures.facade.converter.AbstractConverter;
import com.optigra.funnypictures.facade.converter.Converter;
import com.optigra.funnypictures.facade.resources.picture.FunnyPictureResource;
import com.optigra.funnypictures.facade.resources.picture.PictureResource;
import com.optigra.funnypictures.facade.resources.thumbnail.funny.FunnyPictureThumbnailResource;
import com.optigra.funnypictures.model.FunnyPicture;
import com.optigra.funnypictures.model.Picture;
import com.optigra.funnypictures.model.thumbnail.FunnyPictureThumbnail;

/**
 * Converter from FunnyPicture to FunnyPictureResource.
 * 
 * @author rostyslav
 *
 */
@Component("funnyPictureConverter")
public class FunnyPictureConverter extends AbstractConverter<FunnyPicture, FunnyPictureResource> {

	@Value("${api.domain.content.url}")
	private String contentRootUrl;

	@Resource(name = "pictureConverter")
	private Converter<Picture, PictureResource> pictureConverter;
	
	@Resource(name = "funnyPictureThumbnailConverter")
	private Converter<FunnyPictureThumbnail, FunnyPictureThumbnailResource> funnyPictureThumbnailConverter;

	@Override
	public FunnyPictureResource convert(final FunnyPicture source) {
		return convert(source, new FunnyPictureResource());
	}

	@Override
	public FunnyPictureResource convert(final FunnyPicture source, final FunnyPictureResource target) {

		target.setId(source.getId());
		target.setUrl(getContentRootUrl() + source.getUrl());
		target.setName(source.getName());
		target.setHeader(source.getHeader());
		target.setFooter(source.getFooter());
		target.setTemplate(pictureConverter.convert(source.getPicture()));
		target.setThumbnails(funnyPictureThumbnailConverter.convertAll(source.getThumbnails()));

		return target;
	}

	public String getContentRootUrl() {
		return contentRootUrl;
	}

	public void setContentRootUrl(final String contentRootUrl) {
		this.contentRootUrl = contentRootUrl;
	}
}
