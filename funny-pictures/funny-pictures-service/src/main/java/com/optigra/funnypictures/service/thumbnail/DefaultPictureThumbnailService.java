package com.optigra.funnypictures.service.thumbnail;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.optigra.funnypictures.dao.thumbnail.PictureThumbnailDao;
import com.optigra.funnypictures.model.thumbnail.PictureThumbnail;

/**
 * Picture Thumbnail Service.
 * @author ivanursul
 *
 */
@Service("pictureThumbnailService")
public class DefaultPictureThumbnailService implements PictureThumbnailService {

	@Resource(name = "pictureThumbnailDao")
	private PictureThumbnailDao pictureThumbnailDao;
	
	@Override
	public void createPictureThumbnail(final PictureThumbnail pictureThumbnail) {
		pictureThumbnailDao.save(pictureThumbnail);
	}

}
