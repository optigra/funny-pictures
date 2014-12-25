package com.optigra.funnypictures.dao.thumbnail;

import org.springframework.stereotype.Repository;

import com.optigra.funnypictures.dao.AbstractDao;
import com.optigra.funnypictures.model.thumbnail.PictureThumbnail;
import com.optigra.funnypictures.pagination.PagedResult;
import com.optigra.funnypictures.pagination.PagedSearch;
import com.optigra.funnypictures.queries.Queries;

/**
 * Picture Thumbnail Dao.
 * @author ivanursul
 *
 */
@Repository("pictureThumbnailDao")
public class DefaultPictureThumbnailDao extends AbstractDao<PictureThumbnail, Long> implements PictureThumbnailDao {

	@Override
	public Class<PictureThumbnail> getEntityClass() {
		return PictureThumbnail.class;
	}

	@Override
	public PagedResult<PictureThumbnail> getThumbnails(
			final PagedSearch<PictureThumbnail> pagedSearch) {
		Queries query = Queries.GET_PICTURE_THUMBNAILS;

		pagedSearch.setClazz(getEntityClass());
		pagedSearch.setParameters(pagedSearch.getParameters());
		pagedSearch.setQuery(query);

		return search(pagedSearch);
	}

}
