package com.optigra.funnypictures.service.repository.monitor;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.Watchable;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

@RunWith(MockitoJUnitRunner.class)
public class DirectoryMonitorTaskTest {
	
	private DirectoryMonitorTask unit;
	
	@Mock
	private Path repositoryLocation;
	
	private Path testFile;
	
	@Mock
	private RepositoryListener listener;
	
	@Mock
	private WatchService directoryWatchService; 
	
	@Mock
	private TestWatchKey watchKey;
	
	@Mock
	private WatchEvent<Path> creationWatchEvent;
	
	@Mock
	private WatchEvent<Path> deletionWatchEvent;
	
	@Rule
	public TemporaryFolder folder= new TemporaryFolder();
	
	@Before
	public void setUp() throws Exception {
		
		testFile = folder.newFile("test").toPath();
		
		when(repositoryLocation.register(directoryWatchService, ENTRY_CREATE, ENTRY_DELETE)).thenAnswer(new Answer<WatchKey>() {

			@Override
			public WatchKey answer(final InvocationOnMock invocation) throws Throwable {
				return watchKey;
			}
			
		});
		when(repositoryLocation.resolve(any(Path.class))).thenReturn(testFile);
		when(creationWatchEvent.context()).thenReturn(testFile);
		when(creationWatchEvent.kind()).thenReturn(ENTRY_CREATE);
		when(deletionWatchEvent.context()).thenReturn(testFile);
		when(deletionWatchEvent.kind()).thenReturn(ENTRY_DELETE);
		when(watchKey.pollEvents()).thenReturn(Arrays.asList(creationWatchEvent, deletionWatchEvent));
		when(directoryWatchService.take()).thenReturn(watchKey);
		
		unit = new DirectoryMonitorTask(directoryWatchService, repositoryLocation);
		unit.addListener(listener);
		unit.afterPropertiesSet();
		
	}
	
	@After
	public void tearDown() throws Exception {
		unit.stop();
		unit.destroy();
	}
	
	@Test(timeout=20000)
	public void testMonitor() throws Exception {
		
		Executors.newSingleThreadExecutor().execute(() -> {
			try {
				Thread.sleep(5000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			unit.stop();
		});
		unit.run();
		
		verify(repositoryLocation).register(directoryWatchService, ENTRY_CREATE, ENTRY_DELETE);
		verify(listener).entryCreated(testFile.toString());
		verify(listener).entryDeleted(testFile.toString());
	}
	
	private static class TestWatchKey implements WatchKey {
		@Override
		public boolean isValid() {
			return true;
		}

		@Override
		public List<WatchEvent<?>> pollEvents() {
			return null;
		}

		@Override
		public boolean reset() {
			return false;
		}

		@Override
		public void cancel() {
		}

		@Override
		public Watchable watchable() {
			return null;
		}

	}

}
