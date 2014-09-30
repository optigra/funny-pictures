package org.funny.pictures.generator.util;

import java.awt.Dimension;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.funny.pictures.generator.api.ImageAccessException;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.process.ArrayListOutputConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Methods of this class never modify images they work with.
 * @author odisseus
 *
 */
public class ImageInformationExtractor {
	
	private static final Logger LOG = LoggerFactory.getLogger(ImageInformationExtractor.class);
	
	private ConvertCmd convertCommand = new ConvertCmd();

	public Dimension getImageDimension(Path image) throws ImageAccessException {

		LOG.debug("Getting image dimension for image path: "
				+ image.toAbsolutePath().toString());

		String separator = " ";
		String resultFormat = "%w" + separator + "%h";

		ArrayListOutputConsumer outputConsumer = new ArrayListOutputConsumer();

		
		convertCommand.setOutputConsumer(outputConsumer);
		IMOperation op = new IMOperation();
		op.addImage();
		op.ping();
		op.format(resultFormat);
		op.addImage("info:");

		LOG.debug("Running command: " + op.toString());
		
		try {
			convertCommand.run(op, image.toString());
		} catch (IOException | InterruptedException | IM4JavaException e) {
			LOG.error("Extraction of image information failed", e);
			throw new ImageAccessException(e.getMessage(), e);
		}

		ArrayList<String> output = outputConsumer.getOutput();

		logCommandOutput(output);

		String[] dimensions = output.get(0).split(separator);
		Dimension result = new Dimension(Integer.valueOf(dimensions[0]),
				Integer.valueOf(dimensions[1]));
		LOG.debug("Calculated dimension: " + result.toString());
		return result;

	}
	
	private void logCommandOutput(List<String> output) {
		StringBuilder logBuilder = new StringBuilder(System.lineSeparator());
		for(String outputRow : output){
			logBuilder.append(outputRow).append(System.lineSeparator());
		}
		LOG.debug("Command output: " + logBuilder.toString());
	}

}
