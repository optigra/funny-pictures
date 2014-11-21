package com.optigra.funnypictures.builder.comment;

import org.springframework.stereotype.Component;

import com.optigra.funnypictures.builder.BaseQueryBuilder;
import com.optigra.funnypictures.builder.QueryBuilder;
import com.optigra.funnypictures.model.comment.FunnyPictureComment;
import com.optigra.funnypictures.queries.Queries;

/**
 * 
 * @author ivanursul
 *
 */
@Component("funnyPictureCommentQueryBuilder")
public class FunnyPictureCommentQueryBuilder implements QueryBuilder<FunnyPictureComment> {

	private static final String QUERY_NAME = "FunnyPictureCommentQueryBuilder";
	private static final String QUERY = "SELECT f FROM FunnyPictureComment f %s";

	private static final String PARENT_CONDITION = "f.parent = :parent ";
	private static final String FUNNY_PICTURE_CONDITION = "f.funnyPicture = :funnyPicture ";
	private static final String AUTHOR_CONDITION = "f.author LIKE CONCAT('%',:author,'%') ";
	
	
	@Override
	public Queries build(final FunnyPictureComment context) {
		
		String query = BaseQueryBuilder.getInstance(QUERY)
				.where()
				.addOrCondition(PARENT_CONDITION, context.getParent())
				.addOrCondition(FUNNY_PICTURE_CONDITION, context.getFunnyPicture())
				.addOrCondition(AUTHOR_CONDITION, context.getAuthor())
				.build();
		
		return new Queries(QUERY_NAME, query);
	}

}
