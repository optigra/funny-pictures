package org.funny.pictures.generator.api;

/**
 * An interface for generators of advice comics.
 * @author odisseus
 *
 */
public interface AdviceMemeGenerator {
	
	/**
	 * Generates an advice comic.
	 * @param context input data, including template image and caption texts
	 * @return a handle to the generated comic
	 */
	ImageHandle generate(AdviceMemeContext context);
	
}
