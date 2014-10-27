package com.optigra.funnypictures.facade.facade.thumbnail;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.optigra.funnypictures.service.thumbnail.ThumbnailService;

/**
 * Default implementation for thumbnail entity in facade layer.
 * @author ivanursul
 *
 */
@Component("thumbnailFacade")
public class DefaultThumbnailFacade implements ThumbnailFacade {

	@Resource(name = "thumbnailService")
	private ThumbnailService thumbnailService;
	
}
