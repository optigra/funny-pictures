package org.funny.pictures.generator.util;

import java.awt.Dimension;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.funny.pictures.generator.api.ImageAccessException;
import org.im4java.core.CompareCmd;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.process.ArrayListErrorConsumer;
import org.im4java.process.ArrayListOutputConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A class that contains routines for getting metainformation about images.
 * Methods of this class never modify images they work with.
 * @author odisseus
 *
 */
public class ImageInformationExtractor {
	
	private static final Logger LOG = LoggerFactory.getLogger(ImageInformationExtractor.class);
	
	public static final String NULL_OUTPUT = "null:";
	
	public static final String INFO_OUTPUT = "info:";
	
	private ConvertCmd convertCommand = new ConvertCmd();
	
	private CompareCmd compareCommand = new CompareCmd();

	/**
	 * Returns the dimension (width and height) of an image
	 * @param image target image
	 * @return dimension of the image
	 * @throws ImageAccessException
	 */
	public Dimension getImageDimension(final Path image) throws ImageAccessException {

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
		op.addImage(INFO_OUTPUT);

		Object[] arguments = new String[]{image.toString()};
		LOG.debug("Running command: %1$s with arguments: %2$s", op.toString(), Arrays.toString(arguments));
		
		try {
			convertCommand.run(op, arguments);
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
	
	/**
	 * Returns normalized RMSE difference between two images.
	 * Its values are between 0 and 1. Value of 0 corresponds to identical images.
	 * Order of images doesn't matter.
	 * @param imageA image to compare
	 * @param imageB another image to compare
	 * @return normalized RMSE value (between 0 and 1)
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws IM4JavaException
	 * @link 
	 *       https://en.wikipedia.org/wiki/Root-mean-square_deviation#Normalized_root
	 *       -mean-square_deviation
	 */
	public double calculateNormalizedRmseDifference(final Path imageA,
			final Path imageB) throws ImageAccessException {
		
		LOG.debug("Calculating image dimension for image paths \"%1$s\" and \"%2$s\"", 
				imageA.toAbsolutePath(), imageB.toAbsolutePath());
		
		
		/*
		 * For some reason, compare -metric sends its output to stderr.
		 * Therefore, ErrorConsumer instead of OutputConsumer.
		 * 
		 * @link http://stackoverflow.com/questions/7548943
		 * 
		 * @link http://stackoverflow.com/questions/3438673
		 */
		ArrayListErrorConsumer outputConsumer = new ArrayListErrorConsumer();
		compareCommand.setErrorConsumer(outputConsumer);
		IMOperation op = new IMOperation();
		op.quiet();
		op.metric("RMSE");
		op.addImage();
		op.addImage();
		op.addImage(NULL_OUTPUT);
		
		Object[] arguments = new String[]{imageA.toString(), imageB.toString()};
		LOG.debug("Running command: %1$s with arguments: %2$s", op.toString(), Arrays.toString(arguments));
		try {
			compareCommand.run(op, arguments);
		} catch (IOException | InterruptedException | IM4JavaException e) {
			LOG.error("Extraction of image information failed", e);
			throw new ImageAccessException(e.getMessage(), e);
		}

		ArrayList<String> output = outputConsumer.getOutput();
		logCommandOutput(output);

		String metricValue = output.get(output.size() - 1);

		int beginIndex = metricValue.indexOf("(");
		int endIndex = metricValue.lastIndexOf(")");
		String relativeMetricValue = metricValue.substring(beginIndex + 1,
				endIndex);
		double result = Double.valueOf(relativeMetricValue);
		LOG.debug("Calculated normalized RMSE differense: " + result);
		return result;

	}
	
	/**
	 * Logs multiline IM command output with DEBUG level.
	 * @param output
	 */
	private void logCommandOutput(final List<String> output) {
		StringBuilder logBuilder = new StringBuilder(System.lineSeparator());
		for(String outputRow : output){
			logBuilder.append(outputRow).append(System.lineSeparator());
		}
		LOG.debug("Command output: " + logBuilder.toString());
	}

}
