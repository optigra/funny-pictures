package com.optigra.funnypictures.service.conversion;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

import java.io.ByteArrayInputStream;
import java.nio.file.Path;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.optigra.funnypictures.generator.api.ImageHandle;
import com.optigra.funnypictures.generator.util.ImageConverter;
import com.optigra.funnypictures.model.content.MimeType;

@RunWith(MockitoJUnitRunner.class)
public class DefaultImageConversionServiceTest {
	
	@InjectMocks
	private DefaultImageConversionService unit;
	
	@Mock
	private ImageConverter imageConverter;
	
	@Test
	public void testConvert() throws Exception {
		// Given
		ImageHandle source = new ImageHandle(new ByteArrayInputStream(new byte[]{1, 2, 3}), MimeType.IMAGE_BMP_BMP);
		MimeType targetFormat = MimeType.IMAGE_GIF_GIF;
		
		// When
		ImageHandle result = unit.convert(source, targetFormat);
		
		// Then
		verify(imageConverter).convert(any(Path.class), any(Path.class), eq(targetFormat));
		assertEquals(targetFormat, result.getImageFormat());
	}

}
