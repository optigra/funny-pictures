package org.funny.pictures.generator.util;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.matches;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.awt.Dimension;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;
import org.im4java.process.ArrayListOutputConsumer;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ImageInformationExtractorTest {
	
	@Mock
	private ConvertCmd convertCommand;
	
	@Captor
	ArgumentCaptor<IMOperation> operationCaptor;
	
	@Captor
	ArgumentCaptor<ArrayListOutputConsumer> consumerCaptor;
	
	@InjectMocks
	private ImageInformationExtractor unit;
	
	@Ignore
	@Test
	//TODO fix this test. The actual method looks OK.
	public void testGetImageDimension() throws Exception {
		//Given
		Path path = Paths.get("filename");
		
		
		//When
		Dimension actualResult = unit.getImageDimension(path);
		
		//Then
		verify(convertCommand).setOutputConsumer(consumerCaptor.capture());
		verify(convertCommand).run(operationCaptor.capture(), matches("filename"));
		ArrayListOutputConsumer consumer = spy(consumerCaptor.getValue());
		doReturn(new ArrayList<String>(Arrays.asList("500 400"))).when(consumer).getOutput();
		verify(consumer.getOutput());
		
		String expectedCommand = "";
		assertEquals(expectedCommand, operationCaptor.getValue().toString());	
		Dimension expectedResult = new Dimension(500, 400);
		assertEquals(expectedResult, actualResult);
		
		
	}

}
