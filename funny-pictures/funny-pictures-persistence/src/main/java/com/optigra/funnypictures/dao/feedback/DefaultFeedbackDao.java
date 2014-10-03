package com.optigra.funnypictures.dao.feedback;

import org.springframework.stereotype.Repository;

import com.optigra.funnypictures.dao.AbstractDao;
import com.optigra.funnypictures.model.feedback.Feedback;

/**
 * Default implementation of Dao layer for Feedback entity.
 * @author ivanursul
 *
 */
@Repository("feedbackDao")
public class DefaultFeedbackDao extends AbstractDao<Feedback, Long> implements FeedbackDao {

	@Override
	public Class<Feedback> getEntityClass() {
		return Feedback.class;
	}

}
