package com.optigra.funnypictures.service.feedback;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.optigra.funnypictures.dao.feedback.FeedbackDao;
import com.optigra.funnypictures.model.feedback.Feedback;

@RunWith(MockitoJUnitRunner.class)
public class DefaultFeedbackServiceTest {

	@Mock
	private FeedbackDao feedbackDao;
	
	@InjectMocks
	private DefaultFeedbackService unit;
	
	@Test
	public void testCreateFeedback() throws Exception {
		// Given
		String email = "Ivanon2@gmail.com";
		String name = "Ivan Ursul";
		String text = "Good app, guys.";

		Feedback feedback = new Feedback();
		feedback.setName(name);
		feedback.setText(text);
		feedback.setEmail(email);

		// When
		unit.createFeedback(feedback);

		// Then
		verify(feedbackDao).save(feedback);
	}
}
