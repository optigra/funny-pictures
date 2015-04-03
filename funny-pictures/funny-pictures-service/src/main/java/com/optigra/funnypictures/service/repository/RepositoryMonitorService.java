package com.optigra.funnypictures.service.repository;

/**
 * A monitor service that keeps track of names present in the repository 
 * and can find the next free name.
 * @author odisseus
 *
 */
public interface RepositoryMonitorService {
	
	/**
	 * Finds the next identifier that was never used.
	 * @return the new identifier
	 */
	String getNextFreeIdentifier();

}
