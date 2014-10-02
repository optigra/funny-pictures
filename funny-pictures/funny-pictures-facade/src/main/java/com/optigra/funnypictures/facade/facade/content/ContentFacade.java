package com.optigra.funnypictures.facade.facade.content;

import com.optigra.funnypictures.facade.resources.content.ContentResource;

/**
 * Facade for content.
 * 
 * @author rostyslav
 *
 */
public interface ContentFacade {
	/**
	 * Method return content by uri.
	 * 
	 * @param uri
	 *            to content storage.
	 * @return ContentResource for given uri.
	 */
	ContentResource getContent(String uri);

	/**
	 * Method saves ContentResource to storage.
	 * 
	 * @param resource
	 *            ContentResource to save
	 * @return saved ContentResource
	 */
	ContentResource storeContent(ContentResource resource);
}
