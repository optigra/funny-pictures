package com.optigra.funnypictures.service.thumbnail.funny;

import com.optigra.funnypictures.dao.picture.PictureDao;
import com.optigra.funnypictures.dao.thumbnail.funny.FunnyPictureThumbnailDao;
import com.optigra.funnypictures.model.Picture;
import com.optigra.funnypictures.model.thumbnail.FunnyPictureThumbnail;
import com.optigra.funnypictures.model.thumbnail.ThumbnailType;
import com.optigra.funnypictures.pagination.PagedResult;
import com.optigra.funnypictures.pagination.PagedSearch;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Default implementation of funny picture thumbnail service.
 * @author ivanursul
 * 
 */
@Service("funnyPictureThumbnailService")
public class DefaultFunnyPictureThumbnailService implements FunnyPictureThumbnailService {

	@Resource(name = "pictureDao")
	private PictureDao pictureDao;
	
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

	@Override
	public PagedResult<FunnyPictureThumbnail> getFunnyPictureThumbnailsByPicture(
			final PagedSearch<FunnyPictureThumbnail> pagedSearch, final  Long id) {
		PagedSearch<FunnyPictureThumbnail> pagedSearchWithParameter = pagedSearch;
		Picture picture = pictureDao.findById(id);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("picture", picture);
		parameters.put("type", pagedSearch.getEntity().getThumbnailType());
		pagedSearchWithParameter.setParameters(parameters);
		
		return funnyPictureThumbnailDao.getThumbnailsByPicture(pagedSearchWithParameter);
	}

	@Override
	public FunnyPictureThumbnail getFunnyThumbnailForFunnyPicture(final Long id, final ThumbnailType thumbnailType) {
		return funnyPictureThumbnailDao.getThumbnailForFunnyPicture(id, thumbnailType);
	}

}
