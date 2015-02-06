package com.optigra.funnypictures.dao.thumbnail;

import org.springframework.stereotype.Repository;

import com.optigra.funnypictures.dao.AbstractDao;
import com.optigra.funnypictures.pagination.PagedResult;
import com.optigra.funnypictures.pagination.PagedSearch;
import com.optigra.funnypictures.queries.Queries;
import com.optigra.funnypictures.view.thumbnail.RandomPictureThumbnailView;

/**
 * Random Picture Thumbnail Dao.
 * @author oleh.zasadnyy
 *
 */
@Repository("randomPictureThumbnailDao")
public class DefaultRandomPictureThumbnailDao extends AbstractDao<RandomPictureThumbnailView, Long> implements RandomPictureThumbnailDao{


	@Override
	public Class<RandomPictureThumbnailView> getEntityClass() {
		return RandomPictureThumbnailView.class;
	}
	@Override
	public PagedResult<RandomPictureThumbnailView> getRandomThumbnails(
			final PagedSearch<RandomPictureThumbnailView> pagedSearch) {
		Queries query = Queries.GET_RANDOM_PICTURE_THUMBNAILS;

		pagedSearch.setClazz(getEntityClass());
		pagedSearch.setParameters(pagedSearch.getParameters());
		pagedSearch.setQuery(query);

		return search(pagedSearch);
	}
}
