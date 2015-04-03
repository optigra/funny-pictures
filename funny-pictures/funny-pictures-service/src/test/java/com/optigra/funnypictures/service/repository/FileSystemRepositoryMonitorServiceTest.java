package com.optigra.funnypictures.service.repository;

import static org.junit.Assert.assertEquals;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class FileSystemRepositoryMonitorServiceTest {
	
	@Rule
	public TemporaryFolder folder= new TemporaryFolder();
	
	private FileSystemRepositoryMonitorService unit;
	
	private Path repositoryLocation;
	
	@Before
	public void setUp() throws Exception {
		repositoryLocation = folder.newFolder().toPath();
	}
	
	@Test
	public void testGetNextFreeIdentifier() throws Exception {
		// When
		// Start the monitor
		unit = new FileSystemRepositoryMonitorService(repositoryLocation.toString());
		
		// Fire some creation events
		unit.entryCreated("1test.txt");
		unit.entryCreated("2test.txt");
		unit.entryCreated("Atest.txt");
		
		// Then
		assertEquals("Atesu", unit.getNextFreeIdentifier());
	}
	
	@Test
	public void testGetNextFreeIdentifierWithPreexistingFiles() throws Exception {
		// File created before monitor start
		Path newFile = repositoryLocation.resolve("zzzzx.txt");
		Files.createFile(newFile);
		
		// Start the monitor
		unit = new FileSystemRepositoryMonitorService(repositoryLocation.toString());
		
		assertEquals("zzzzy", unit.getNextFreeIdentifier());
		
	}
	
	@Test
	public void testGetNextFreeIdentifierWithDeletions() throws Exception {
		unit = new FileSystemRepositoryMonitorService(repositoryLocation.toString());
		unit.entryCreated("xxxxx.txt");
		unit.entryDeleted("xxxxx.txt");	
		assertEquals("xxxxy", unit.getNextFreeIdentifier());
	}
	
	@Test
	public void testGetNextFreeIdentifierWithNoCreationEvent() throws Exception {
		unit = new FileSystemRepositoryMonitorService(repositoryLocation.toString());
		unit.entryCreated("zzzzy.txt");
		
		// Get next identifier but don't create the new file
		String expected1 = unit.getNextFreeIdentifier();

		// Get another identifier
		String expected2 = unit.getNextFreeIdentifier();

		assertEquals("zzzzz", expected1);
		assertEquals("100000", expected2);
	}

}
