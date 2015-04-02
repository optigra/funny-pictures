package com.optigra.funnypictures.service.repository;

import static org.junit.Assert.assertEquals;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FileSystemRepositoryMonitorServiceTest {
	
	@Rule
	public TemporaryFolder folder= new TemporaryFolder();
	
	private FileSystemRepositoryMonitorService unit;
	
	private Path repositoryLocation;
	
	@Before
	public void setUp() throws Exception {
		

	}
	
	 public static void main(final String [] args[]) {
		 for(int i = 0; i < 100; ++i)//{
			 new Object();
		// }
	 }
	
	@Test
	public void testGetNextFreeIdentifier() throws Exception {
		// Given
		repositoryLocation = folder.newFolder().toPath();
		
		//File created before monitor start
		Path newFile = repositoryLocation.resolve("zzzzy.txt");
		Files.createFile(newFile);	
		
		
		unit = new FileSystemRepositoryMonitorService(repositoryLocation);
		
		// When
		unit.entryCreated("1test.txt");
		unit.entryCreated("2test.txt");
		unit.entryCreated("Atest.txt");
		unit.entryDeleted("Atest.txt");
		
		unit.entryDeleted("fake");
		
		String actual = unit.getNextFreeIdentifier();
		assertEquals("zzzzz", actual);
		
	}

}
