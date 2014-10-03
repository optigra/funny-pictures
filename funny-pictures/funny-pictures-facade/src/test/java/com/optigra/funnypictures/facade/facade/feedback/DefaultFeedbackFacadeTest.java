package com.optigra.funnypictures.facade.facade.feedback;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.optigra.funnypictures.facade.converter.Converter;
import com.optigra.funnypictures.facade.resources.feedback.FeedbackResource;
import com.optigra.funnypictures.model.feedback.Feedback;
import com.optigra.funnypictures.service.feedback.FeedbackService;

@RunWith(MockitoJUnitRunner.class)
public class DefaultFeedbackFacadeTest {

	@Mock
	private Converter<FeedbackResource, Feedback> feedbackResourceConverter;
	
	@Mock
	private Converter<Feedback, FeedbackResource> feedbackConverter;
	
	@Mock
	private FeedbackService feedbackService;
	
	@InjectMocks
	private DefaultFeedbackFacade unit;
	
	@Test
	public void testCreateFeedback() throws Exception {
		// Given
		String email = "Ivanon2@gmail.com";
		String name = "Ivan Ursul";
		String text = "Good app, guys.";
		FeedbackResource expected = new FeedbackResource();
		expected.setName(name);
		expected.setText(text);
		expected.setEmail(email);
		
		Feedback feedback = new Feedback();
		feedback.setName(name);
		feedback.setText(text);
		feedback.setEmail(email);
		
		// When
		when(feedbackResourceConverter.convert(any(FeedbackResource.class))).thenReturn(feedback);
		when(feedbackConverter.convert(any(Feedback.class))).thenReturn(expected);
		
		FeedbackResource actual = unit.createFeedback(expected);

		// Then
		verify(feedbackResourceConverter).convert(expected);
		verify(feedbackService).createFeedback(feedback);
		assertEquals(expected, actual);
	}
}
