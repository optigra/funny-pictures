package com.optigra.funnypictures.generator.api;

/**
 * An interface for generators that append labels to images.
 * @author odisseus
 *
 */
public interface LabelledImageGenerator {

	/**
	 * Generates a labelled image.
	 * @param context input data, including source image and label text
	 * @return a handle to the generated image
	 */
	ImageHandle generate(ImageLabellingContext context);
	
}
