package com.optigra.funnypictures.service.repository.monitor;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.optigra.funnypictures.service.repository.monitor.DirectoryMonitor;
import com.optigra.funnypictures.service.repository.monitor.RepositoryListener;

@RunWith(MockitoJUnitRunner.class)
public class DirectoryMonitorTest {
	
	private DirectoryMonitor unit;
	
	private Path repositoryLocation;
	
	@Mock
	private RepositoryListener listener;
	
	@Rule
	public TemporaryFolder folder= new TemporaryFolder();
	
	@Before
	public void setUp() throws Exception {
		repositoryLocation = folder.newFolder().toPath();
		unit = spy(new DirectoryMonitor(repositoryLocation));
		unit.start();
		unit.addListener(listener);
	}
	
	@After
	public void tearDown() throws Exception {
		unit.stop();
	}
	
	@Test
	public void testMonitor() throws Exception {
		String expected = "test.txt";
		Path newFile = repositoryLocation.resolve(expected);
		Files.createFile(newFile);
		Files.delete(newFile);
		// Give some time for the monitor to notice the changes
		Thread.sleep(5000);
		verify(listener).entryCreated(expected);
		verify(listener).entryDeleted(expected);
	}

}
