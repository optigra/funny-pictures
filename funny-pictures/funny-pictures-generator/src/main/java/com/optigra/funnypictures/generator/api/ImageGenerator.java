package com.optigra.funnypictures.generator.api;

/**
 * A basic interface for all image generators.
 * @author odisseus
 *
 * @param <C> A type of context object, which contains all inputs necessary to generate the image.
 * 
 */
public interface ImageGenerator<C> {
	
	/**
	 * Generates an image.
	 * @param context input data.
	 * @return a handle to the generated image
	 */
	ImageHandle generate(C context);
	
}
