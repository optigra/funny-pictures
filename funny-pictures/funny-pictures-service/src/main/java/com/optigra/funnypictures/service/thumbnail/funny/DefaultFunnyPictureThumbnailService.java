package com.optigra.funnypictures.service.thumbnail.funny;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.optigra.funnypictures.dao.thumbnail.funny.FunnyPictureThumbnailDao;
import com.optigra.funnypictures.model.thumbnail.FunnyPictureThumbnail;
import com.optigra.funnypictures.pagination.PagedResult;
import com.optigra.funnypictures.pagination.PagedSearch;

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

	@Override
	public FunnyPictureThumbnail getFunnyPictureThumbnail(final Long id) {
		return funnyPictureThumbnailDao.findById(id);
	}

	@Override
	public PagedResult<FunnyPictureThumbnail> getFunnyPictureThumbnails(
			final PagedSearch<FunnyPictureThumbnail> pagedSearch) {
		Map<String, Object> params = new HashMap<>();
		params.put("type", pagedSearch.getEntity().getThumbnailType());
		
		pagedSearch.setParameters(params);
		return funnyPictureThumbnailDao.getThumbnails(pagedSearch);
	}

}
