package org.funny.pictures.generator.util;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

import org.im4java.core.CompareCmd;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.process.ArrayListErrorConsumer;
import org.im4java.process.ArrayListOutputConsumer;

public class ImageUtils {
	
	public static final String NULL_OUTPUT = "null:";
	
	public static int[] getImageDimensions(Path image) throws IOException, InterruptedException, IM4JavaException{
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
		cmd.run(op);
		
		ArrayList<String> output = outputConsumer.getOutput();			
		String[] dimensions = output.get(0).split(separator);
		return new int[]{
				Integer.valueOf(dimensions[0]),
				Integer.valueOf(dimensions[1]),
		};
		
	}
	
	/**
	 * 
	 * @param image
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws IM4JavaException
	 * @link https://en.wikipedia.org/wiki/Root-mean-square_deviation#Normalized_root-mean-square_deviation
	 */
	public static double calculateNormalizedRmseDifference(Path imageA, Path imageB) throws IOException, InterruptedException, IM4JavaException{
		CompareCmd cmd = new CompareCmd();
		/*
		 * For some reason, compare -metric sends its output to stderr.
		 * Therefore, ErrorConsumer instead of OutputConsumer.
		 * @link http://stackoverflow.com/questions/7548943
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
		cmd.run(op);
		
		ArrayList<String> output = outputConsumer.getOutput();		
		
		String metricValue = output.get(output.size()-1);
		
		int beginIndex = metricValue.indexOf("(");
		int endIndex = metricValue.lastIndexOf(")");
		String relativeMetricValue = metricValue.substring(beginIndex+1, endIndex);
		return Double.valueOf(relativeMetricValue);
		
	}

}
