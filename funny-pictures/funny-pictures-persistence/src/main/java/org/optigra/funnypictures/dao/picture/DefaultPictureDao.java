package org.optigra.funnypictures.dao.picture;

import org.optigra.funnypictures.dao.AbstractDao;
import org.springframework.stereotype.Repository;

import com.optigra.funnypictures.model.Picture;

@Repository("pictureDao")
public class DefaultPictureDao extends AbstractDao<Picture, Long> implements PictureDao {

	@Override
	public Class<Picture> getEntityClass() {
		return Picture.class;
	}

}
