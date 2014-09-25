package com.optigra.funnypictures.facade.facade.funny;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
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

import com.optigra.funnypictures.facade.converter.Converter;
import com.optigra.funnypictures.facade.resources.ApiResource;
import com.optigra.funnypictures.facade.resources.picture.FunnyPictureResource;
import com.optigra.funnypictures.facade.resources.search.PagedRequest;
import com.optigra.funnypictures.facade.resources.search.PagedResultResource;
import com.optigra.funnypictures.model.FunnyPicture;
import com.optigra.funnypictures.pagination.PagedResult;
import com.optigra.funnypictures.pagination.PagedSearch;
import com.optigra.funnypictures.queries.Queries;
import com.optigra.funnypictures.service.funnypicture.FunnyPictureService;

@RunWith(MockitoJUnitRunner.class)
public class DefaultFunnyPictureFacadeTest {

	@Mock
	private FunnyPictureService funnyPictureService;

	@Mock
	private Converter<PagedRequest, PagedSearch<FunnyPicture>> pagedRequestConverter;

	@Mock
	private Converter<PagedResult<?>, PagedResultResource<? extends ApiResource>> pagedResultConverter;

	@Mock
	private Converter<FunnyPicture, FunnyPictureResource> funnyPictureConverter;

	@InjectMocks
	private DefaultFunnyPictureFacade unit = new DefaultFunnyPictureFacade();

	@Test
	public void testGetFunnies() throws Exception {
		// Given
		PagedRequest pagedRequest = new PagedRequest(10, 10);
		List<FunnyPictureResource> funnyResources = Collections.singletonList(new FunnyPictureResource());
		PagedResultResource<FunnyPictureResource> expectedFunnies = new PagedResultResource<>("/funnies");
		expectedFunnies.setEntities(funnyResources);

		PagedSearch<FunnyPicture> pagedSearch = new PagedSearch<FunnyPicture>(5, 7, Queries.FIND_PICTURES, Collections.<String, Object> emptyMap(),
				FunnyPicture.class);
		List<FunnyPicture> entities = Arrays.asList(new FunnyPicture());
		PagedResult<FunnyPicture> pagedResult = new PagedResult<FunnyPicture>(8, 3, 5, entities);

		// When
		when(pagedRequestConverter.convert(any(PagedRequest.class))).thenReturn(pagedSearch);
		when(funnyPictureService.getFunnyPictures(Matchers.<PagedSearch<FunnyPicture>> any())).thenReturn(pagedResult);
		when(funnyPictureConverter.convertAll(anyListOf(FunnyPicture.class))).thenReturn(funnyResources);

		PagedResultResource<FunnyPictureResource> actualFunnies = unit.getFunnies(pagedRequest);

		// Then
		verify(pagedRequestConverter).convert(pagedRequest);
		verify(funnyPictureService).getFunnyPictures(pagedSearch);
		verify(funnyPictureConverter).convertAll(entities);
		verify(pagedResultConverter).convert(pagedResult, expectedFunnies);

		assertEquals(expectedFunnies, actualFunnies);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testCreateFunny() throws Exception {
		// Given
		FunnyPictureResource funny = new FunnyPictureResource();

		// When
		unit.createFunnyPicture(funny);

		// Then
		// Expected exception
	}

}
