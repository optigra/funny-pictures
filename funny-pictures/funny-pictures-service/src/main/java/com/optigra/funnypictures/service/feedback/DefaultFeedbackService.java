package com.optigra.funnypictures.service.feedback;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.optigra.funnypictures.dao.feedback.FeedbackDao;
import com.optigra.funnypictures.model.feedback.Feedback;

/**
 * Default implementation of feedback service layer.
 * @author ivanursul
 *
 */
@Service("feedbackService")
public class DefaultFeedbackService implements FeedbackService {
	private static final Logger LOG = LoggerFactory.getLogger(DefaultFeedbackService.class);
	
	@Resource(name = "feedbackDao")
	private FeedbackDao feedbackDao;
	
	@Override
	public void createFeedback(final Feedback feedback) {
		LOG.info("Creating feedback entity: {}", feedback);
		feedbackDao.save(feedback);
	}

}
