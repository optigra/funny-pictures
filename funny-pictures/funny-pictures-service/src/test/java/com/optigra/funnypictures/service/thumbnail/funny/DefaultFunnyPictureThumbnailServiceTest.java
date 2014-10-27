package com.optigra.funnypictures.service.thumbnail.funny;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.optigra.funnypictures.dao.thumbnail.funny.FunnyPictureThumbnailDao;
import com.optigra.funnypictures.model.thumbnail.FunnyPictureThumbnail;

@RunWith(MockitoJUnitRunner.class)
public class DefaultFunnyPictureThumbnailServiceTest {

	@InjectMocks
	private DefaultFunnyPictureThumbnailService unit;

	@Mock
	private FunnyPictureThumbnailDao funnyPictureThumbnailDao;
	
	@Test
	public void testCreateFunnyPictureThumbnail() throws Exception {
		// Given
		FunnyPictureThumbnail funnyPictureThumbnail = new FunnyPictureThumbnail();

		// When
		unit.createFunnyPictureThumbnail(funnyPictureThumbnail);

		// Then
		verify(funnyPictureThumbnailDao).save(funnyPictureThumbnail);
	}
}
