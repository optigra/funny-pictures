package com.optigra.funnypictures.web.picture;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.optigra.funnypictures.facade.facade.funny.FunnyPictureFacade;
import com.optigra.funnypictures.facade.resources.picture.FunnyPictureResource;
import com.optigra.funnypictures.facade.resources.search.PagedRequest;
import com.optigra.funnypictures.facade.resources.search.PagedResultResource;
import com.optigra.funnypictures.web.BaseController;

/**
 * REST Controller with CRUD for funny pictures.
 * 
 * @author rostyslav
 *
 */
@RestController
@RequestMapping("/funnies")
public class FunnyPictureController extends BaseController {
	private static final Logger LOG = LoggerFactory.getLogger(FunnyPictureController.class);
	@Resource(name = "funnyPictureFacade")
	private FunnyPictureFacade funnyPictureFacade;

	/**
	 * Controller method for getting paged result of funny pictures. Requested
	 * method GET.
	 * 
	 * @param offset
	 *            starting position of paged result
	 * @param limit
	 *            count of entities in result.
	 * @return PagedResultResource with limit count of entities.
	 */
	@RequestMapping(method = RequestMethod.GET)
	public PagedResultResource<FunnyPictureResource> getFunnies(@RequestParam(value = "offset", defaultValue = "0") final Integer offset,
			@RequestParam(value = "limit", defaultValue = "20") final Integer limit) {

		LOG.info("Get funnies: offset [{}] limit [{}] ", offset, limit);

		PagedRequest pagedRequest = new PagedRequest(offset, limit);

		return funnyPictureFacade.getFunnies(pagedRequest);
	}

	/**
	 * Method for creating funny picture. Requested method POST.
	 * 
	 * @param funnyPictureResource
	 *            Request JSON parameters with funny picture object.
	 * @return created FunnyPicture.
	 */
	@RequestMapping(method = RequestMethod.POST)
	public FunnyPictureResource createFunny(@RequestBody final FunnyPictureResource funnyPictureResource) {
		LOG.info("Create funny picture for {}", funnyPictureResource);

		return funnyPictureFacade.createFunnyPicture(funnyPictureResource);
	}
}
