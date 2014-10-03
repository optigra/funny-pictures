package com.optigra.funnypictures.web.feedback;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.optigra.funnypictures.facade.facade.feedback.FeedbackFacade;
import com.optigra.funnypictures.facade.resources.feedback.FeedbackResource;

/**
 * Controller, that handles all feedback API's.
 * Feedback - can be described as a entity of
 * Contact Form.
 * @author ivanursul
 *
 */
@RestController
@RequestMapping(value = "/feedbacks")
public class FeedbackController {
	private static final Logger LOG = LoggerFactory.getLogger(FeedbackController.class);
	
	@Resource(name = "feedbackFacade")
	private FeedbackFacade feedbackFacade;
	
	/**
	 * POST method for creating feedback.
	 * @param feedbackResource resource, that will be stored in system.
	 * @return Resource with generated identifier.
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public FeedbackResource createFeedback(@RequestBody final FeedbackResource feedbackResource) {
		LOG.info("Creating feedback: {}", feedbackResource);
		
		return feedbackFacade.createFeedback(feedbackResource);
	}
	
}
