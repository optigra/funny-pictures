package com.optigra.funnypictures.generator;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyVararg;
import static org.mockito.Mockito.verify;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;
import org.im4java.process.OutputConsumer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.optigra.funnypictures.generator.api.ImageLabellingContext;
import com.optigra.funnypictures.model.content.MimeType;

@RunWith(MockitoJUnitRunner.class)
public class BaseLabelledImageGeneratorTest {
	
	@InjectMocks
	private BaseLabelledImageGenerator unit;
	
	@Mock
	private ConvertCmd convertCommand;

	@Captor
	private ArgumentCaptor<IMOperation> convertOperationCaptor;
	
	@Captor
	private ArgumentCaptor<OutputConsumer> outputConsumerCaptor;

	@Test
	public void testGenerate() throws Exception {
		// Given
		Path templatePath = FileSystems.getDefault().getPath("src", "test", "resources", "templates", "advice-dog.jpg");
		InputStream templateInputStream = new FileInputStream(templatePath.toString());
		ImageLabellingContext context = new ImageLabellingContext(templateInputStream,
				MimeType.IMAGE_JPEG_JPG, "Label text");

		// When
		unit.generate(context);

		// Then
		verify(convertCommand).run(convertOperationCaptor.capture(),
				anyVararg());
		String expectedConvertCommand = "?img? -background khaki label:Label text -gravity east -append ?img? ";
		assertEquals(expectedConvertCommand, convertOperationCaptor
				.getAllValues().get(0).toString());
	}


}
