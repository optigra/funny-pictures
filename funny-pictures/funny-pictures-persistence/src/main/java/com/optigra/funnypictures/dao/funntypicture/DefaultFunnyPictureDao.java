package com.optigra.funnypictures.dao.funntypicture;

import java.util.Collections;

import org.springframework.stereotype.Repository;

import com.optigra.funnypictures.dao.AbstractDao;
import com.optigra.funnypictures.model.FunnyPicture;
import com.optigra.funnypictures.pagination.PagedResult;
import com.optigra.funnypictures.pagination.PagedSearch;
import com.optigra.funnypictures.queries.Queries;

/**
 * DAO class to get PagedResult of FunnyPictures.
 * 
 * @author oyats
 *
 */
@Repository("funnyPictureDao")
public class DefaultFunnyPictureDao extends AbstractDao<FunnyPicture, Long> implements FunnyPictureDao {

	@Override
	public PagedResult<FunnyPicture> getFunnyPictures(final PagedSearch<FunnyPicture> pagedSearch) {

		Queries query = Queries.FIND_FUNNY_PICTURES;

		pagedSearch.setClazz(getEntityClass());
		pagedSearch.setParameters(Collections.<String, Object>emptyMap());
		pagedSearch.setQuery(query);

		return search(pagedSearch);
	}

	@Override
	public Class<FunnyPicture> getEntityClass() {
		return FunnyPicture.class;
	}

}
