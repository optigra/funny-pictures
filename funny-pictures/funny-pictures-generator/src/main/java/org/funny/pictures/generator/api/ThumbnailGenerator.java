package org.funny.pictures.generator.api;

/**
 * An interface for generators of image thumbnails.
 * @author odisseus
 *
 */
public interface ThumbnailGenerator {
	
	/**
	 * Generates a thumbnail.
	 * @param context input data, including source image and required thumbnail size
	 * @return a handle to the generated thumbnail
	 */
	ImageHandle generate(ThumbnailContext context);

}
