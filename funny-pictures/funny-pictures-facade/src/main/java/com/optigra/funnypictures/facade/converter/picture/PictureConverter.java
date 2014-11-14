package com.optigra.funnypictures.facade.converter.picture;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.optigra.funnypictures.facade.converter.AbstractConverter;
import com.optigra.funnypictures.facade.converter.Converter;
import com.optigra.funnypictures.facade.resources.picture.PictureResource;
import com.optigra.funnypictures.facade.resources.thumbnail.PictureThumbnailResource;
import com.optigra.funnypictures.model.Picture;
import com.optigra.funnypictures.model.thumbnail.PictureThumbnail;

/**
 * Converter from Picture to PictureResource.
 * 
 * @author rostyslav
 *
 */
@Component("pictureConverter")
public class PictureConverter extends AbstractConverter<Picture, PictureResource> {

	@Value("${api.domain.picture.url}")
	private String contentRootUrl;

	@Resource(name = "pictureThumbnailConverter")
	private Converter<PictureThumbnail, PictureThumbnailResource> pictureThumbnailConverter;
	
	@Override
	public PictureResource convert(final Picture source, final PictureResource target) {
		Assert.notNull(source);

		target.setId(source.getId());
		target.setName(source.getName());
		target.setUrl(getContentRootUrl() + source.getUrl());
		target.setThumbnails(pictureThumbnailConverter.convertAll(source.getThumbnails()));

		return target;
	}

	@Override
	public PictureResource convert(final Picture source) {
		Assert.notNull(source);

		PictureResource result = new PictureResource();

		result = convert(source, result);

		return result;
	}

	public String getContentRootUrl() {
		return contentRootUrl;
	}

	public void setContentRootUrl(final String contentRootUrl) {
		this.contentRootUrl = contentRootUrl;
	}
}
