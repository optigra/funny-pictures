package com.optigra.funnypictures.service.funnypicture;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.optigra.funnypictures.dao.funntypicture.FunnyPictureDao;
import com.optigra.funnypictures.dao.picture.PictureDao;
import com.optigra.funnypictures.model.FunnyPicture;
import com.optigra.funnypictures.model.Picture;
import com.optigra.funnypictures.pagination.PagedResult;
import com.optigra.funnypictures.pagination.PagedSearch;

/**
 * Implementation of @see com.optigra.funnypictures.service.FunnyPicture, that
 * works with @see com.optigra.funnypictures.dao.funnypicture.FunnyPictureDao.
 * 
 * @author ivanursul
 *
 */
@Service("funnyPictureService")
public class DefaultFunnyPictureService implements FunnyPictureService {

	@Resource(name = "pictureDao")
	private PictureDao pictureDao;

	@Resource(name = "funnyPictureDao")
	private FunnyPictureDao funnyPictureDao;

	@Override
	public PagedResult<FunnyPicture> getFunnyPictures(final PagedSearch<FunnyPicture> pagedSearch) {
		return funnyPictureDao.getFunnyPictures(pagedSearch);
	}

	@Override
	public FunnyPicture createFunnyPicture(final FunnyPicture funnyPicture) {
		funnyPictureDao.save(funnyPicture);
		return funnyPicture;
	}

	@Override
	public FunnyPicture getFunnyPicture(final Long id) {
		return funnyPictureDao.findById(id);
	}

	@Override
	public PagedResult<FunnyPicture> getFunnyPicturesByPicture(final PagedSearch<FunnyPicture> pagedSearch, final Long id) {
		
		PagedSearch<FunnyPicture> pagedSearchWithParameter = pagedSearch;
		Picture picture = pictureDao.findById(id);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("picture", picture);
		pagedSearchWithParameter.setParameters(parameters);
		
		return funnyPictureDao.getFunnyPicturesByPicture(pagedSearchWithParameter);
	}

}
