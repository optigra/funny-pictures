package com.optigra.funnypictures.generator;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyVararg;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.awt.Rectangle;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

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

import com.optigra.funnypictures.generator.api.ComicContext;
import com.optigra.funnypictures.model.content.MimeType;

@RunWith(MockitoJUnitRunner.class)
public class BaseComicGeneratorTest {
	
	@Mock
	private ConvertCmd convertCommand;
	
	@Captor
	private ArgumentCaptor<IMOperation> convertOperationCaptor;
	
	@Captor
	private ArgumentCaptor<OutputConsumer> outputConsumerCaptor;
	
	@InjectMocks
	private BaseComicGenerator unit;
	
	@Before
	public void setUp() throws IOException {
		//Mockito doesn't set outputFormat, so we need to set it manually
		unit.setOutputFormat(BaseComicGenerator.DEFAULT_OUTPUT_FORMAT);
	}

	@After
	public void tearDown() throws IOException {

	}
	
	@Test
	public void testGenerate() throws Exception {
		// Given
		InputStream templateInputStream = new ByteArrayInputStream(new byte[]{});
		Rectangle rectangle1 = new Rectangle(10, 20, 100, 50);
		String caption1 = "Lorem ipsum dolor sit amet";
		Rectangle rectangle2 = new Rectangle(110, 40, 40, 50);
		String caption2 = "?\\/|'\" -abc";
		Map<Rectangle, String> blocksMap = new HashMap<Rectangle, String>();
		blocksMap.put(rectangle1, caption1);
		blocksMap.put(rectangle2, caption2);
		ComicContext context = new ComicContext(templateInputStream, MimeType.IMAGE_JPEG_JPG, blocksMap);
		
		// When
		unit.generate(context);
		
		// Then
		verify(convertCommand, times(4)).run(convertOperationCaptor.capture(), anyVararg());
		
		String expectedConvertCommand1 = "?img? ?img? ";
		String expectedConvertCommand2 = "?img? -fill white -draw roundrectangle 10,20,110,70,10,10 " +
				"-fill black -background transparent -gravity center -size 100x50 -caption Lorem ipsum dolor sit amet " +
				"-geometry 100x50+10+20 -gravity none -composite ?img? ";
		String expectedConvertCommand3 = "?img? -fill white -draw roundrectangle 110,40,150,90,10,10 " +
				"-fill black -background transparent -gravity center -size 40x50 -caption ?\\/|'\" -abc " +
				"-geometry 40x50+110+40 -gravity none -composite ?img? ";
		String expectedConvertCommand4 = "?img? ?img? ";
		
		assertEquals(expectedConvertCommand1, convertOperationCaptor.getAllValues().get(0).toString());
		assertEquals(expectedConvertCommand2, convertOperationCaptor.getAllValues().get(1).toString());
		assertEquals(expectedConvertCommand3, convertOperationCaptor.getAllValues().get(2).toString());
		assertEquals(expectedConvertCommand4, convertOperationCaptor.getAllValues().get(3).toString());
		
	}

}
