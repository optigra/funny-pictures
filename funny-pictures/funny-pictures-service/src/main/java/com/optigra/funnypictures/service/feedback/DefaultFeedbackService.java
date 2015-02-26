package com.optigra.funnypictures.service.feedback;

import com.optigra.funnypictures.dao.feedback.FeedbackDao;
import com.optigra.funnypictures.model.feedback.Feedback;
import com.optigra.funnypictures.service.mail.ApplicationMailer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Default implementation of feedback service layer.
 * @author ivanursul
 *
 */
@Service("feedbackService")
public class DefaultFeedbackService implements FeedbackService {
	private static final Logger LOG = LoggerFactory.getLogger(DefaultFeedbackService.class);

	@Value("${feedback.mail}")
	private String receiver;

	@Resource(name = "feedbackDao")
	private FeedbackDao feedbackDao;

	@Resource(name = "applicationMailer")
	private ApplicationMailer applicationMailer;
	
	@Override
	public void createFeedback(final Feedback feedback) {
		LOG.info("Creating feedback entity: {}", feedback);
		feedbackDao.save(feedback);
	}

	@Override
	public void sendFeedback(final Feedback feedback) {
		StringBuilder text = new StringBuilder();
		text.append(feedback.getName())
		.append(" <").append(feedback.getEmail()).append(">\n\n")
		.append(feedback.getText());
		applicationMailer.sendMail(feedback.getEmail(), receiver, feedback.getSubject(), text.toString());
	}

}
