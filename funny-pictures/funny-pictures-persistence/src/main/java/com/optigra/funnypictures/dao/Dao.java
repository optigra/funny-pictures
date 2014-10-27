package com.optigra.funnypictures.dao;

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

}
