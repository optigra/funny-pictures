package com.optigra.funnypictures.facade.converter.feedback;

import org.springframework.stereotype.Component;

import com.optigra.funnypictures.facade.converter.AbstractConverter;
import com.optigra.funnypictures.facade.resources.feedback.FeedbackResource;
import com.optigra.funnypictures.model.feedback.Feedback;

/**
 * Converter, that converts from Feedback to FeedbackResource.
 * @author ivanursul
 *
 */
@Component("feedbackConverter")
public class FeedbackConverter extends AbstractConverter<Feedback, FeedbackResource> {

	@Override
	public FeedbackResource convert(final Feedback source, final FeedbackResource target) {
		
		target.setEmail(source.getEmail());
		target.setId(source.getId());
		target.setSubject(source.getSubject());
		target.setName(source.getName());
		target.setText(source.getText());
		
		return target;
	}

	@Override
	public FeedbackResource convert(final Feedback source) {
		return convert(source, new FeedbackResource());
	}

}
