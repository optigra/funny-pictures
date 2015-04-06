package com.optigra.funnypictures.facade.converter.picture;

import com.optigra.funnypictures.facade.converter.Converter;
import com.optigra.funnypictures.facade.resources.picture.PictureResource;
import com.optigra.funnypictures.facade.resources.thumbnail.PictureThumbnailResource;
import com.optigra.funnypictures.model.Picture;
import com.optigra.funnypictures.model.thumbnail.PictureThumbnail;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class PictureConverterTest {

	private String contentRootUrl = "";

	@Mock
	private Converter<PictureThumbnail, PictureThumbnailResource> pictureThumbnailConverter;
	
	@InjectMocks
	private PictureConverter unit;
	
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

		Picture picture1 = new Picture();
		picture1.setId(id);
		picture1.setName(name);
		picture1.setUrl(url);

		PictureResource expectedResource = new PictureResource();

		expectedResource.setId(id);
		expectedResource.setName(name);
		expectedResource.setUrl(url);
		expectedResource.setThumbnails(Collections.<PictureThumbnailResource>emptyList());

		// When
		PictureResource actualResource = unit.convert(picture1);

		// Then
		assertEquals(expectedResource, actualResource);

	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConvertWithNullSource() {
		
		// Given
		Picture picture1 = null;
		
		// When
		unit.convert(picture1);
	}
	
	@Test
	public void testConvertToResource() {

		// Given
		Long id = 1L;
		String name = "Name";
		String url = "Url";

		Picture picture1 = new Picture();
		picture1.setId(id);
		picture1.setName(name);
		picture1.setUrl(url);

		PictureResource expectedResource = new PictureResource();

		expectedResource.setId(id);
		expectedResource.setName(name);
		expectedResource.setUrl(url);
		expectedResource.setThumbnails(Collections.<PictureThumbnailResource>emptyList());

		// When
		PictureResource actualResource = new PictureResource();
		actualResource = unit.convert(picture1, actualResource);

		// Then
		assertEquals(expectedResource,actualResource);

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

		Picture picture1 = new Picture();
		picture1.setId(id1);
		picture1.setName(name1);
		picture1.setUrl(url1);

		Picture picture2 = new Picture();
		picture2.setId(id2);
		picture2.setName(name2);
		picture2.setUrl(url2);

		PictureResource expectedResource1 = new PictureResource();

		expectedResource1.setId(id1);
		expectedResource1.setName(name1);
		expectedResource1.setUrl(url1);
		expectedResource1.setThumbnails(Collections.<PictureThumbnailResource>emptyList());

		PictureResource expectedResource2= new PictureResource();
		
		expectedResource2.setId(id2);
		expectedResource2.setName(name2);
		expectedResource2.setUrl(url2);
		expectedResource2.setThumbnails(Collections.<PictureThumbnailResource>emptyList());

		List<PictureResource> expectedResource = Arrays.asList(expectedResource1,expectedResource2);
		
		// When
		List<PictureResource> actualResource = unit.convertAll(Arrays.asList(picture1,picture2)); 

		// Then
		assertEquals(expectedResource, actualResource);
	}
}
