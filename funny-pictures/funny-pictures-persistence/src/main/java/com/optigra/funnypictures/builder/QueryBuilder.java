package com.optigra.funnypictures.builder;

import com.optigra.funnypictures.queries.Queries;

/**
 * Base interface for all query builders.
 * @author ivanursul
 *
 * @param <C> Entity as context.
 */
public interface QueryBuilder<C> {

	/**
	 * Method for building query.
	 * @param context
	 * @return Query instance.
	 */
	Queries build(C context);
}
