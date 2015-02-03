package com.optigra.funnypictures.service.funnypicture;

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

import com.optigra.funnypictures.dao.funnypicture.FunnyPictureDao;
import com.optigra.funnypictures.model.FunnyPicture;
import com.optigra.funnypictures.pagination.PagedResult;
import com.optigra.funnypictures.pagination.PagedSearch;

@RunWith(MockitoJUnitRunner.class)
public class DefaultFunnyPictureServiceTest {

	@InjectMocks
	private DefaultFunnyPictureService unit;

	@Mock
	private FunnyPictureDao pictureDao;

	@Test
	public void testGetFunnyPictures() {
		// Given
		Integer offset = 0;
		Integer limit = 20;
		long count = 100;
		FunnyPicture funnyPicture1 = new FunnyPicture();
		FunnyPicture funnyPicture2 = new FunnyPicture();
		List<FunnyPicture> entities = Arrays.asList(funnyPicture1,funnyPicture2);
		
		PagedSearch<FunnyPicture> pagedSearch = new PagedSearch<FunnyPicture>();
		pagedSearch.setLimit(limit);
		pagedSearch.setOffset(offset);
		
		PagedResult<FunnyPicture> expected = new PagedResult<FunnyPicture>(offset, limit, count, entities);
		
		// When
		
		when(pictureDao.getFunnyPictures(Matchers.<PagedSearch<FunnyPicture>> any())).thenReturn(expected);
		PagedResult<FunnyPicture> actual = unit.getFunnyPictures(pagedSearch);
		
		// Then
		verify(pictureDao).getFunnyPictures(pagedSearch);
		assertEquals(expected, actual);
	}

}
