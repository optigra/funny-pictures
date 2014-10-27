package com.optigra.funnypictures.dao.thumbnail;

import org.springframework.stereotype.Repository;

import com.optigra.funnypictures.dao.AbstractDao;
import com.optigra.funnypictures.model.thumbnail.Thumbnail;

/**
 * Default implementation of Thumbnail dao.
 * @author ivanursul
 *
 */
@Repository("thumbnailDao")
public class DefaultThumbnailDao extends AbstractDao<Thumbnail, Long> implements ThumbnailDao {

	@Override
	public Class<Thumbnail> getEntityClass() {
		return Thumbnail.class;
	}

}
