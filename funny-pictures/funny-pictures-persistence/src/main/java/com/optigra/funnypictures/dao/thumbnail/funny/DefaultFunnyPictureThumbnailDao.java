package com.optigra.funnypictures.dao.thumbnail.funny;

import com.optigra.funnypictures.dao.AbstractDao;
import com.optigra.funnypictures.model.thumbnail.FunnyPictureThumbnail;
import com.optigra.funnypictures.model.thumbnail.ThumbnailType;
import com.optigra.funnypictures.pagination.PagedResult;
import com.optigra.funnypictures.pagination.PagedSearch;
import com.optigra.funnypictures.queries.Queries;
import com.optigra.funnypictures.queries.Query;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

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

	@Override
	public FunnyPictureThumbnail getThumbnailForFunnyPicture(final Long funnyPictureId, final ThumbnailType thumbnailType) {
		Queries query = Queries.FIND_FUNNY_PICTURE_THUMBNAILS_BY_FUNNY_PICTURE;

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("funnyPictureId", funnyPictureId);
		parameters.put("type", thumbnailType);
		Query<FunnyPictureThumbnail> finalQuery = new Query<>(getEntityClass(), query.getQuery(), parameters);

		return getPersistenceManager().executeSingleResultQuery(finalQuery);
	}

}
