package com.optigra.funnypictures.service.repository.monitor;

import java.util.EventListener;

/**
 * An interface for repository listeners that listen for entry creation and deletion events.
 * @author odisseus
 *
 */
public interface RepositoryListener extends EventListener {
	/**
	 * A method that is called when a new entry is created.
	 * @param name the name of the new entry
	 */
	void entryCreated(String name);

	/**
	 * A method that is called when an entry is deleted.
	 * @param name the name of the deleted entry
	 */
	void entryDeleted(String name);
}
