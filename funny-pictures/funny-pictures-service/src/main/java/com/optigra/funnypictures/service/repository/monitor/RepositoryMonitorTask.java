package com.optigra.funnypictures.service.repository.monitor;

/**
 * @author odisseus
 *
 */
public interface RepositoryMonitorTask extends Runnable {

	/**
	 * Adds a repository listener that will receive notifications 
	 * when repository entries are created or deleted. 
	 * @param listener the listener to add
	 */
	void addListener(RepositoryListener listener);
	
	/**
	 * Removes a repository listener from the listeners list.
	 * It will no longer receive notifications from this repository monitor. 
	 * @param listener the listener to remove
	 */
	void removeListener(RepositoryListener listener);

	/**
	 * Stops the monitor.
	 */
	void stop();
	
	@Override
	void run();

}
