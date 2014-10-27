package com.optigra.funnypictures.service.thumbnail;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.optigra.funnypictures.dao.thumbnail.ThumbnailDao;
import com.optigra.funnypictures.model.thumbnail.Thumbnail;

/**
 * Default implementation of Thumbnail Service.
 * @author ivanursul
 *
 */
@Service("thumbnailService")
public class DefaultThumbnailService implements ThumbnailService {

	@Resource(name = "thumbnailDao")
	private ThumbnailDao thumbnailDao;

	@Override
	public void createThumbnail(final Thumbnail thumbnail) {
		thumbnailDao.save(thumbnail);
	}
	
}
