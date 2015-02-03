package com.optigra.funnypictures.dao.funntypicture;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.optigra.funnypictures.dao.funnypicture.DefaultFunnyPictureDao;
import com.optigra.funnypictures.dao.persistence.PersistenceManager;
import com.optigra.funnypictures.model.FunnyPicture;
import com.optigra.funnypictures.pagination.PagedResult;
import com.optigra.funnypictures.pagination.PagedSearch;
import com.optigra.funnypictures.queries.Queries;

@RunWith(MockitoJUnitRunner.class)
public class DefaultFunnyPictureDaoTest {

	@InjectMocks
	private DefaultFunnyPictureDao unit;

	@Mock
	private PersistenceManager<FunnyPicture, Long> persistenceManager;

	@Test
	public void testGetFunnyPictures() {

		// Given
		Integer limit = 20;
		Integer offset = 0;
		long count = 100;
		FunnyPicture entity1 = new FunnyPicture();
		List<FunnyPicture> entities = Arrays.asList(entity1);

		PagedSearch<FunnyPicture> pagedSearch = new PagedSearch<FunnyPicture>();
		pagedSearch.setLimit(limit);
		pagedSearch.setOffset(offset);
		pagedSearch.setClazz(FunnyPicture.class);
		pagedSearch.setParameters(Collections.<String, Object> emptyMap());
		pagedSearch.setQuery(Queries.FIND_PICTURES);

		PagedResult<FunnyPicture> expected = new PagedResult<FunnyPicture>(offset, limit, count, entities);

		when(persistenceManager.search(Matchers.<PagedSearch<FunnyPicture>> any())).thenReturn(expected);
		
		// When
		PagedResult<FunnyPicture> actual = unit.getFunnyPictures(pagedSearch);

		// Then
		verify(persistenceManager).search(pagedSearch);
		assertEquals(expected, actual);

	}

}
