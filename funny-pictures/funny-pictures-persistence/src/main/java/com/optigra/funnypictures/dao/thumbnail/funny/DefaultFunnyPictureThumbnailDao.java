package com.optigra.funnypictures.dao.thumbnail.funny;

import org.springframework.stereotype.Repository;

import com.optigra.funnypictures.dao.AbstractDao;
import com.optigra.funnypictures.model.thumbnail.FunnyPictureThumbnail;
import com.optigra.funnypictures.pagination.PagedResult;
import com.optigra.funnypictures.pagination.PagedSearch;
import com.optigra.funnypictures.queries.Queries;

/**
 * Default implementation of funny picture thumbnail dao.
 * @author ivanursul
 *
 */
@Repository("funnyPictureThumbnailDao")
public class DefaultFunnyPictureThumbnailDao extends AbstractDao<FunnyPictureThumbnail, Long> implements FunnyPictureThumbnailDao {

	@Override
	public Class<FunnyPictureThumbnail> getEntityClass() {
		return FunnyPictureThumbnail.class;
	}

	@Override
	public PagedResult<FunnyPictureThumbnail> getThumbnails(
			final PagedSearch<FunnyPictureThumbnail> pagedSearch) {
		
		Queries query = Queries.GET_FUNNY_PICTURE_THUMBNAILS;

		pagedSearch.setClazz(getEntityClass());
		pagedSearch.setParameters(pagedSearch.getParameters());
		pagedSearch.setQuery(query);

		return search(pagedSearch);
	}

	@Override
	public PagedResult<FunnyPictureThumbnail> getThumbnailsByPicture(
			final PagedSearch<FunnyPictureThumbnail> pagedSearch) {
		
		Queries query = Queries.FIND_FUNNY_PICTURE_THUMBNAILS_BY_PICTURE;

		pagedSearch.setClazz(getEntityClass());
		pagedSearch.setParameters(pagedSearch.getParameters());
		pagedSearch.setQuery(query);

		return search(pagedSearch);
	}

}
