package com.optigra.funnypictures.facade.facade.converter.funny;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.optigra.funnypictures.facade.converter.Converter;
import com.optigra.funnypictures.facade.converter.funny.FunnyPictureConverter;
import com.optigra.funnypictures.facade.resources.picture.FunnyPictureResource;
import com.optigra.funnypictures.facade.resources.picture.PictureResource;
import com.optigra.funnypictures.facade.resources.thumbnail.funny.FunnyPictureThumbnailResource;
import com.optigra.funnypictures.model.FunnyPicture;
import com.optigra.funnypictures.model.Picture;
import com.optigra.funnypictures.model.thumbnail.FunnyPictureThumbnail;

@RunWith(MockitoJUnitRunner.class)
public class FunnyPictureConverterTest {
	
	private String contentRootUrl = "";
	
	@InjectMocks
	private FunnyPictureConverter unit = new FunnyPictureConverter();
	
	@Mock
	private Converter<Picture, PictureResource> pictureConverter;
	
	@Mock
	private Converter<FunnyPictureThumbnail, FunnyPictureThumbnailResource> funnyPictureThumbnailConverter;
	
	@Before
	public void setUp(){
		unit.setContentRootUrl(contentRootUrl);
	}

	@Test
	public void testConvert() {

		// Given
		Long id = 1L;
		String name = "Name";
		String url = "Url";
		String footer = "Footer";
		String header = "Header";
		Picture picture = new Picture();
		
		FunnyPicture picture1 = new FunnyPicture();
		picture1.setId(id);
		picture1.setName(name);
		picture1.setUrl(url);
		picture1.setFooter(footer);
		picture1.setHeader(header);
		picture1.setPicture(picture);
		picture1.setThumbnails(Collections.<FunnyPictureThumbnail>emptyList());

		FunnyPictureResource expectedResource = new FunnyPictureResource();

		PictureResource pictureResource = new PictureResource();
		
		expectedResource.setId(id);
		expectedResource.setName(name);
		expectedResource.setUrl(url);
		expectedResource.setHeader(header);
		expectedResource.setFooter(footer);
		expectedResource.setTemplate(pictureResource);
		expectedResource.setThumbnails(Collections.<FunnyPictureThumbnailResource>emptyList());

		// When
		when(pictureConverter.convert(picture)).thenReturn(pictureResource);
		
		FunnyPictureResource actualResource = unit.convert(picture1);

		// Then
		assertEquals(expectedResource, actualResource);

	}

	@Test
	public void testConvertToResource() {

		// Given
		Long id = 1L;
		String name = "Name";
		String url = "Url";

		FunnyPicture picture1 = new FunnyPicture();
		picture1.setId(id);
		picture1.setName(name);
		picture1.setUrl(url);
		picture1.setThumbnails(Collections.<FunnyPictureThumbnail>emptyList());

		FunnyPictureResource expectedResource = new FunnyPictureResource();

		expectedResource.setId(id);
		expectedResource.setName(name);
		expectedResource.setUrl(url);
		expectedResource.setThumbnails(Collections.<FunnyPictureThumbnailResource>emptyList());

		// When
		FunnyPictureResource actualResource = new FunnyPictureResource();
		actualResource = unit.convert(picture1, actualResource);

		// Then
		assertEquals(expectedResource, actualResource);

	}

	@Test
	public void testConvertAll() {
		// Given
		Long id1 = 1L;
		String name1 = "Name";
		String url1 = "Url";

		Long id2 = 2L;
		String name2 = "Name2";
		String url2 = "Url2";

		FunnyPicture picture1 = new FunnyPicture();
		picture1.setId(id1);
		picture1.setName(name1);
		picture1.setUrl(url1);
		picture1.setThumbnails(Collections.<FunnyPictureThumbnail>emptyList());

		FunnyPicture picture2 = new FunnyPicture();
		picture2.setId(id2);
		picture2.setName(name2);
		picture2.setUrl(url2);

		FunnyPictureResource expectedResource1 = new FunnyPictureResource();

		expectedResource1.setId(id1);
		expectedResource1.setName(name1);
		expectedResource1.setUrl(url1);
		expectedResource1.setThumbnails(Collections.<FunnyPictureThumbnailResource>emptyList());

		FunnyPictureResource expectedResource2 = new FunnyPictureResource();

		expectedResource2.setId(id2);
		expectedResource2.setName(name2);
		expectedResource2.setUrl(url2);
		expectedResource2.setThumbnails(Collections.<FunnyPictureThumbnailResource>emptyList());

		List<FunnyPictureResource> expectedResource = Arrays.asList(expectedResource1, expectedResource2);

		// When
		List<FunnyPictureResource> actualResource = unit.convertAll(Arrays.asList(picture1, picture2));

		// Then
		assertArrayEquals(expectedResource.toArray(), actualResource.toArray());
	}
}
