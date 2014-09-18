package org.optigra.funnypictures.dao.picture;

import org.optigra.funnypictures.dao.Dao;

import com.optigra.funnypictures.model.Picture;
import com.optigra.funnypictures.pagination.PagedResult;
import com.optigra.funnypictures.pagination.PagedSearch;

public interface PictureDao extends Dao<Picture, Long> {

	PagedResult<Picture> getPictures(PagedSearch<Picture> pagedSearch);

}
