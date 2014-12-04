package com.optigra.funnypictures.facade.facade.funny;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.optigra.funnypictures.content.model.Content;
import com.optigra.funnypictures.content.model.ThumbnailContent;
import com.optigra.funnypictures.content.service.ContentService;
import com.optigra.funnypictures.facade.converter.Converter;
import com.optigra.funnypictures.facade.converter.funny.FunnyPictureConverter;
import com.optigra.funnypictures.facade.resources.ApiResource;
import com.optigra.funnypictures.facade.resources.content.ContentResource;
import com.optigra.funnypictures.facade.resources.content.ContentResourceNamingStrategy;
import com.optigra.funnypictures.facade.resources.picture.FunnyPictureResource;
import com.optigra.funnypictures.facade.resources.picture.PictureResource;
import com.optigra.funnypictures.facade.resources.search.PagedRequest;
import com.optigra.funnypictures.facade.resources.search.PagedResultResource;
import com.optigra.funnypictures.generator.api.AdviceMemeContext;
import com.optigra.funnypictures.generator.api.AdviceMemeGenerator;
import com.optigra.funnypictures.generator.api.ImageHandle;
import com.optigra.funnypictures.generator.api.LabelledImageGenerator;
import com.optigra.funnypictures.model.FunnyPicture;
import com.optigra.funnypictures.model.Picture;
import com.optigra.funnypictures.model.content.MimeType;
import com.optigra.funnypictures.pagination.PagedResult;
import com.optigra.funnypictures.pagination.PagedSearch;
import com.optigra.funnypictures.queries.Queries;
import com.optigra.funnypictures.service.funnypicture.FunnyPictureService;
import com.optigra.funnypictures.service.picture.PictureService;
import com.optigra.funnypictures.service.thumbnail.ThumbnailGeneratorService;
import com.optigra.funnypictures.service.thumbnail.funny.FunnyPictureThumbnailService;

@RunWith(MockitoJUnitRunner.class)
public class DefaultFunnyPictureFacadeTest {
	
	@Mock
	private FunnyPictureService funnyPictureService;
	
	@Mock
	private PictureService pictureService;
	
	@Mock
	private ContentService contentService;
	
	@Mock
	private ThumbnailGeneratorService thumbnailGeneratorService;
	
	@Mock
	private FunnyPictureThumbnailService funnyPictureThumbnailService;

	@Mock
	private Converter<PagedRequest, PagedSearch<FunnyPicture>> pagedRequestConverter;

	@Mock
	private Converter<PagedResult<?>, PagedResultResource<? extends ApiResource>> pagedResultConverter;

	@Mock
	private FunnyPictureConverter funnyPictureConverter;
	
	@Mock
	private AdviceMemeGenerator memeGenerator;
	
	@Mock
	private LabelledImageGenerator imageLabeller;

	@Mock
	private ContentResourceNamingStrategy namingStrategy;
	
	@InjectMocks
	private DefaultFunnyPictureFacade unit = new DefaultFunnyPictureFacade();
	
	@Captor
	private ArgumentCaptor<Content> contentCaptor;
	
	@Test
	public void testGetFunnies() throws Exception {
		// Given
		PagedRequest pagedRequest = new PagedRequest(10, 10);
		List<FunnyPictureResource> funnyResources = Collections.singletonList(new FunnyPictureResource());
		PagedResultResource<FunnyPictureResource> expectedFunnies = new PagedResultResource<>("/funnies");
		expectedFunnies.setEntities(funnyResources);

		PagedSearch<FunnyPicture> pagedSearch = new PagedSearch<FunnyPicture>(5, 7, Queries.FIND_PICTURES, Collections.<String, Object> emptyMap(),
				FunnyPicture.class);
		List<FunnyPicture> entities = Arrays.asList(new FunnyPicture());
		PagedResult<FunnyPicture> pagedResult = new PagedResult<FunnyPicture>(8, 3, 5, entities);

		// When
		when(pagedRequestConverter.convert(any(PagedRequest.class))).thenReturn(pagedSearch);
		when(funnyPictureService.getFunnyPictures(Matchers.<PagedSearch<FunnyPicture>> any())).thenReturn(pagedResult);
		when(funnyPictureConverter.convertAll(anyListOf(FunnyPicture.class))).thenReturn(funnyResources);

		PagedResultResource<FunnyPictureResource> actualFunnies = unit.getFunnies(pagedRequest);

		// Then
		verify(pagedRequestConverter).convert(pagedRequest);
		verify(funnyPictureService).getFunnyPictures(pagedSearch);
		verify(funnyPictureConverter).convertAll(entities);
		verify(pagedResultConverter).convert(pagedResult, expectedFunnies);

		assertEquals(expectedFunnies, actualFunnies);
	}
	
	@Test
	public void testCreateFunnyPicture() throws Exception {
		// TODO make this test more clear
		// Given
		Long inputId = -1L;
		String inputUrl = "template url";
		Picture dbEntity = new Picture();
		dbEntity.setUrl(inputUrl);
		PictureResource inputPicture = new PictureResource();
		inputPicture.setId(inputId);
		byte[] templateData = new byte[]{1, 2, 3};
		Content content = new Content();
		content.setPath(inputUrl);
		content.setContentStream(new ByteArrayInputStream(templateData));
		content.setSize((long) templateData.length);
		byte[] pictureData = new byte[]{125, 126, 127};
		InputStream pictureStream = new ByteArrayInputStream(pictureData);
		ImageHandle pictureHandle = new ImageHandle(pictureStream, MimeType.IMAGE_JPEG_JPG);
		String pictureID = "id-for-database";
		ThumbnailContent thumbnail = new ThumbnailContent();
		thumbnail.setPath("path/to/thumbnail");
		FunnyPictureResource expected = new FunnyPictureResource();
		
		FunnyPictureResource input = new FunnyPictureResource();
		input.setTemplate(inputPicture);
		
		when(pictureService.getPicture(any(Long.class))).thenReturn(dbEntity);
		when(contentService.getContentByPath(any(String.class))).thenReturn(content);
		when(memeGenerator.generate(any(AdviceMemeContext.class))).thenReturn(pictureHandle);
		when(namingStrategy.createIdentifier(any(ContentResource.class))).thenReturn(pictureID);
		when(thumbnailGeneratorService.generateThumbnails(any(Content.class))).thenReturn(Collections.singletonList(thumbnail));
		when(funnyPictureService.createFunnyPicture(any(FunnyPicture.class))).thenReturn(new FunnyPicture());
		when(funnyPictureConverter.convert(any(FunnyPicture.class))).thenReturn(expected);
		
		// When
		FunnyPictureResource actual = unit.createFunnyPicture(input);
		
		// Then
		verify(pictureService).getPicture(inputId);
		verify(contentService).getContentByPath(inputUrl);
		verify(memeGenerator).generate(any(AdviceMemeContext.class));
		verify(contentService, times(2)).saveContent(contentCaptor.capture());
		verify(funnyPictureService).createFunnyPicture(any(FunnyPicture.class));
		verify(thumbnailGeneratorService).generateThumbnails(any(Content.class));
		verify(contentService).saveContent(thumbnail);
		
		List<Content> capturedContents = contentCaptor.getAllValues();
		assertEquals(pictureStream, capturedContents.get(0).getContentStream());
		assertEquals(thumbnail, capturedContents.get(1));
		assertEquals(expected, actual);
	}
	

}
