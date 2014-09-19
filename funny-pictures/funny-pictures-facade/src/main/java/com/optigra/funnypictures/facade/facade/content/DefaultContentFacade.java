package com.optigra.funnypictures.facade.facade.content;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.optigra.funnypictures.content.model.Content;
import com.optigra.funnypictures.content.service.ContentService;
import com.optigra.funnypictures.facade.resources.content.ContentResource;

@Component("contentFacade")
public class DefaultContentFacade implements ContentFacade {

	private static final Logger LOG = LoggerFactory.getLogger(DefaultContentFacade.class);

	@Resource(name = "contentService")
	private ContentService contentService;

	@Override
	public ContentResource getContent(String uri) {
		LOG.info("Get content by uri: {}", uri);
		
		Content content = contentService.getContentByPath(uri);

		// TODO: IP - Implement converter
		ContentResource contentResource = new ContentResource();
		contentResource.setContentStream(content.getContentStream());
		contentResource.setMimeType(content.getMimeType());
		contentResource.setPath(uri);

		return contentResource;
	}
}
