package org.funny.pictures.generator.testutil;

import static org.funny.pictures.generator.util.ImageUtils.*;
import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Path;

import org.im4java.core.IM4JavaException;
import org.junit.ComparisonFailure;
import org.junit.internal.ArrayComparisonFailure;

public class ImageAssert {
	
	public static void assertImageEquals(Path expected, Path actual) throws IOException, InterruptedException, IM4JavaException {
		assertImageEquals(null, expected, actual);
		
		//TODO add more measures (e. g. PSNR)
		double expectedRmse = calculateNormalizedRmseDifference(expected, actual);
		
		assertEquals(expectedRmse, 0, 0);
	}

	public static void assertImageEquals(String message, Path expected, Path actual) throws IOException, InterruptedException, IM4JavaException {
		int[] expectedDimensions = getImageDimensions(expected);
		assertImageDimensionsEquals(message, expectedDimensions, actual);
	}
	
	public static void assertImageDimensionsEquals(int[] expecteds, Path actual) throws IOException, InterruptedException, IM4JavaException {
		assertImageDimensionsEquals(null, expecteds, actual);
	}
	
	public static void assertImageDimensionsEquals(String message, int[] expecteds, Path actual) throws IOException, InterruptedException, IM4JavaException {
		String separator = "x";
		int[] actuals = getImageDimensions(actual);
		try{
			org.junit.Assert.assertArrayEquals(expecteds, actuals);			
		}catch(ArrayComparisonFailure e){
			String expectedAsString = expecteds[0] + separator + expecteds[1];
			String actualAsString = actuals[0] + separator + actuals[1];
			throw new ComparisonFailure("Image dimensions comparison failure: " + message, expectedAsString, actualAsString);
		}
	}	
	
}
