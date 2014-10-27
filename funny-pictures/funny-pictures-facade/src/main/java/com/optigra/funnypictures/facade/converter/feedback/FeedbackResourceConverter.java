package com.optigra.funnypictures.facade.converter.feedback;

import org.springframework.stereotype.Component;

import com.optigra.funnypictures.facade.converter.AbstractConverter;
import com.optigra.funnypictures.facade.resources.feedback.FeedbackResource;
import com.optigra.funnypictures.model.feedback.Feedback;

/**
 * Converter, that converts from FeedbackResource to Feedback.
 * Note, that this converter do not convert Identifiers.
 * This was done because some methods, for example create
 * methods don't need such id, and update methods are
 * designed in the way, that u are updating only fields, 
 * that are not identifiers.
 * 
 * @author ivanursul
 *
 */
@Component("feedbackResourceConverter")
public class FeedbackResourceConverter extends AbstractConverter<FeedbackResource, Feedback> {

	@Override
	public Feedback convert(final FeedbackResource source, final Feedback target) {
		
		target.setEmail(source.getEmail());
		target.setSubject(source.getSubject());
		target.setName(source.getName());
		target.setText(source.getText());
		
		return target;
	}

	@Override
	public Feedback convert(final FeedbackResource source) {
		return convert(source, new Feedback());
	}

}
