package com.optigra.funnypictures.service.thumbnail.funny;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.optigra.funnypictures.dao.thumbnail.funny.FunnyPictureThumbnailDao;
import com.optigra.funnypictures.model.thumbnail.FunnyPictureThumbnail;

/**
 * Default implementation of funny picture thumbnail service.
 * @author ivanursul
 *
 */
@Service("funnyPictureThumbnailService")
public class DefaultFunnyPictureThumbnailService implements FunnyPictureThumbnailService {

	@Resource(name = "funnyPictureThumbnailDao")
	private FunnyPictureThumbnailDao funnyPictureThumbnailDao;
	
	@Override
	public void createFunnyPictureThumbnail(final FunnyPictureThumbnail funnyPictureThumbnail) {
		funnyPictureThumbnailDao.save(funnyPictureThumbnail);
	}

}
