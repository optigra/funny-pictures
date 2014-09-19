package org.funny.pictures.generator;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyVararg;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.funny.pictures.generator.api.AdviceMemeContext;
import org.funny.pictures.generator.api.ImageHandle;
import org.im4java.core.CompositeCmd;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.optigra.funnypictures.model.content.MimeType;

@RunWith(MockitoJUnitRunner.class)
public class BaseAdviceMemeGeneratorTest {

	@InjectMocks
	BaseAdviceMemeGenerator unit;

	@Mock
	private ConvertCmd convertCommand;
	
	@Mock
	private CompositeCmd compositeCommand;
	
	@Captor
	private ArgumentCaptor<IMOperation> convertOperationCaptor;
	
	@Captor
	private ArgumentCaptor<IMOperation> compositeOperationCaptor;
	
	Path outputImagePath = null;

	@Before
	public void setUp() throws IOException {
		//Mockito doesn't set outputFormat, so we need to set it manually
		unit.setOutputFormat(BaseAdviceMemeGenerator.DEFAULT_OUTPUT_FORMAT);
		outputImagePath = Files.createTempFile("expectedOutput", unit.getOutputFormat().getExtension());
	}

	@After
	public void tearDown() throws IOException {
		if (outputImagePath != null) {
			Files.deleteIfExists(outputImagePath);
		}
	}

	@Test
	public void testGenerate() throws Exception {
		System.out.println(convertCommand);
		// Given
		Path templatePath = FileSystems.getDefault().getPath("src", "test", "resources", "templates", "advice-dog.jpg");
		InputStream templateInputStream = new FileInputStream(templatePath.toString());
		AdviceMemeContext context = new AdviceMemeContext(templateInputStream, MimeType.IMAGE_JPEG_JPG, "Top caption", "Bottom caption");

		// When
		ImageHandle imgHandle = unit.generate(context);
		Files.copy(imgHandle.getImageInputStream(), outputImagePath, StandardCopyOption.REPLACE_EXISTING);

		// Then
		verify(convertCommand, times(3)).run(convertOperationCaptor.capture(), anyVararg());
		//verify(convertCommand).run(bottomCaptionOperationCaptor.capture(), anyVararg());
		verify(compositeCommand, times(2)).run(compositeOperationCaptor.capture(), anyVararg());
		
		String expectedCropCommand = "?img? -gravity Center -crop 400x400+0+0 +repage ?img? ";
		String expectedConvertTopCommand = "-size 400x50 -background transparent -fill white -stroke black -strokewidth 2 -font Impact-Regular -gravity Center label:Top caption ?img? ";
		String expectedConvertBottomCommand = "-size 400x50 -background transparent -fill white -stroke black -strokewidth 2 -font Impact-Regular -gravity Center label:Bottom caption ?img? ";
		String expectedCompositeTopCommand = "-geometry 400x400+0+0 ?img? ?img? ?img? ";
		String expectedCompositeBottomCommand = "-geometry 400x400+0+350 ?img? ?img? ?img? ";
		
		assertEquals(expectedCropCommand, convertOperationCaptor.getAllValues().get(0).toString());
		assertEquals(expectedConvertTopCommand, convertOperationCaptor.getAllValues().get(1).toString());
		assertEquals(expectedConvertBottomCommand, convertOperationCaptor.getAllValues().get(2).toString());
		assertEquals(expectedCompositeTopCommand, compositeOperationCaptor.getAllValues().get(0).toString());
		assertEquals(expectedCompositeBottomCommand, compositeOperationCaptor.getAllValues().get(1).toString());

	}

}
