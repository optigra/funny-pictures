package com.optigra.funnypictures.generator;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyVararg;
import static org.mockito.Mockito.verify;

import java.awt.Dimension;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;
import org.im4java.process.OutputConsumer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.optigra.funnypictures.generator.BaseAdviceMemeGenerator;
import com.optigra.funnypictures.generator.BaseThumbnailGenerator;
import com.optigra.funnypictures.generator.api.ThumbnailContext;
import com.optigra.funnypictures.model.content.MimeType;

@RunWith(MockitoJUnitRunner.class)
public class BaseThumbnailGeneratorTest {
	
	@InjectMocks
	BaseThumbnailGenerator unit;

	@Mock
	private ConvertCmd convertCommand;

	@Captor
	private ArgumentCaptor<IMOperation> convertOperationCaptor;
	
	@Captor
	private ArgumentCaptor<OutputConsumer> outputConsumerCaptor;
	
	
	@Before
	public void setUp() throws IOException {
		//Mockito doesn't set outputFormat, so we need to set it manually
		unit.setOutputFormat(BaseAdviceMemeGenerator.DEFAULT_OUTPUT_FORMAT);
	}

	@After
	public void tearDown() throws IOException {
		
	}
	
	@Test
	public void testGenerate() throws Exception {
		// Given
		Path templatePath = FileSystems.getDefault().getPath("src", "test", "resources", "templates", "advice-dog.jpg");
		InputStream templateInputStream = new FileInputStream(templatePath.toString());
		ThumbnailContext context = new ThumbnailContext(templateInputStream, MimeType.IMAGE_JPEG_JPG, new Dimension(100, 100));
		
		//When
		unit.generate(context);	
		
		// Then
		verify(convertCommand).run(convertOperationCaptor.capture(), anyVararg());
		String expectedConvertCommand = "?img? -thumbnail 100x100^ -gravity center -extent 100x100 -unsharp 0.0x10.0 ?img? ";
		assertEquals(expectedConvertCommand, convertOperationCaptor.getAllValues().get(0).toString());
	}

}
