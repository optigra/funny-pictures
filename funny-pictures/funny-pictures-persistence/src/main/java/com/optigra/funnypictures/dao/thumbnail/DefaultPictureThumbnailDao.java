package com.optigra.funnypictures.dao.thumbnail;

import org.springframework.stereotype.Repository;

import com.optigra.funnypictures.dao.AbstractDao;
import com.optigra.funnypictures.model.thumbnail.PictureThumbnail;

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

}
