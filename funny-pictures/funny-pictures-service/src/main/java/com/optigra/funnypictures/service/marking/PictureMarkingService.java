package com.optigra.funnypictures.service.marking;

import com.optigra.funnypictures.content.model.Content;

/**
 * An interface for a picture marking service.
 * Its purpose is putting a certain mark on the picture, like an annotation or a watermark.
 * 
 * @author odisseus
 *
 */
public interface PictureMarkingService {

	/**
	 * Generates a marked copy of the source picture.
	 * @param source the source picture
	 * @return the marked picture
	 */
	Content generateMarkedPicture(Content source);
	
}
