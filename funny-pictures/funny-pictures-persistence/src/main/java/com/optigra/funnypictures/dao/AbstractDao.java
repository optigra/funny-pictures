package com.optigra.funnypictures.dao;

import javax.annotation.Resource;

import com.optigra.funnypictures.builder.QueryBuilder;
import com.optigra.funnypictures.dao.persistence.PersistenceManager;
import com.optigra.funnypictures.pagination.PagedResult;
import com.optigra.funnypictures.pagination.PagedSearch;
import com.optigra.funnypictures.queries.Queries;

/**
 * Abstract class with implemented CRUD methods.
 * 
 * @author oyats
 *
 * @param <E>
 *            Type of Entities to manipulate with.
 * @param <T>
 *            Type of ID in our Entity class.
 */
public abstract class AbstractDao<E, T> implements Dao<E, T> {

	@Resource(name = "persistenceManager")
	private PersistenceManager<E, T> persistenceManager;

	private QueryBuilder<E> queryBuilder;
	
	public void setQueryBuilder(final QueryBuilder<E> queryBuilder) {
		this.queryBuilder = queryBuilder;
	}
	
	public PersistenceManager<E, T> getPersistenceManager() {
		return persistenceManager;
	}
	
	/**
	 * Method to get Class of an Entity.
	 * @return Class of an Entity.
	 */
	public abstract Class<E> getEntityClass();

	@Override
	public E findById(final T id) {
		E entity = persistenceManager.findById(getEntityClass(), id);
		return entity;
	}

	@Override
	public void save(final E entity) {
		persistenceManager.create(entity);
	}

	@Override
	public void update(final E entity) {
		persistenceManager.update(entity);
	}

	@Override
	public void delete(final E entity) {
		persistenceManager.remove(entity);
	}
	
	/**
	 * Search PagedResult with generic type from the PagedSearch.
	 * @param searchRequest Parameter with pagination, queries and parameters.
	 * @return Result of the search.
	 */
	protected PagedResult<E> search(final PagedSearch<E> searchRequest) {
		return persistenceManager.search(searchRequest);
	}
	
	@Override
	public PagedResult<E> getEntities(final PagedSearch<E> pagedSearch) {

		Queries query = queryBuilder.build(pagedSearch.getEntity());

		pagedSearch.setClazz(getEntityClass());
		pagedSearch.setParameters(pagedSearch.getParameters());
		pagedSearch.setQuery(query);

		return persistenceManager.search(pagedSearch);
	}
}
