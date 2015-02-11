package com.optigra.funnypictures.facade.facade.content;

import java.io.InputStream;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.optigra.funnypictures.content.model.Content;
import com.optigra.funnypictures.content.service.ContentService;
import com.optigra.funnypictures.facade.resources.content.ContentResource;
import com.optigra.funnypictures.facade.resources.content.ContentResourceNamingStrategy;
import com.optigra.funnypictures.generator.api.ImageHandle;
import com.optigra.funnypictures.model.content.MimeType;
import com.optigra.funnypictures.service.conversion.ImageConversionService;

/**
 * Default implementation of ContentFacade.
 * 
 * @author rostyslav
 *
 */
@Component("contentFacade")
public class DefaultContentFacade implements ContentFacade {
	/**
	 * Logger for DefaultContentFacade.class.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(DefaultContentFacade.class);

	@Resource(name = "contentService")
	private ContentService contentService;

	@Resource(name = "namingStrategy")
	private ContentResourceNamingStrategy namingStrategy;
	
	@Resource(name = "imageConversionService")
	private ImageConversionService imageConversionService;

	@Override
	public ContentResource getContent(final String uri) {
		LOG.info("Get content by uri: {}", uri);

		Content content = contentService.getContentByPath(uri);

		return toContentResource(content);
	}

	/**
	 * Converts Content to ContentResource.
	 * 
	 * @param content
	 *            Content to convert
	 * @return converted Content to ContentResource
	 */
	private ContentResource toContentResource(final Content content) {
		ContentResource contentResource = new ContentResource();
		contentResource.setContentStream(content.getContentStream());
		contentResource.setMimeType(content.getMimeType());
		contentResource.setPath(content.getPath());

		return contentResource;
	}

	@Override
	public ContentResource storeContent(final ContentResource resource) {
		
		ImageHandle incomingImageHandle = new ImageHandle(resource.getContentStream(), resource.getMimeType());
		ImageHandle convertedImageHandle = imageConversionService.convert(incomingImageHandle, MimeType.IMAGE_PNG_PNG);
		
		ContentResource convertedResource = new ContentResource();
		convertedResource.setPath(resource.getPath());
		convertedResource.setContentStream(convertedImageHandle.getImageInputStream());
		convertedResource.setMimeType(convertedImageHandle.getImageFormat());

		String identifier = namingStrategy.createIdentifier(convertedResource);
		LOG.info("Store content with identifier: {}", identifier);

		InputStream contentStream = convertedResource.getContentStream();
		Content content = new Content();
		content.setContentStream(contentStream);
		content.setPath(identifier);
		content.setMimeType(convertedResource.getMimeType());

		contentService.saveContent(content);

		return toContentResource(content);
	}
}
