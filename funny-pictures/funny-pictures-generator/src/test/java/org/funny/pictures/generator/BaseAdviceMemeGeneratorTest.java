package org.funny.pictures.generator;

import static org.funny.pictures.generator.testutil.ImageAssert.assertImageEquals;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.funny.pictures.generator.api.AdviceMemeContext;
import org.funny.pictures.generator.api.ImageHandle;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.optigra.funnypictures.model.content.MimeType;

@RunWith(JUnit4.class)
public class BaseAdviceMemeGeneratorTest  {
	
	BaseAdviceMemeGenerator unit = new BaseAdviceMemeGenerator();
	
	Path outputImagePath = null;
	
	@Before
	public void setUp() throws IOException {
		outputImagePath = Files.createTempFile("expectedOutput", unit.getOutputFormat().getExtension());
	}
	
	@After
	public void tearDown() throws IOException {
		if(outputImagePath != null){
			Files.deleteIfExists(outputImagePath);
		}
	}
	
	@Test
	public void testGenerate() throws Exception {
		//Given
		Path templatePath = FileSystems.getDefault().getPath
				("src", "test", "resources", "templates", "advice-dog.jpg");
		InputStream templateInputStream = new FileInputStream(templatePath.toString());
		AdviceMemeContext context = new AdviceMemeContext(
				templateInputStream, MimeType.IMAGE_JPEG_JPG, "Top caption", "Bottom caption");
		
		//When
		ImageHandle imgHandle = unit.generate(context);
		Files.copy(imgHandle.getImageInputStream(), outputImagePath, StandardCopyOption.REPLACE_EXISTING);
		
		//Then
		Path expected = FileSystems.getDefault().getPath("src", "test", "resources", 
				"org", "funny", "pictures", "generator", "baseAdviceMemeGeneratorTest", "testGenerate.png");
		assertImageEquals(expected, outputImagePath);
		
	}
	

}
