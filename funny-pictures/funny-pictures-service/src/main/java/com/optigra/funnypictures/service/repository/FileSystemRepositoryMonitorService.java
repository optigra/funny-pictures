package com.optigra.funnypictures.service.repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.optigra.funnypictures.service.repository.monitor.DirectoryMonitor;
import com.optigra.funnypictures.service.repository.monitor.RepositoryListener;
import com.optigra.funnypictures.service.repository.monitor.RepositoryMonitor;
import com.optigra.funnypictures.service.repository.monitor.RepositoryMonitorException;

/**
 * A service that monitors a directory the file system for file creation and deletion events.
 * @author odisseus
 *
 */
@Service("repositoryMonitorService")
public class FileSystemRepositoryMonitorService implements
		RepositoryMonitorService, RepositoryListener {
	
	private static final Logger LOG = LoggerFactory.getLogger(FileSystemRepositoryMonitorService.class);
	
	private final Base62Converter converter = new Base62Converter();
	private final SortedSet<String> registeredFileNames = Collections.synchronizedSortedSet(new TreeSet<String>(converter.getBase62Comparator()));
	
	private final RepositoryMonitor repositoryMonitor;
	
	private final Path repositoryLocation;

	/**
	 * Creates a new FileSystemRepositoryMonitorService.
	 * @param repositoryLocation the directory to monitor
	 */
	public FileSystemRepositoryMonitorService(final Path repositoryLocation) {
		this.repositoryLocation = repositoryLocation;
		this.repositoryMonitor = new DirectoryMonitor(repositoryLocation);
		repositoryMonitor.addListener(this);
		repositoryMonitor.start();
		try {
			scanRepository();
		} catch (IOException e) {
			throw new RepositoryMonitorException(e);
		}
	}

	@Override
	// TODO MG -- think of a more efficient synchronizing mode
	public synchronized String getNextFreeIdentifier() {
		return converter.getNextBase62Number(registeredFileNames.last());
	}

	@Override
	public void entryCreated(final String name) {
		registeredFileNames.add(stripFileExtension(name));
	}

	@Override
	public void entryDeleted(final String name) {
		// Records for deleted entries are not removed, so that their identifiers never get reassigned
		// Do nothing		
	}
	
	/**
	 * Scans the repository for already existing files and adds them to the register.
	 * @throws IOException when something bad happens
	 */
	private void scanRepository() throws IOException {
		Collection<File> files = FileUtils.listFiles(repositoryLocation.toFile(), null, false);
		List<String> names = files.stream().map(p -> stripFileExtension(p.getName().toString())).collect(
				() -> new ArrayList<String>(), 
				(list, name) -> list.add(name), 
				(list1, list2) -> list1.addAll(list2));
		registeredFileNames.addAll(names);
	}
	
	/**
	 * Strips the file name from the extension, if it's present.
	 * @param fileName the filename string
	 * @return the stripped filename
	 */
	private String stripFileExtension(final String fileName) {
		int dotPosition = fileName.lastIndexOf('.');
		return dotPosition >= 0 ? fileName.substring(0, dotPosition) : fileName;
	}
	
}
