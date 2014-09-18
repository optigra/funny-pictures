package org.funny.pictures.generator.testutil;

import static org.funny.pictures.generator.util.ImageUtils.calculateNormalizedRmseDifference;
import static org.funny.pictures.generator.util.ImageUtils.getImageDimension;
import static org.junit.Assert.assertEquals;

import java.awt.Dimension;
import java.io.IOException;
import java.nio.file.Path;

import org.im4java.core.IM4JavaException;

public class ImageAssert {

	public static void assertImageEquals(Path expected, Path actual)
			throws IOException, InterruptedException, IM4JavaException {
		assertImageEquals("", expected, actual);
	}

	public static void assertImageEquals(String message, Path expected,
			Path actual) throws IOException, InterruptedException,
			IM4JavaException {
		
		Dimension expectedDimension = getImageDimension(expected);
		Dimension actualDimension = getImageDimension(actual);
		assertEquals(message, expectedDimension, actualDimension);
		
		// TODO add more measures (e. g. PSNR)
		double actualRmse = calculateNormalizedRmseDifference(expected,actual);

		assertEquals(0, actualRmse, 0);
	}


}
