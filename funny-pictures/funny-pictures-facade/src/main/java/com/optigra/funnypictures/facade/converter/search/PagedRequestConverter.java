package com.optigra.funnypictures.facade.converter.search;

import org.springframework.stereotype.Component;

import com.optigra.funnypictures.facade.converter.AbstractConverter;
import com.optigra.funnypictures.facade.resources.search.PagedRequest;
import com.optigra.funnypictures.pagination.PagedSearch;

/**
 * Converter for PagedRequst and PagedSearch.
 * 
 * @author rostyslav
 *
 * @param <T>
 *            Type of converted model
 */
@Component("pagedRequestConverter")
public class PagedRequestConverter<T> extends AbstractConverter<PagedRequest, PagedSearch<T>> {

	@Override
	public PagedSearch<T> convert(final PagedRequest source, final PagedSearch<T> target) {

		target.setOffset(source.getOffset());
		target.setLimit(source.getLimit());

		return target;
	}

	@Override
	public PagedSearch<T> convert(final PagedRequest source) {
		return convert(source, new PagedSearch<T>());
	}

}
