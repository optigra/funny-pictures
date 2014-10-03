package com.optigra.funnypictures.facade.converter.feedback;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.optigra.funnypictures.facade.resources.feedback.FeedbackResource;
import com.optigra.funnypictures.model.feedback.Feedback;


public class FeedbackConverterTest {

	private FeedbackConverter unit = new FeedbackConverter();

	@Test
	public void testConvert() throws Exception {
		// Given
		Long id = 1L;
		String name = "Name";
		String email = "email";
		String text = "text";
		String subject = "subject";
		
		Feedback source = new Feedback();
		source.setEmail(email);
		source.setId(id);
		source.setName(name);
		source.setText(text);
		source.setSubject(subject);

		FeedbackResource expected = new FeedbackResource();
		expected.setEmail(email);
		expected.setId(id);
		expected.setName(name);
		expected.setText(text);
		expected.setSubject(subject);
		
		// When
		FeedbackResource actual = unit.convert(source);

		// Then
		assertEquals(expected, actual);
	}
}
