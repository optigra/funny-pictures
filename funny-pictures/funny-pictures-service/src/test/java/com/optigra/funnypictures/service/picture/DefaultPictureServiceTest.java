package com.optigra.funnypictures.service.picture;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.optigra.funnypictures.dao.picture.PictureDao;

import com.optigra.funnypictures.model.Picture;
import com.optigra.funnypictures.pagination.PagedResult;
import com.optigra.funnypictures.pagination.PagedSearch;

@RunWith(MockitoJUnitRunner.class)
public class DefaultPictureServiceTest {

	@InjectMocks
	private DefaultPictureService unit;
	
	@Mock
	private PictureDao pictureDao;
	
	@Test
	public void testGetPictures() throws Exception {
		// Given
		Integer limit = 20;
		Integer offset = 0;
		long count = 100;
		Picture entity1 = new Picture();
		List<Picture> entities = Arrays.asList(entity1);
		
		PagedSearch<Picture> pagedSearch = new PagedSearch<>();
		pagedSearch.setLimit(limit);
		pagedSearch.setOffset(offset);

		PagedResult<Picture> expected = new PagedResult<Picture>(offset, limit, count, entities);

		when(pictureDao.getPictures(Matchers.<PagedSearch<Picture>>any())).thenReturn(expected);
		
		// When
		PagedResult<Picture> actual = unit.getPictures(pagedSearch);

		// Then
		verify(pictureDao).getPictures(pagedSearch);
		assertEquals(expected, actual);
	}
}
