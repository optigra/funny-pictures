package com.optigra.funnypictures.service.repository.monitor;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A monitor for file system repositories. It monitors a directory for file creation and deletion events. It does not monitor contents of nested directories.
 * @author odisseus
 *
 */
public class DirectoryMonitor implements RepositoryMonitor {
	
	private static final Logger LOG = LoggerFactory.getLogger(DirectoryMonitor.class);
	
	private final WatchService directoryWatchService;
	
	private final Map<WatchKey, Path> keys = new HashMap<WatchKey, Path>();
	
	private final Path repositoryLocation;
	
	private volatile boolean terminationRequested = false;
	
	// TODO MG --- think of a more efficient synchronization mode
	private List<RepositoryListener> listeners = Collections.synchronizedList(new ArrayList<RepositoryListener>());
	
	/**
	 * Creates a new DirectoryMonitor. 
	 * @param repositoryLocation the directory to monitor
	 */
	public DirectoryMonitor(final Path repositoryLocation) {
		try {
			this.directoryWatchService = FileSystems.getDefault().newWatchService();
			this.repositoryLocation = repositoryLocation;
			register(repositoryLocation);
		} catch (IOException e) {
			throw new RepositoryMonitorException(e);
		}
	}
	
	/**
	 * Register a directory to monitor.
	 * @param dir directory that will be monitored
	 * @throws IOException when something bad happens
	 */
	private void register(final Path dir) throws IOException {
		LOG.debug("Registering directory: {}", dir);
		WatchKey key = dir.register(directoryWatchService, ENTRY_CREATE,
				ENTRY_DELETE);
		keys.put(key, dir);
	}
	
	/* (non-Javadoc)
	 * @see com.optigra.funnypictures.service.repository.RepositoryMonitor#start()
	 */
    @Override
	public void start() {
    	DirectoryMonitor that = this;
    	Runnable r = () -> {
			try {
				that.run();
			} finally {
				try {
					LOG.debug("Closing the directory watch service for location: {}", repositoryLocation);
					directoryWatchService.close();
				} catch (IOException e) {
					throw new RepositoryMonitorException(e);
				}
			}

		};
		new Thread(r).start();
    }

    /**
     * Run the monitor loop.
     */
	private void run() {
		LOG.info("Starting file system monitor loop for location: {}", repositoryLocation);
        while (!terminationRequested) {

            // wait for key to be signalled
            WatchKey key;
            try {
                key = directoryWatchService.take();
            } catch (InterruptedException x) {
            	LOG.error("The file system monitor thread for {} was interrupted! Terminating monitor loop.", repositoryLocation);
                return;
            }

            Path dir = keys.get(key);
            if (dir == null) {
                LOG.error("WatchKey not recognized! WatchKey: {}", key);
                continue;
            }

            for (WatchEvent<?> event: key.pollEvents()) {
                WatchEvent.Kind kind = event.kind();

                if (kind == OVERFLOW) {
                	LOG.warn("Event overflow! Some events possibly got lost or discarded.");
                    continue;
                }

                // Context for directory entry event is the file name of entry
                WatchEvent<Path> ev = (WatchEvent<Path>) event;
                Path name = ev.context();
                Path child = dir.resolve(name);

				if (kind == ENTRY_CREATE) {
					LOG.debug("Entry created: {}", child);
					onEntryCreated(name);
					if (Files.isDirectory(child, NOFOLLOW_LINKS)) {
						LOG.warn("The entry {} is a directory. Its contents will not be monitored.", child);
					}
				} else if (kind == ENTRY_DELETE) {
					LOG.debug("Entry deleted: {}", child);
					onEntryDeleted(name);
				}
            }

            // reset key and remove from set if directory no longer accessible
            boolean valid = key.reset();
            if (!valid) {
                keys.remove(key);

                // all directories are inaccessible
                if (keys.isEmpty()) {
                	LOG.warn("The file system is no more accessible! Terminating the monitor loop.");
                    break;
                }
            }
        }
        
        LOG.info("The monitor loop for {} has ended! ", repositoryLocation);
	}
    
	/* (non-Javadoc)
	 * @see com.optigra.funnypictures.service.repository.RepositoryMonitor#stop()
	 */
    @Override
	public void stop() {
    	terminationRequested = true;
    }

    @Override
	public void addListener(final RepositoryListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeListener(final RepositoryListener listener) {
		listeners.remove(listener);		
	}
	
	/**
	 * Called when a new file is created.
	 * @param fileName the name of the new file
	 */
	private void onEntryCreated(final Path fileName) {
		for (RepositoryListener listener : listeners) {
			listener.entryDeleted(fileName.toString());
		}		
	}
	
	/**
	 * Called when a file is deleted.
	 * @param fileName the name of the deleted file
	 */
	private void onEntryDeleted(final Path fileName) {
		for (RepositoryListener listener : listeners) {
			listener.entryCreated(fileName.toString());
		}
	}

}
