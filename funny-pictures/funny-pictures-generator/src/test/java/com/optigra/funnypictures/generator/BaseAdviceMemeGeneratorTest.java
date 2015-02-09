package com.optigra.funnypictures.generator;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyVararg;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.Dimension;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

import org.im4java.core.CompositeCmd;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;
import org.im4java.process.OutputConsumer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.optigra.funnypictures.generator.api.AdviceMemeContext;
import com.optigra.funnypictures.generator.util.ImageInformationExtractor;
import com.optigra.funnypictures.model.content.MimeType;

@RunWith(MockitoJUnitRunner.class)
public class BaseAdviceMemeGeneratorTest {

	@InjectMocks
	private BaseAdviceMemeGenerator unit;

	@Mock
	private ConvertCmd convertCommand;
	
	@Mock
	private CompositeCmd compositeCommand;
	
	@Mock
	private ImageInformationExtractor imageInfoExtractor;
	
	@Captor
	private ArgumentCaptor<IMOperation> convertOperationCaptor;
	
	@Captor
	private ArgumentCaptor<OutputConsumer> outputConsumerCaptor;
	
	@Before
	public void setUp() throws IOException {
		//Mockito doesn't set outputFormat, so we need to set it manually
		unit.setOutputFormat(BaseAdviceMemeGenerator.DEFAULT_OUTPUT_FORMAT);
	}

	@After
	public void tearDown() throws IOException {

	}

	@Test
	public void testGenerate() throws Exception {
		// Given
		InputStream templateInputStream = new ByteArrayInputStream(new byte[]{});
		AdviceMemeContext context = new AdviceMemeContext(templateInputStream, MimeType.IMAGE_JPEG_JPG, "Top caption", "Bottom caption");

		//When
		when(imageInfoExtractor.getImageDimension(any(Path.class))).thenReturn(new Dimension(400, 400));
		unit.generate(context);		

		// Then
		verify(convertCommand, times(4)).run(convertOperationCaptor.capture(), anyVararg());
		
		String expectedConvertCommand = "?img? ?img? ";
		
		String expectedConvertTopCommand = "?img? ( -size 400x80 -fill white -stroke black -strokewidth 2 " +
				"-background none -gravity Center -font Impact-Regular caption:Top caption ( +clone -shadow 100x10.0+0+0 ) " + 
				"+swap -layers merge ) -gravity North -composite ?img? ";
		String expectedConvertBottomCommand = "?img? ( -size 400x80 -fill white -stroke black -strokewidth 2 " + 
				"-background none -gravity Center -font Impact-Regular caption:Bottom caption ( +clone -shadow 100x10.0+0+0 ) " + 
				"+swap -layers merge ) -gravity South -composite ?img? ";
		
		assertEquals(expectedConvertCommand, convertOperationCaptor.getAllValues().get(0).toString());
		assertEquals(expectedConvertTopCommand, convertOperationCaptor.getAllValues().get(1).toString());
		assertEquals(expectedConvertBottomCommand, convertOperationCaptor.getAllValues().get(2).toString());
		assertEquals(expectedConvertCommand, convertOperationCaptor.getAllValues().get(3).toString());
	}
}
