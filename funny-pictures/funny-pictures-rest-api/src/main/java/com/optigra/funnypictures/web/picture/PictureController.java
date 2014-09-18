package com.optigra.funnypictures.web.picture;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.optigra.funnypictures.facade.facade.picture.PictureFacade;
import com.optigra.funnypictures.facade.resources.picture.PictureResource;
import com.optigra.funnypictures.facade.resources.search.PagedRequest;
import com.optigra.funnypictures.facade.resources.search.PagedResultResource;
import com.optigra.funnypictures.web.BaseController;

@RestController
@RequestMapping(value = "/pictures")
public class PictureController extends BaseController {
	
	@Resource(name = "pictureFacade")
	private PictureFacade pictureFacade;
	
	@RequestMapping
	public PagedResultResource<PictureResource> getPictures(@RequestParam(value = "offset", defaultValue = "0") Integer offset,
			@RequestParam(value = "limit", defaultValue = "20") Integer limit) {
		PagedRequest pagedRequest = new PagedRequest(offset, limit);
		return pictureFacade.getPictures(pagedRequest);
	}
	
}
