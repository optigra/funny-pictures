package com.optigra.funnypictures.service.thumbnail;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.optigra.funnypictures.dao.thumbnail.PictureThumbnailDao;
import com.optigra.funnypictures.model.thumbnail.PictureThumbnail;
import com.optigra.funnypictures.pagination.PagedResult;
import com.optigra.funnypictures.pagination.PagedSearch;

@RunWith(MockitoJUnitRunner.class)
public class DefaultPictureThumbnailServiceTest {
	
	@InjectMocks
	private DefaultPictureThumbnailService unit;

	@Mock
	private PictureThumbnailDao pictureDao;
	
	@Test
	public void testCreatePictureThumbnail() throws Exception {
		// Given
		PictureThumbnail pictureThumbnail = new PictureThumbnail();
		
		// When
		unit.createPictureThumbnail(pictureThumbnail);
		
		//Then
		verify(pictureDao).save(pictureThumbnail);
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetPictureThumbnails() throws Exception {
		// Given
		PictureThumbnail pictureThumbnail = new PictureThumbnail();
		int offset = 5;
		int limit = 20;
		PagedSearch<PictureThumbnail> search = new PagedSearch<PictureThumbnail>();
		PagedResult<PictureThumbnail> expected = new PagedResult<PictureThumbnail>(offset, limit, 1, Collections.singletonList(pictureThumbnail));
		when(pictureDao.getThumbnails(any(PagedSearch.class))).thenReturn(expected);
		
		// When
		PagedResult<PictureThumbnail> actual = unit.getPictureThumbnails(search);
		
		// Then
		verify(pictureDao).getThumbnails(search);
		assertEquals(expected, actual);
	}

}
