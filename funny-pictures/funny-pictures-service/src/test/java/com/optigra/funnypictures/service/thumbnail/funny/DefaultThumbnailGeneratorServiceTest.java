package com.optigra.funnypictures.service.thumbnail.funny;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.optigra.funnypictures.content.model.Content;
import com.optigra.funnypictures.content.model.ThumbnailContent;
import com.optigra.funnypictures.content.service.ContentService;
import com.optigra.funnypictures.generator.api.ImageHandle;
import com.optigra.funnypictures.generator.api.ThumbnailContext;
import com.optigra.funnypictures.generator.api.ThumbnailGenerator;
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
	
	private String originalFilePath = "path";
	
	//private InputStream inputDataStream = new ByteArrayInputStream(new byte[]{125, 126, 127});
	private Content generatorServiceInputContent;
	private Content contentServiceOutputContent;
	
	private InputStream outputDataStream = new ByteArrayInputStream(new byte[]{1, 2, 3});
	private ImageHandle imageHandle = new ImageHandle(outputDataStream, MimeType.IMAGE_TIFF_TIFF);
	
	@Before
	public void setup() {
		generatorServiceInputContent = new Content();
		generatorServiceInputContent.setPath(originalFilePath);
		
		contentServiceOutputContent = new Content();
		contentServiceOutputContent.setContentStream(outputDataStream);
	}
	
	@Test
	public void testGenerateThumbnails() throws Exception {
		// Given
		when(thumbnailGenerator.generate(any(ThumbnailContext.class))).thenReturn(imageHandle);
		when(contentService.getContentByPath(any(String.class))).thenReturn(contentServiceOutputContent);
		
		// When
		List<ThumbnailContent> thumbnails = unit.generateThumbnails(generatorServiceInputContent);
		
		// Then
		verify(contentService, times(4)).getContentByPath(originalFilePath);
		verify(thumbnailGenerator, times(4)).generate(any(ThumbnailContext.class));
		// TODO ad assertion about results list
	}

}
