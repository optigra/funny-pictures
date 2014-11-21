package com.optigra.funnypictures.web.feedback;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.optigra.funnypictures.facade.facade.feedback.FeedbackFacade;
import com.optigra.funnypictures.facade.resources.feedback.FeedbackResource;
import com.optigra.funnypictures.web.AbstractControllerTest;

@RunWith(MockitoJUnitRunner.class)
public class FeedbackControllerTest extends AbstractControllerTest {

	@Mock
	private FeedbackFacade feedbackFacade;
	
	@InjectMocks
	private FeedbackController unit;
	
	private MockMvc mockMvc;
	
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(unit).build();
	}
	
	@Test
	public void testCreateFeedback() throws Exception {
		// Given
		String email = "Ivanon2@gmail.com";
		String name = "Ivan Ursul";
		String text = "Good app, guys.";
		FeedbackResource feedbackResource = new FeedbackResource();
		feedbackResource.setName(name);
		feedbackResource.setText(text);
		feedbackResource.setEmail(email);

		// When
		String request = getJson(feedbackResource, true);
		String response = getJson(feedbackResource, false);
		
		when(feedbackFacade.createFeedback(any(FeedbackResource.class))).thenReturn(feedbackResource);
		
		// Then
		mockMvc.perform(post("/feedbacks")
				.contentType(MediaType.APPLICATION_JSON)
				.content(request))
			.andExpect(status().isCreated())
			.andExpect(content().string(response));
		
		verify(feedbackFacade).createFeedback(feedbackResource);
	}
}
