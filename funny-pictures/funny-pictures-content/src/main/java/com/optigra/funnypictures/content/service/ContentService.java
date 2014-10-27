package com.optigra.funnypictures.content.service;

import com.optigra.funnypictures.content.model.Content;

/**
 * Interface for Content Service.
 * 
 * @author oyats
 *
 */
public interface ContentService {

	/**
	 * Method, that allows you to save content.
	 * 
	 * @param content
	 *            Input content, that you want to save.
	 */
	void saveContent(Content content);

	/**
	 * Method to get Content by Path.
	 * 
	 * @param path
	 *            Path parameter for getting Content by it.
	 * @return Content by path.
	 */
	Content getContentByPath(String path);

}
