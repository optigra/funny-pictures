package com.optigra.funnypictures.web.picture;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.optigra.funnypictures.facade.facade.funny.FunnyPictureFacade;
import com.optigra.funnypictures.facade.resources.picture.FunnyPictureResource;
import com.optigra.funnypictures.facade.resources.search.PagedRequest;
import com.optigra.funnypictures.facade.resources.search.PagedResultResource;
import com.optigra.funnypictures.web.BaseController;

@RestController
@RequestMapping("/funnies")
public class FunnyPictureController extends BaseController {

	private static final Logger LOG = LoggerFactory.getLogger(FunnyPictureController.class);

	@Resource(name = "funnyPictureFacade")
	private FunnyPictureFacade funnyPictureFacade;

	@RequestMapping(method = RequestMethod.GET)
	public PagedResultResource<FunnyPictureResource> getFunnies(@RequestParam(value = "offset", defaultValue = "0") Integer offset,
			@RequestParam(value = "limit", defaultValue = "20") Integer limit) {

		LOG.info("Get funnies: offset [{}] limit [{}] ", offset, limit);

		PagedRequest pagedRequest = new PagedRequest(offset, limit);

		return funnyPictureFacade.getFunnies(pagedRequest);
	}
}
