package com.optigra.funnypictures.dao;

import com.optigra.funnypictures.pagination.PagedResult;
import com.optigra.funnypictures.pagination.PagedSearch;

/**
 * DAO interface to make CRUD operations with entities.
 * 
 * @author oyats
 *
 * @param <E>
 *            Entity type.
 * @param <T>
 *            Type of Id of entity.
 */
public interface Dao<E, T> {

	/**
	 * Method to find Entity by Id.
	 * 
	 * @param id
	 *            Parameter by which we are trying to find our entity.
	 * @return Found entity.
	 */
	E findById(T id);

	/**
	 * Method for saving entity.
	 * 
	 * @param entity
	 *            Parameter for entity to save.
	 */
	void save(E entity);

	/**
	 * Method for updating an entity.
	 * 
	 * @param entity
	 *            Entity to update.
	 */
	void update(E entity);

	/**
	 * Method to delete an entity.
	 * 
	 * @param entity
	 *            Entity to delete.
	 */
	void delete(E entity);

	/**
	 * Method for getting paged Result.
	 * @param pagedSearch
	 * @return paged result.
	 */
	PagedResult<E> getEntities(PagedSearch<E> pagedSearch);
}
