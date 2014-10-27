package com.optigra.funnypictures.facade.converter.picture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.optigra.funnypictures.facade.converter.AbstractConverter;
import com.optigra.funnypictures.facade.resources.picture.PictureResource;
import com.optigra.funnypictures.model.Picture;

/**
 * Converter from Picture to PictureResource.
 * 
 * @author rostyslav
 *
 */
@Component("pictureConverter")
public class PictureConverter extends AbstractConverter<Picture, PictureResource> {

	@Value("${api.domain.content.url}")
	private String contentRootUrl;

	@Override
	public PictureResource convert(final Picture source, final PictureResource target) {
		Assert.notNull(source);

		target.setId(source.getId());
		target.setName(source.getName());
		target.setUrl(getContentRootUrl() + source.getUrl());

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
