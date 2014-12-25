package com.optigra.funnypictures.dao.thumbnail;

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

import com.optigra.funnypictures.dao.persistence.PersistenceManager;
import com.optigra.funnypictures.model.thumbnail.PictureThumbnail;
import com.optigra.funnypictures.pagination.PagedResult;
import com.optigra.funnypictures.pagination.PagedSearch;
import com.optigra.funnypictures.queries.Queries;

@RunWith(MockitoJUnitRunner.class)
public class DefaultPictureThumbnailDaoTest {
	@InjectMocks
	private DefaultPictureThumbnailDao unit;
	
	@Mock
	private PersistenceManager<PictureThumbnail, Long> persistenceManager;
	
	@Test
	public void testGetThumbnails() throws Exception {
		// Given
		Integer limit = 20;
		Integer offset = 0;
		long count = 100;
		PictureThumbnail entity1 = new PictureThumbnail();
		List<PictureThumbnail> entities = Arrays.asList(entity1);
		
		PagedSearch<PictureThumbnail> pagedSearch = new PagedSearch<PictureThumbnail>();
		pagedSearch.setLimit(limit);
		pagedSearch.setOffset(offset);
		pagedSearch.setClazz(PictureThumbnail.class);
		pagedSearch.setParameters(Collections.<String, Object>emptyMap());
		pagedSearch.setQuery(Queries.GET_PICTURE_THUMBNAILS);
		
		PagedResult<PictureThumbnail> expected = new PagedResult<PictureThumbnail>(offset, limit, count, entities);
		
		when(persistenceManager.search(Matchers.<PagedSearch<PictureThumbnail>>any())).thenReturn(expected);
		
		// When
		PagedResult<PictureThumbnail> actual = unit.getThumbnails(pagedSearch);

		// Then
		verify(persistenceManager).search(pagedSearch);
		assertEquals(expected, actual);
	}

}
