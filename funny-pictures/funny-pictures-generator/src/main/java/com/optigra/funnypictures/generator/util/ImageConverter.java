package com.optigra.funnypictures.generator.util;

import java.io.IOException;
import java.nio.file.Path;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.optigra.funnypictures.generator.api.GeneratorException;
import com.optigra.funnypictures.model.content.MimeType;

/**
 * A tool for converting between image formats.
 * It also adds white background and removes transparency.
 * @author odisseus
 *
 */
public class ImageConverter {
	
	private static final Logger LOG = LoggerFactory.getLogger(ImageInformationExtractor.class);
	
	private ConvertCmd convertCommand = new ConvertCmd();
	
	/**
	 * Converts the source image file to the destination image file. 
	 * The target format is decided from the extension of the destination file.
	 * @param source source file
	 * @param destination destination file
	 */
	public void convert(final Path source, final Path destination) {
		convert(source, destination, "");
	}
	
	/**
	 * Converts the source image file to given format 
	 * and writes the result to destination image file. 
	 * @param source source file
	 * @param destination destination file
	 * @param targetFormat destination format
	 */
	public void convert(final Path source, final Path destination, final MimeType targetFormat) {
		String extension = targetFormat.getExtension();
		String formatPrefix = extension.substring(1) + ":";
		convert(source, destination, formatPrefix);
	}
	
	/**
	 * Converts the source image file to given format.
	 * Format is specified with the format prefix.
	 * @param source source file
	 * @param destination destination file
	 * @param formatPrefix format prefix for ImageMagick
	 */
	private void convert(final Path source, final Path destination, final String formatPrefix) {
		
		LOG.debug("Converting {} to {}", source, destination);

		try {			

			IMOperation op = new IMOperation();
			op.addImage();
			op.background("white");
			op.alpha("remove");
			op.addImage();

			Object[] args = new String[] {source.toString(), formatPrefix + destination.toString()};
			LOG.debug("Running command [%1%s] with arguments %2$s ", op.toString(), args);

			convertCommand.run(op, args);
			
			LOG.debug("Image converted");

		} catch (IOException | IM4JavaException | InterruptedException e) {
			LOG.error("Image conversion failed", e);
			throw new GeneratorException(e.getMessage(), e);
		} 
	}

}
