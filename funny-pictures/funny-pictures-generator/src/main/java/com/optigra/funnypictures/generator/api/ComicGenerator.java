package com.optigra.funnypictures.generator.api;

/**
 * An interface for generators of free-form comics.
 * @author odisseus
 *
 */
public interface ComicGenerator {
	
	/**
	 * Generates a comic.
	 * @param context input data, including template image, text block positions, sizes and contents.
	 * @return a handle to the generated comic
	 */
	ImageHandle generate(ComicContext context);
	
}
