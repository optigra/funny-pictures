package com.optigra.funnypictures.generator.util;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.awt.Dimension;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.im4java.core.CompareCmd;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;
import org.im4java.process.ArrayListErrorConsumer;
import org.im4java.process.ArrayListOutputConsumer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import com.optigra.funnypictures.generator.util.ImageInformationExtractor;

@RunWith(MockitoJUnitRunner.class)
public class ImageInformationExtractorTest {
	
	
	
	@Mock
	private ConvertCmd convertCommand;
	
	@Mock
	private CompareCmd compareCommand;
	
	@Captor
	ArgumentCaptor<IMOperation> operationCaptor;
	
	@Captor
	ArgumentCaptor<ArrayListOutputConsumer> outputConsumerCaptor;
	
	@Captor
	ArgumentCaptor<ArrayListErrorConsumer> errorConsumerCaptor;
	
	@InjectMocks
	private ImageInformationExtractor unit;
	
	@Test
	//TODO fix this test. The actual method looks OK.
	public void testGetImageDimension() throws Exception {
		//Given
		Path path = Paths.get("filename");
		String mockOutput = "500 400";
		final InputStream mockIstream = new ByteArrayInputStream(mockOutput.getBytes());
		doAnswer(new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
				ArrayListOutputConsumer consumer = (ArrayListOutputConsumer) args[0];
				consumer.consumeOutput(mockIstream);
				return null;
			}
		}).when(convertCommand).setOutputConsumer(any(ArrayListOutputConsumer.class));
		
		//When
		Dimension actualResult = unit.getImageDimension(path);
		
		//Then
		verify(convertCommand).setOutputConsumer(outputConsumerCaptor.capture());
		verify(convertCommand).run(operationCaptor.capture(), matches("filename"));
		
		String expectedCommand = "?img? -ping -format %w %h info: ";
		assertEquals(expectedCommand, operationCaptor.getValue().toString());	
		Dimension expectedResult = new Dimension(500, 400);
		assertEquals(expectedResult, actualResult);
		
	}
	
	@Test
	public void testCalculateNormalizedRmseDifference() throws Exception {
		//Given
		Path path1 = Paths.get("filename1");
		Path path2 = Paths.get("filename2");
		String mockOutput = "(0.56)";
		final InputStream mockIstream = new ByteArrayInputStream(mockOutput.getBytes());
		doAnswer(new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
				ArrayListErrorConsumer consumer = (ArrayListErrorConsumer) args[0];
				consumer.consumeError(mockIstream);
				return null;
			}
		}).when(compareCommand).setErrorConsumer(any(ArrayListErrorConsumer.class));
		
		//When
		double actualResult = unit.calculateNormalizedRmseDifference(path1, path2);
		
		//Then
		verify(compareCommand).setErrorConsumer(errorConsumerCaptor.capture());
		verify(compareCommand).run(operationCaptor.capture(), anyVararg());
		
		String expectedCommand = "-quiet -metric RMSE ?img? ?img? null: ";
		assertEquals(expectedCommand, operationCaptor.getValue().toString());	
		double expectedResult = .56;
		assertEquals(expectedResult, actualResult, 0);
	}

}
