package com.optigra.funnypictures.facade.facade.content;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.optigra.funnypictures.content.model.Content;
import com.optigra.funnypictures.content.service.ContentService;
import com.optigra.funnypictures.facade.resources.content.ContentResource;
import com.optigra.funnypictures.model.content.MimeType;

@RunWith(MockitoJUnitRunner.class)
public class DefaultContentFacadeTest {

	@Mock
	private ContentService contentService;
	
	@InjectMocks
	private DefaultContentFacade unit = new DefaultContentFacade();
	
	@Test
	public void testGetContent() throws Exception {
		// Given 
		String uri = "test-file.png";
		MimeType mimeType =  MimeType.IMAGE_PNG_PNG;
		ContentResource expectedContent = new ContentResource();
		expectedContent.setMimeType(mimeType );
		expectedContent.setPath(uri);
			
		Content content = new Content();
		content.setMimeType(mimeType);
		content.setPath(uri);

		// When
		when(contentService.getContentByPath(uri)).thenReturn(content);
		
		ContentResource actualContent = unit.getContent(uri);
		
		// Then
		assertEquals(expectedContent, actualContent);
	}
}
