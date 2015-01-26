package com.optigra.funnypictures.facade.converter.thumbnail.picture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.optigra.funnypictures.facade.converter.AbstractConverter;
import com.optigra.funnypictures.facade.resources.thumbnail.PictureThumbnailResource;
import com.optigra.funnypictures.model.thumbnail.PictureThumbnail;

/**
 * Converter, that converts from Entity to Resource.
 * @author ivanursul
 *
 */
@Component("pictureThumbnailConverter")
public class PictureThumbnailConverter extends AbstractConverter<PictureThumbnail, PictureThumbnailResource> {

	@Value("${api.domain.picture.url}")
	private String contentRootUrl;
	
	@Override
	public PictureThumbnailResource convert(final PictureThumbnail source, final PictureThumbnailResource target) {

		target.setId(source.getId());
		target.setPictureId(source.getPicture().getId());
		target.setThumbnailType(source.getThumbnailType());
		target.setUrl(getContentRootUrl() + source.getUrl());
		
		return target;
	}

	@Override
	public PictureThumbnailResource convert(final PictureThumbnail source) {
		return convert(source, new PictureThumbnailResource());
	}
	
	public String getContentRootUrl() {
		return contentRootUrl;
	}

	public void setContentRootUrl(final String contentRootUrl) {
		this.contentRootUrl = contentRootUrl;
	}

}
