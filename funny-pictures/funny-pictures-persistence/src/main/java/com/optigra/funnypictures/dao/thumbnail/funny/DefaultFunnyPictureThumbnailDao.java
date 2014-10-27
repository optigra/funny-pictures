package com.optigra.funnypictures.dao.thumbnail.funny;

import org.springframework.stereotype.Repository;

import com.optigra.funnypictures.dao.AbstractDao;
import com.optigra.funnypictures.model.thumbnail.FunnyPictureThumbnail;

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

}
