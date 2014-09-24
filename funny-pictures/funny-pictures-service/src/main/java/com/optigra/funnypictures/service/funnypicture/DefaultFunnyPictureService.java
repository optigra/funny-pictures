package com.optigra.funnypictures.service.funnypicture;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.optigra.funnypictures.dao.funntypicture.FunnyPictureDao;
import com.optigra.funnypictures.model.FunnyPicture;
import com.optigra.funnypictures.pagination.PagedResult;
import com.optigra.funnypictures.pagination.PagedSearch;

@Service("funnyPictureService")
public class DefaultFunnyPictureService implements FunnyPictureService {

	@Resource(name = "funnyPictureDao")
	private FunnyPictureDao funnyPictureDao;

	@Override
	public PagedResult<FunnyPicture> getFunnyPictures(PagedSearch<FunnyPicture> pagedSearch) {
		return funnyPictureDao.getFunnyPictures(pagedSearch);
	}

	@Override
	public FunnyPicture createFunnyPicture(FunnyPicture funnyPicture) {
		funnyPictureDao.save(funnyPicture);
		return funnyPicture;
	}

	@Override
	public FunnyPicture getFunnyPicture(Long id) {
		return funnyPictureDao.findById(id);
	}

}
