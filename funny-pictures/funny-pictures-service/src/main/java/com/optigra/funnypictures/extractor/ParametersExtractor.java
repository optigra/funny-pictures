package com.optigra.funnypictures.extractor;

import java.util.Map;

/**
 * Interface, that is used to retrieve all parameters from special pagedSearch.
 * @author ivanursul
 *
 * @param <T> entity.
 */
public interface ParametersExtractor<T> {

	/**
	 * method for getting parameters from pagedSearch.
	 * @param entity the paged search or something.
	 * @return parameter map
	 */
	Map<String, Object> getParameters(T entity);

}
