package com.optigra.funnypictures.generator.api;

/**
 * An interface for generators that annotate images.
 * @author odisseus
 *
 */
public interface AnnotatedImageGenerator {

	/**
	 * Generates an annotated image.
	 * @param context input data, including source image and annotation text
	 * @return a handle to the generated comic
	 */
	ImageHandle generate(ImageAnnotationContext context);
	
}
