package com.optigra.funnypictures.service.picture;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.optigra.funnypictures.dao.picture.PictureDao;
import com.optigra.funnypictures.model.Picture;
import com.optigra.funnypictures.pagination.PagedResult;
import com.optigra.funnypictures.pagination.PagedSearch;

/**
 * Service, that works with Picture Entity.
 * @author ivanursul
 *
 */
@Service("pictureService")
public class DefaultPictureService implements PictureService {

	@Resource(name = "pictureDao")
	private PictureDao pictureDao;
	
	@Override
	public PagedResult<Picture> getPictures(final PagedSearch<Picture> pagedSearch) {
		return pictureDao.getPictures(pagedSearch);
	}

	@Override
	public Picture createPicture(final Picture picture) {
		pictureDao.save(picture);
		return picture;
	}

	@Override
	public Picture getPicture(final Long id) {
		return pictureDao.findById(id);
	}

	@Override
	public void updatePicture(final Picture picture) {
		pictureDao.update(picture);
	}

	@Override
	public void deletePicture(final Picture picture) {
		pictureDao.delete(picture);		
	}

}
