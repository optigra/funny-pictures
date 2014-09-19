package com.optigra.funnypictures.web.picture;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private Logger logger = LoggerFactory.getLogger(PictureController.class);
	
	@Resource(name = "pictureFacade")
	private PictureFacade pictureFacade;
	
	@RequestMapping
	public PagedResultResource<PictureResource> getPictures(@RequestParam(value = "offset", defaultValue = "0") Integer offset,
			@RequestParam(value = "limit", defaultValue = "20") Integer limit) {
		logger.info("Retrieving PagedResultResource for Picture Resources with offset: {}, limit: {}", offset, limit);
		PagedRequest pagedRequest = new PagedRequest(offset, limit);
		return pictureFacade.getPictures(pagedRequest);
	}
	
}
