package com.optigra.funnypictures.service.feedback;

import com.optigra.funnypictures.model.feedback.Feedback;

/**
 * FeedbackService interface, that describes all methods for
 * service layer.
 * @author ivanursul
 *
 */
public interface FeedbackService {

	/**
	 * Method for creating feedback.
	 * @param feedback feedback
	 */
	void createFeedback(Feedback feedback);

	/**
	 * Method for sending feedback to support email.
	 * @param feedback feedback
	 */
	void sendFeedback(Feedback feedback);

}
