package org.funny.pictures.generator.util;

import java.awt.Dimension;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.im4java.core.CompareCmd;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.process.ArrayListErrorConsumer;
import org.im4java.process.ArrayListOutputConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageUtils {

	public static final String NULL_OUTPUT = "null:";

	private static final Logger LOG = LoggerFactory.getLogger(ImageUtils.class);
	
	public static Dimension getImageDimension(Path image) throws IOException,
			InterruptedException, IM4JavaException {
		
		LOG.debug("Getting image dimension for image path: " + image.toAbsolutePath().toString());
		
		String separator = " ";
		String resultFormat = "%w" + separator + "%h";

		ConvertCmd cmd = new ConvertCmd();
		ArrayListOutputConsumer outputConsumer = new ArrayListOutputConsumer();
		cmd.setOutputConsumer(outputConsumer);
		IMOperation op = new IMOperation();
		op.addImage(image.toString());
		op.ping();
		op.format(resultFormat);
		op.addImage("info:");
		LOG.debug("Running command: " + op.toString());
		cmd.run(op);

		ArrayList<String> output = outputConsumer.getOutput();
		
		logCommandOutput(output);
		
		String[] dimensions = output.get(0).split(separator);
		Dimension result = new Dimension(Integer.valueOf(dimensions[0]),Integer.valueOf(dimensions[1]));
		LOG.debug("Calculated dimension: " + result.toString());
		return result;

	}

	/**
	 * 
	 * @param image
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws IM4JavaException
	 * @link 
	 *       https://en.wikipedia.org/wiki/Root-mean-square_deviation#Normalized_root
	 *       -mean-square_deviation
	 */
	public static double calculateNormalizedRmseDifference(Path imageA,
			Path imageB) throws IOException, InterruptedException,
			IM4JavaException {
		
		LOG.debug("Calculating image dimension for image paths \"%1$s\" and \"%2$s\"", 
				imageA.toAbsolutePath(), imageB.toAbsolutePath());
		
		CompareCmd cmd = new CompareCmd();
		/*
		 * For some reason, compare -metric sends its output to stderr.
		 * Therefore, ErrorConsumer instead of OutputConsumer.
		 * 
		 * @link http://stackoverflow.com/questions/7548943
		 * 
		 * @link http://stackoverflow.com/questions/3438673
		 */
		ArrayListErrorConsumer outputConsumer = new ArrayListErrorConsumer();
		cmd.setErrorConsumer(outputConsumer);
		IMOperation op = new IMOperation();
		op.quiet();
		op.metric("RMSE");
		op.addImage(imageA.toString());
		op.addImage(imageB.toString());
		op.addImage("null:");
		LOG.debug("Running command: " + op.toString());
		cmd.run(op);

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
	
	private static void logCommandOutput(List<String> output) {
		StringBuilder logBuilder = new StringBuilder(System.lineSeparator());
		for(String outputRow : output){
			logBuilder.append(outputRow).append(System.lineSeparator());
		}
		LOG.debug("Command output: " + logBuilder.toString());
	}

}
