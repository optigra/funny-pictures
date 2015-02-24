package com.optigra.funnypictures.extractor;

import java.util.Map;

import com.optigra.funnypictures.dao.Dao;
import com.optigra.funnypictures.model.Model;

/**
 * Abstract parameters extractor with some operations.
 * @author ivanursul
 *
 * @param <T> Entity.
 */
public abstract class AbstractParametersExtractor<T> implements ParametersExtractor<T> {

	//FIXME add proper Javadoc comments.
	
	/**
	 * Method for foreign key parameters.
	 * @param entity entity
	 * @param dao dao
	 * @param parameterName parameterName
	 * @param parameters parameters map
	 */
	protected <E> void addParameter(final Model entity, final Dao<E, Long> dao, final String parameterName, final Map<String, Object> parameters) {
		
		if (entity != null) {
			E dbEntity = dao.findById(entity.getId());
			parameters.put(parameterName, dbEntity);
		}
	}
	
	/**
	 * Method for simple fields.
	 * @param parameter ADD DESC
	 * @param parameterName ADD DESC
	 * @param parameters ADD DESC
	 */
	protected void addParameter(final Object parameter, final String parameterName, final Map<String, Object> parameters) {
		
		if (parameter != null) {
			parameters.put(parameterName, parameter);
		}
		
	}
}
