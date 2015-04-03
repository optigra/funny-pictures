package com.optigra.funnypictures.facade.facade.picture;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.optigra.funnypictures.content.model.Content;
import com.optigra.funnypictures.content.service.ContentService;
import com.optigra.funnypictures.facade.converter.Converter;
import com.optigra.funnypictures.facade.resources.ApiResource;
import com.optigra.funnypictures.facade.resources.content.ContentResourceNamingStrategy;
import com.optigra.funnypictures.facade.resources.picture.PictureResource;
import com.optigra.funnypictures.facade.resources.search.PagedRequest;
import com.optigra.funnypictures.facade.resources.search.PagedResultResource;
import com.optigra.funnypictures.model.Picture;
import com.optigra.funnypictures.model.thumbnail.PictureThumbnail;
import com.optigra.funnypictures.pagination.PagedResult;
import com.optigra.funnypictures.pagination.PagedSearch;
import com.optigra.funnypictures.service.picture.PictureService;
import com.optigra.funnypictures.service.thumbnail.PictureThumbnailService;
import com.optigra.funnypictures.service.thumbnail.ThumbnailGeneratorService;

@RunWith(MockitoJUnitRunner.class)
public class DefaultPictureFacadeTest {

	@InjectMocks
	private DefaultPictureFacade unit;

	@Mock
	private Converter<PagedRequest<PictureResource>, PagedSearch<Picture>> pagedRequestConverter;

	@Mock
	private Converter<PagedResult<?>, PagedResultResource<? extends ApiResource>> pagedResultConverter;

	@Mock
	private Converter<Picture, PictureResource> pictureConverter;

	@Mock
	private Converter<PictureResource, Picture> pictureResourceConverter;

	@Mock
	private PictureService pictureService;

	@Mock
	private PictureThumbnailService pictureThumbnailService;

	@Mock
	private ContentResourceNamingStrategy namingStrategy;
	
	@Mock
	private ContentResourceNamingStrategy thumbnailNamingStrategy;

	@Mock
	private ThumbnailGeneratorService thumbnailGeneratorService;

	@Mock
	private ContentService contentService;

	private Boolean generateMissingFunnyThumbnails = true;

	@Before
	public void setup() {
		unit.setGenerateMissingFunnyThumbnails(generateMissingFunnyThumbnails);
	}
	
	@Test
	public void testGetPictures() throws Exception {
		// Given
		Integer offset = 1;
		Integer limit = 20;
		PagedRequest<PictureResource> pagedRequest = new PagedRequest<PictureResource>(offset, limit);
		PagedSearch<Picture> pagedSearch = new PagedSearch<>();
		pagedSearch.setLimit(limit);
		pagedSearch.setOffset(offset);

		Picture picture1 = new Picture();
		PictureThumbnail thumbnail1 = new PictureThumbnail();
		List<PictureThumbnail> thumbnails = Arrays.asList(thumbnail1);
		picture1.setThumbnails(thumbnails);
		picture1.setName("picture1");
		long count = 199;
		List<Picture> entities = Arrays.asList(picture1);
		PagedResult<Picture> pagedResult = new PagedResult<Picture>(offset, limit, count, entities);
		PictureResource resource1 = new PictureResource();
		List<PictureResource> resources = Arrays.asList(resource1);

		PagedResultResource<PictureResource> expected = new PagedResultResource<>("/pictures");
		expected.setEntities(resources);

		// When
		when(pagedRequestConverter.convert(Matchers.<PagedRequest<PictureResource>>any())).thenReturn(pagedSearch);
		when(pictureService.getPictures(Matchers.<PagedSearch<Picture>> any())).thenReturn(pagedResult);
		when(pictureConverter.convertAll(anyListOf(Picture.class))).thenReturn(resources);

		PagedResultResource<PictureResource> actual = unit.getPictures(pagedRequest);

		// Then
		verify(pagedRequestConverter).convert(pagedRequest);
		verify(pictureService).getPictures(pagedSearch);
		verify(pictureConverter).convertAll(entities);
		verify(pagedResultConverter).convert(pagedResult, expected);
		verify(thumbnailGeneratorService, times(0)).generateThumbnails(any(Content.class));
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testGetPicturesWithGenerated() throws Exception {
		// Given
		Integer offset = 1;
		Integer limit = 20;
		PagedRequest<PictureResource> pagedRequest = new PagedRequest<PictureResource>(offset, limit);
		PagedSearch<Picture> pagedSearch = new PagedSearch<>();
		pagedSearch.setLimit(limit);
		pagedSearch.setOffset(offset);
		
		Picture picture1 = new Picture();
		List<PictureThumbnail> thumbnails = Collections.emptyList();
		picture1.setThumbnails(thumbnails);
		picture1.setName("picture1");
		long count = 199;
		List<Picture> entities = Arrays.asList(picture1);
		PagedResult<Picture> pagedResult = new PagedResult<Picture>(offset, limit, count, entities);
		PictureResource resource1 = new PictureResource();
		List<PictureResource> resources = Arrays.asList(resource1);
		
		PagedResultResource<PictureResource> expected = new PagedResultResource<>("/pictures");
		expected.setEntities(resources);
		
		// When
		when(pagedRequestConverter.convert(Matchers.<PagedRequest<PictureResource>>any())).thenReturn(pagedSearch);
		when(pictureService.getPictures(Matchers.<PagedSearch<Picture>> any())).thenReturn(pagedResult);
		when(pictureConverter.convertAll(anyListOf(Picture.class))).thenReturn(resources);
		
		PagedResultResource<PictureResource> actual = unit.getPictures(pagedRequest);
		
		// Then
		verify(pagedRequestConverter).convert(pagedRequest);
		verify(pictureService).getPictures(pagedSearch);
		verify(pictureConverter).convertAll(entities);
		verify(pagedResultConverter).convert(pagedResult, expected);
		verify(thumbnailGeneratorService).generateThumbnails(any(Content.class));
		
		assertEquals(expected, actual);
	}

	@Test
	public void testCreatePicture() {

		// Given
		String name = "name";
		String url = "url";

		PictureResource pictureResource = new PictureResource();
		pictureResource.setName(name);
		pictureResource.setUrl(url);

		Picture returnedPictureFromConverter = new Picture();
		returnedPictureFromConverter.setName(name);
		returnedPictureFromConverter.setUrl(url);
		
		PictureResource expected = new PictureResource();
		expected.setName(name);
		expected.setUrl(url);
		
		// When

		when(pictureResourceConverter.convert(pictureResource)).thenReturn(returnedPictureFromConverter);
		when(pictureConverter.convert(returnedPictureFromConverter)).thenReturn(expected);
		PictureResource actual = unit.createPicture(pictureResource);
		// Then

		verify(pictureConverter).convert(returnedPictureFromConverter);
		assertEquals(expected, actual);
	}

	@Test
	public void testUpdatePicture() {
		
		// Given
		Long id = 2L;
		String name = "name";
		String url = "url";
		
		PictureResource pictureResource = new PictureResource();
		pictureResource.setName(name);
		pictureResource.setUrl(url);
		
		Picture picture = new Picture();
		picture.setName(name);
		picture.setUrl(url);
		
		PictureResource expected = new PictureResource();
		expected.setName(name);
		expected.setUrl(url);
		
		// When
		when(pictureService.getPicture(anyLong())).thenReturn(picture);
		unit.updatePicture(id, pictureResource);
		// Then
		verify(pictureService).getPicture(id);
		verify(pictureResourceConverter).convert(pictureResource, picture);
		verify(pictureService).updatePicture(picture);
	}
	
	@Test
	public void testGetPicture() {
		
		// Given
		Long id = 2L;
		String name = "name";
		String url = "url";
		
		Picture picture = new Picture();
		picture.setName(name);
		picture.setUrl(url);
		
		PictureResource expected = new PictureResource();
		expected.setName(name);
		expected.setUrl(url);
		
		// When
		when(pictureService.getPicture(anyLong())).thenReturn(picture);
		when(pictureConverter.convert(any(Picture.class))).thenReturn(expected);
		PictureResource actual = unit.getPicture(id);
		// Then
		verify(pictureService).getPicture(id);
		verify(pictureConverter).convert(picture);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testDeletePicture() {
		
		// Given
		Long id = 2L;
		String name = "name";
		String url = "url";
		
		Picture picture = new Picture();
		picture.setName(name);
		picture.setUrl(url);
		
		
		// When
		when(pictureService.getPicture(anyLong())).thenReturn(picture);
		unit.deletePicture(id);
		// Then
		verify(pictureService).getPicture(id);
		verify(pictureService).deletePicture(picture);
	}

}
