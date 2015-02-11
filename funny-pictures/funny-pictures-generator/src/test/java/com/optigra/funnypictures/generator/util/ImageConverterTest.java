package com.optigra.funnypictures.generator.util;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyVararg;
import static org.mockito.Mockito.verify;

import java.nio.file.FileSystems;
import java.nio.file.Path;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ImageConverterTest {
	
	@InjectMocks
	private ImageConverter unit;
	
	@Mock
	private ConvertCmd convertCommand;

	@Captor
	private ArgumentCaptor<IMOperation> convertOperationCaptor;
	
	@Test
	public void testConvert() throws Exception {
		// Given
		Path sourcePath = FileSystems.getDefault().getPath("src", "test", "resources", "templates", "advice-dog.jpg");
		Path destinationPath = FileSystems.getDefault().getPath("src", "test", "resources", "templates", "converter-output.png");

		// When
		unit.convert(sourcePath, destinationPath);

		// Then
		verify(convertCommand).run(convertOperationCaptor.capture(), anyVararg());
		String expectedConvertCommand = "?img? -background white -alpha remove ?img? ";
		assertEquals(expectedConvertCommand, convertOperationCaptor
				.getAllValues().get(0).toString());

	}

}
