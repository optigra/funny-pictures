package com.optigra.funnypictures.facade.converter.thumbnail.picture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.optigra.funnypictures.facade.converter.AbstractConverter;
import com.optigra.funnypictures.facade.resources.thumbnail.PictureThumbnailResource;
import com.optigra.funnypictures.view.thumbnail.RandomPictureThumbnailView;

/**
 * Converter, that converts from RandomPictureThumbnailView to PictureThumbnailResource.
 * @author oleh.zasadnyy
 *
 */
@Component("randomPictureThumbnailConverter")
public class RandomPictureThumbnailConverter extends AbstractConverter<RandomPictureThumbnailView, PictureThumbnailResource>  {
	
	@Value("${api.domain.picture.url}")
	private String contentRootUrl;
	
	@Override
	public PictureThumbnailResource convert(final RandomPictureThumbnailView source,
			final PictureThumbnailResource target) {
		
		target.setId(source.getId());
		target.setPictureId(source.getPictureId());
		target.setThumbnailType(source.getThumbnailType());
		target.setUrl(getContentRootUrl() + source.getUrl());
		
		return target;
	}

	@Override
	public PictureThumbnailResource convert(final RandomPictureThumbnailView source) {
		return convert(source, new PictureThumbnailResource());
	}
	
	public String getContentRootUrl() {
		return contentRootUrl;
	}

	public void setContentRootUrl(final String contentRootUrl) {
		this.contentRootUrl = contentRootUrl;
	}
}
