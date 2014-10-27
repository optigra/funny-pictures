package com.optigra.funnypictures.dao.picture;

import java.util.Collections;

import org.springframework.stereotype.Repository;

import com.optigra.funnypictures.dao.AbstractDao;
import com.optigra.funnypictures.model.Picture;
import com.optigra.funnypictures.pagination.PagedResult;
import com.optigra.funnypictures.pagination.PagedSearch;
import com.optigra.funnypictures.queries.Queries;

/**
 * DAO class to access entity class and get pictures.
 * 
 * @author oyats
 *
 */
@Repository("pictureDao")
public class DefaultPictureDao extends AbstractDao<Picture, Long> implements PictureDao {

	@Override
	public Class<Picture> getEntityClass() {
		return Picture.class;
	}

	@Override
	public PagedResult<Picture> getPictures(final PagedSearch<Picture> pagedSearch) {

		Queries query = Queries.FIND_PICTURES;

		pagedSearch.setClazz(getEntityClass());
		pagedSearch.setParameters(Collections.<String, Object>emptyMap());
		pagedSearch.setQuery(query);

		return search(pagedSearch);
	}

}
