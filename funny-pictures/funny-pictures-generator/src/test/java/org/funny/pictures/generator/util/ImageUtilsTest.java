package org.funny.pictures.generator.util;

import static org.junit.Assert.assertEquals;

import java.nio.file.FileSystems;
import java.nio.file.Path;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@Ignore
@RunWith(JUnit4.class)
public class ImageUtilsTest {
	
	private Path imagesDirectory = FileSystems.getDefault()
			.getPath("src", "test", "resources", 
					"org", "funny", "pictures", "generator", "util", "imageUtilsTest");
	
	@Test
	public void testGetNormalizedRmseDifferenceSameImage() throws Exception {
		Path dog = imagesDirectory.resolve("dog-uncropped.jpg");
		double expectedValue = 0;
		double actualValue = ImageUtils.calculateNormalizedRmseDifference(dog, dog);
		assertEquals(expectedValue, actualValue, 0);
	}
	
	@Test
	public void testGetNormalizedRmseDifferenceSymmetry() throws Exception {
		Path dogA = imagesDirectory.resolve("dog-with-caption-a.png");
		Path dogB = imagesDirectory.resolve("dog-with-caption-b.png");
		double valueAtoB = ImageUtils.calculateNormalizedRmseDifference(dogA, dogB);
		double valueBtoA = ImageUtils.calculateNormalizedRmseDifference(dogB, dogA);
		assertEquals(valueAtoB, valueBtoA, 0);
	}

}
