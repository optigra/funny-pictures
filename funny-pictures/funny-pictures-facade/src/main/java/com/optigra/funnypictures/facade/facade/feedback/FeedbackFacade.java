package com.optigra.funnypictures.facade.facade.feedback;

import com.optigra.funnypictures.facade.resources.feedback.FeedbackResource;

/**
 * Facade for all operations, connected with feedback entity.
 * @author ivanursul
 *
 */
public interface FeedbackFacade {

	/**
	 * Created feedback in our system.
	 * @param feedbackResource resource.
	 * @return Resource with generated identifier.
	 */
	FeedbackResource createFeedback(FeedbackResource feedbackResource);

}
