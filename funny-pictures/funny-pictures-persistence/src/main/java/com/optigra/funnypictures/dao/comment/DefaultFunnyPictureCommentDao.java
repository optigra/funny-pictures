package com.optigra.funnypictures.dao.comment;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.optigra.funnypictures.builder.QueryBuilder;
import com.optigra.funnypictures.dao.AbstractDao;
import com.optigra.funnypictures.model.comment.FunnyPictureComment;

/**
 * 
 * @author ivanursul
 *
 */
@Repository("funnyPictureCommentDao")
public class DefaultFunnyPictureCommentDao extends AbstractDao<FunnyPictureComment, Long> implements FunnyPictureCommentDao {

	@Override
	public Class<FunnyPictureComment> getEntityClass() {
		return FunnyPictureComment.class;
	}

	@Override
	@Resource(name = "funnyPictureCommentQueryBuilder")
	public void setQueryBuilder(final QueryBuilder<FunnyPictureComment> queryBuilder) {
		super.setQueryBuilder(queryBuilder);
	}

}
