package com.optigra.funnypictures.facade.converter.feedback;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.optigra.funnypictures.facade.resources.feedback.FeedbackResource;
import com.optigra.funnypictures.model.feedback.Feedback;

public class FeedbackResourceConverterTest {

	private FeedbackResourceConverter unit = new FeedbackResourceConverter();
	
	@Test
	public void testConvert() throws Exception {
		// Given
		Long id = 1L;
		String name = "Name";
		String email = "email";
		String text = "text";
		String subject = "subject";
		
		Feedback expected = new Feedback();
		expected.setEmail(email);
		expected.setName(name);
		expected.setText(text);
		expected.setSubject(subject);

		FeedbackResource source = new FeedbackResource();
		source.setEmail(email);
		source.setId(id);
		source.setName(name);
		source.setText(text);
		source.setSubject(subject);
		
		// When
		Feedback actual = unit.convert(source);

		// Then
		assertEquals(expected, actual);
	}
}
