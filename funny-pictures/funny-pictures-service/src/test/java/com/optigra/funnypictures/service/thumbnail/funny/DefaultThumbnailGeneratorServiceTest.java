package com.optigra.funnypictures.service.thumbnail.funny;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.funny.pictures.generator.api.ImageHandle;
import org.funny.pictures.generator.api.ThumbnailContext;
import org.funny.pictures.generator.api.ThumbnailGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.optigra.funnypictures.content.model.Content;
import com.optigra.funnypictures.content.service.ContentService;
import com.optigra.funnypictures.model.content.MimeType;
import com.optigra.funnypictures.service.thumbnail.DefaultThumbnailGeneratorService;

@RunWith(MockitoJUnitRunner.class)
public class DefaultThumbnailGeneratorServiceTest {
	
	@Mock
	private ThumbnailGenerator thumbnailGenerator;
	
	@Mock
	private ContentService contentService;
	
	@InjectMocks
	private DefaultThumbnailGeneratorService unit;
	
	//private InputStream inputDataStream = new ByteArrayInputStream(new byte[]{125, 126, 127});
	private Content inputContent = new Content();
	
	private InputStream outputDataStream = new ByteArrayInputStream(new byte[]{1, 2, 3});
	private ImageHandle imageHandle = new ImageHandle(outputDataStream, MimeType.IMAGE_TIFF_TIFF);
	
	@Test
	public void testGenerateThumbnails() throws Exception {
		// Given
		when(thumbnailGenerator.generate(any(ThumbnailContext.class))).thenReturn(imageHandle);
		when(contentService.getContentByPath(any(String.class))).thenReturn(inputContent);
		
		// When
		Content content = new Content();
		unit.generateThumbnails(content);
		
		// Then
		// TODO insert verifications.
	}

}
