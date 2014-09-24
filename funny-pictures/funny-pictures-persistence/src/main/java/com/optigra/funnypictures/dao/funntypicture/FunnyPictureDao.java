package com.optigra.funnypictures.dao.funntypicture;

import com.optigra.funnypictures.dao.Dao;
import com.optigra.funnypictures.model.FunnyPicture;
import com.optigra.funnypictures.pagination.PagedResult;
import com.optigra.funnypictures.pagination.PagedSearch;

public interface FunnyPictureDao extends Dao<FunnyPicture, Long>  {
	
	PagedResult<FunnyPicture> getFunnyPictures(PagedSearch<FunnyPicture> pagedSearch);

}
