package com.optigra.funnypictures.dao.persistence;

import java.util.List;

import com.optigra.funnypictures.pagination.PagedResult;
import com.optigra.funnypictures.pagination.PagedSearch;
import com.optigra.funnypictures.queries.Query;

/**
 * Interface to manage entities.
 * 
 * @author oyats
 *
 * @param <T>
 *            Type of entity.
 * @param <I>
 */
public interface PersistenceManager<T, I> {

	/**
	 * Method to create new entity in database.
	 * 
	 * @param entity
	 *            Entity to create.
	 * @return Created entity.
	 */
	T create(T entity);

	/**
	 * Method to find entity by ID and class.
	 * 
	 * @param clazz
	 *            Class of entity.
	 * @param id
	 *            Type of entity ID.
	 * @return Entity, that was found.
	 */
	T findById(Class<T> clazz, I id);

	/**
	 * Method to update an entity.
	 * 
	 * @param entity
	 *            Entity to update.
	 * @return Updated entity.
	 */
	T update(T entity);

	/**
	 * Method to remove entity.
	 * 
	 * @param entity
	 *            Entity to remove.
	 */
	void remove(T entity);

	/**
	 * Method to execute queries.
	 * 
	 * @param query
	 *            Query to execute.
	 * @return Entity from query.
	 */
	T executeSingleResultQuery(Query<T> query);

	/**
	 * Method to execute multiple queries.
	 * 
	 * @param query
	 *            Query to execute.
	 * @return List of entities from queries.
	 */
	List<T> executeMultipleResultQuery(Query<T> query);

	/**
	 * Method to find PagedResult from PagedSearch object.
	 * 
	 * @param searchRequest
	 *            Parameter by which PagedSearch will be found.
	 * @return Found PagedResult.
	 */
	PagedResult<T> search(PagedSearch<T> searchRequest);
}
