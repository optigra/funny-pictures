package com.optigra.funnypictures.service.repository.monitor;

/**
 * A repository monitor. It monitors the target repository for entry creation and deletion events and fires the respective abstract methods. 
 * An implementation may not block the thread that called it. 
 * Creation and deletion of nested directories should be monitored, but their contents may be not monitored.
 * @author odisseus
 *
 */
public interface RepositoryMonitor {

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
	 * Starts the monitor.
	 */
	void start();

	/**
	 * Stops the monitor.
	 */
	void stop();

}
