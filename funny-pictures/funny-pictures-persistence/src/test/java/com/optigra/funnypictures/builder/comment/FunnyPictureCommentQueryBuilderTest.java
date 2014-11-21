package com.optigra.funnypictures.builder.comment;


import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.optigra.funnypictures.model.comment.FunnyPictureComment;
import com.optigra.funnypictures.queries.Queries;


public class FunnyPictureCommentQueryBuilderTest {

	private FunnyPictureCommentQueryBuilder unit = new FunnyPictureCommentQueryBuilder();
	
	@Test
	public void testBuild() throws Exception {
		// Given
		String author = "author";
		
		FunnyPictureComment context = new FunnyPictureComment();
		context.setAuthor(author);
		
		String expectedQuery = "SELECT f FROM FunnyPictureComment f WHERE f.author LIKE CONCAT('%',:author,'%') ";
		
		// When
		Queries actual = unit.build(context);
		String actualQuery = actual.getQuery();
		
		// Then
		assertEquals(expectedQuery, actualQuery);
	}
}
