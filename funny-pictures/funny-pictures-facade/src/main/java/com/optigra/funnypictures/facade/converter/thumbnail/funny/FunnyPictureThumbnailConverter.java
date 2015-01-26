package com.optigra.funnypictures.facade.converter.thumbnail.funny;

import org.springframework.beans.factory.annotation.Value;
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

	@Value("${api.domain.meme.url}")
	private String contentRootUrl;
	
	@Override
	public FunnyPictureThumbnailResource convert(final FunnyPictureThumbnail source, final FunnyPictureThumbnailResource target) {
		
		target.setFunnyPictureId(source.getFunnyPicture().getId());
		target.setId(source.getId());
		target.setThumbnailType(source.getThumbnailType());
		target.setUrl(getContentRootUrl() + source.getUrl());
		
		return target;
	}

	@Override
	public FunnyPictureThumbnailResource convert(final FunnyPictureThumbnail source) {
		return convert(source, new FunnyPictureThumbnailResource());
	}
	
	public String getContentRootUrl() {
		return contentRootUrl;
	}

	public void setContentRootUrl(final String contentRootUrl) {
		this.contentRootUrl = contentRootUrl;
	}
}
