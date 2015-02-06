package com.optigra.funnypictures.service.thumbnail;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.optigra.funnypictures.dao.thumbnail.PictureThumbnailDao;
import com.optigra.funnypictures.dao.thumbnail.RandomPictureThumbnailDao;
import com.optigra.funnypictures.model.thumbnail.PictureThumbnail;
import com.optigra.funnypictures.pagination.PagedResult;
import com.optigra.funnypictures.pagination.PagedSearch;
import com.optigra.funnypictures.view.thumbnail.RandomPictureThumbnailView;

/**
 * Picture Thumbnail Service.
 * @author ivanursul
 *
 */
@Service("pictureThumbnailService")
public class DefaultPictureThumbnailService implements PictureThumbnailService {

	@Resource(name = "pictureThumbnailDao")
	private PictureThumbnailDao pictureThumbnailDao;
	
	@Resource(name = "randomPictureThumbnailDao")
	private RandomPictureThumbnailDao randomPictureThumbnailDao;
	
	@Override
	public void createPictureThumbnail(final PictureThumbnail pictureThumbnail) {
		pictureThumbnailDao.save(pictureThumbnail);
	}

	@Override
	public PagedResult<PictureThumbnail> getPictureThumbnails(
			final PagedSearch<PictureThumbnail> pagedSearch) {
		Map<String, Object> params = new HashMap<>();
		params.put("type", pagedSearch.getEntity().getThumbnailType());
		
		pagedSearch.setParameters(params);
		return pictureThumbnailDao.getThumbnails(pagedSearch);
	}

	@Override
	public PagedResult<RandomPictureThumbnailView> getRandomPictureThumbnails(
			PagedSearch<RandomPictureThumbnailView> pagedSearch) {
		Map<String, Object> params = new HashMap<>();
		
		pagedSearch.setParameters(params);
		return randomPictureThumbnailDao.getRandomThumbnails(pagedSearch);
	}

	@Override
	public PictureThumbnail getPictureThumbnail(final Long id) {
		return pictureThumbnailDao.findById(id);
	}
}
