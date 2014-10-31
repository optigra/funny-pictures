package com.optigra.funnypictures.service.thumbnail;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.funny.pictures.generator.api.ImageHandle;
import org.funny.pictures.generator.api.ThumbnailContext;
import org.funny.pictures.generator.api.ThumbnailGenerator;

import com.optigra.funnypictures.content.model.Content;
import com.optigra.funnypictures.content.model.ThumbnailContent;
import com.optigra.funnypictures.content.service.ContentService;
import com.optigra.funnypictures.model.thumbnail.ThumbnailType;

/**
 * A service for generating sets of thumbnails.
 * @author odisseus
 *
 */
public class DefaultThumbnailGeneratorService implements
		ThumbnailGeneratorService {
	
	@Resource(name = "thumbnailGenerator")
	private ThumbnailGenerator thumbnailGenerator;
	
	@Resource(name = "contentService")
	private ContentService contentService;
	
	@Override
	public List<ThumbnailContent> generateThumbnails(final Content content) {
		List<ThumbnailContent> thumbnails = new ArrayList<ThumbnailContent>();
		for (ThumbnailType thumbnailType : ThumbnailType.values()) {
			ThumbnailContent thumbnail = generateThumbnail(contentService.getContentByPath(content.getPath()), thumbnailType);
			thumbnails.add(thumbnail);
		}
		return thumbnails;
	}

	/**
	 * Generates a single thumbnail.
	 * @param inputContent input image data and metadata
	 * @param thumbnailType type of thumbnail to generate 
	 * @return the generated thumbnail
	 */
	private ThumbnailContent generateThumbnail(final Content inputContent,
			final ThumbnailType thumbnailType) {
		
		ThumbnailContext context = new ThumbnailContext(inputContent.getContentStream(), 
				inputContent.getMimeType(), new Dimension(thumbnailType.getWidth(), thumbnailType.getHeight()));
		
		ImageHandle thumbnailHandle = thumbnailGenerator.generate(context);
		
		ThumbnailContent resultContent = new ThumbnailContent();
		resultContent.setContentStream(thumbnailHandle.getImageInputStream());
		resultContent.setMimeType(thumbnailHandle.getImageFormat());
		resultContent.setThumbnailType(thumbnailType);
		
		return resultContent;
	}

}
