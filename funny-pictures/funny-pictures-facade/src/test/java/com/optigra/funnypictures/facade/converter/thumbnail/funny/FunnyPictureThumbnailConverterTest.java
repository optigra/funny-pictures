package com.optigra.funnypictures.facade.converter.thumbnail.funny;

import java.util.Date;

import org.junit.Test;

import com.optigra.funnypictures.facade.resources.thumbnail.funny.FunnyPictureThumbnailResource;
import com.optigra.funnypictures.model.FunnyPicture;
import com.optigra.funnypictures.model.thumbnail.FunnyPictureThumbnail;

public class FunnyPictureThumbnailConverterTest {

	private FunnyPictureThumbnailConverter unit = new FunnyPictureThumbnailConverter();
	
	@Test
	public void testConverter() throws Exception {
		// Given
		FunnyPicture funnyPicture = new FunnyPicture();

		FunnyPictureThumbnail source = new FunnyPictureThumbnail();
		source.setCreateDate(new Date());
		source.setFunnyPicture(funnyPicture);
		
		// When
		FunnyPictureThumbnailResource actual = unit.convert(source);

		// Then
	}
}
