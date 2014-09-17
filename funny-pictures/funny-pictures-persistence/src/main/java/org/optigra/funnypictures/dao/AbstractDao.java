package org.optigra.funnypictures.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class AbstractDao<E, T> implements Dao<E, T> {

	@PersistenceContext(unitName="persistenceUnit")
	private EntityManager entityManager;

	public abstract Class<E> getEntityClass();
	
	@Override
	public E findById(T id) {
		E entity = entityManager.find(getEntityClass(), id);
		return entity;
	}

	@Override
	public void save(E entity) {
		entityManager.persist(entity);
	}

	@Override
	public void update(E entity) {
		entityManager.merge(entity);
	}

	@Override
	public void delete(E entity) {
		entityManager.remove(entity);
	}
}
