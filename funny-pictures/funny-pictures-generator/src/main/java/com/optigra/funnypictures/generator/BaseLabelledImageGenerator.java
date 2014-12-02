package com.optigra.funnypictures.generator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.optigra.funnypictures.generator.api.LabelledImageGenerator;
import com.optigra.funnypictures.generator.api.ImageLabellingContext;
import com.optigra.funnypictures.generator.api.ImageHandle;

/**
 * Generator of labelled images.
 * @author odisseus
 *
 */
public class BaseLabelledImageGenerator implements LabelledImageGenerator {
	
	private static final Logger LOG = LoggerFactory.getLogger(BaseAdviceMemeGenerator.class);
	
	private static final String labelBackgroundColor = "khaki";

	@Override
	public ImageHandle generate(final ImageLabellingContext context) {
		// TODO Auto-generated method stub
		return null;
	}

}
