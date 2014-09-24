package com.optigra.funnypictures.service.funnypicture;

import com.optigra.funnypictures.model.FunnyPicture;
import com.optigra.funnypictures.pagination.PagedResult;
import com.optigra.funnypictures.pagination.PagedSearch;

public interface FunnyPictureService {

	PagedResult<FunnyPicture> getFunnyPictures(PagedSearch<FunnyPicture> pagedSearch);

	FunnyPicture createFunnyPicture(FunnyPicture funnyPicture);

	FunnyPicture getFunnyPicture(Long id);
	
}
