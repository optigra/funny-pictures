package com.optigra.funnypictures.facade.facade.feedback;

import com.optigra.funnypictures.facade.converter.Converter;
import com.optigra.funnypictures.facade.resources.feedback.FeedbackResource;
import com.optigra.funnypictures.model.feedback.Feedback;
import com.optigra.funnypictures.service.feedback.FeedbackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Default implementation of feedback facade.
 * @author ivanursul
 *
 */
@Component("feedbackFacade")
@Transactional
public class DefaultFeedbackFacade implements FeedbackFacade {
	private static final Logger LOG = LoggerFactory.getLogger(DefaultFeedbackFacade.class);
	
	@Resource(name = "feedbackResourceConverter")
	private Converter<FeedbackResource, Feedback> feedbackResourceConverter;

	@Resource(name = "feedbackConverter")
	private Converter<Feedback, FeedbackResource> feedbackConverter;
	
	@Resource(name = "feedbackService")
	private FeedbackService feedbackService;
	
	@Override
	public FeedbackResource createFeedback(final FeedbackResource feedbackResource) {
		LOG.info("Creating feedback: {}", feedbackResource);
		Feedback feedback = feedbackResourceConverter.convert(feedbackResource);
		feedbackService.createFeedback(feedback);
		feedbackService.sendFeedback(feedback);

		return feedbackConverter.convert(feedback);
	}

}
