package com.optigra.funnypictures.service.conversion;

import com.optigra.funnypictures.generator.api.ImageHandle;
import com.optigra.funnypictures.model.content.MimeType;

/**
 * An interface of a service that converts images to a different format.
 * @author odisseus
 *
 */
public interface ImageConversionService {
	
	/**
	 * Converts an image to a given format.
	 * @param source the source image
	 * @param targetFormat the target format
	 * @return the converted image
	 */
	ImageHandle convert(ImageHandle source, MimeType targetFormat);
	
}
