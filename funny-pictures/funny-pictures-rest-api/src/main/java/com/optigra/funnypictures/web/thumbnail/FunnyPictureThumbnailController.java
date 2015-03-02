package com.optigra.funnypictures.web.thumbnail;

import com.optigra.funnypictures.facade.facade.thumbnail.funny.FunnyPictureThumbnailFacade;
import com.optigra.funnypictures.facade.resources.search.PagedRequest;
import com.optigra.funnypictures.facade.resources.search.PagedResultResource;
import com.optigra.funnypictures.facade.resources.thumbnail.funny.FunnyPictureThumbnailResource;
import com.optigra.funnypictures.model.thumbnail.ThumbnailType;
import com.optigra.funnypictures.web.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Controller for funny picture thumbnails.
 * 
 * @author oleh.zasadnyy
 *
 */
@RestController
@RequestMapping("/funniesthumb")
public class FunnyPictureThumbnailController extends BaseController {
private static final Logger LOG = LoggerFactory.getLogger(FunnyPictureThumbnailController.class);
	
	@Resource(name = "funnyPictureThumbnailFacade")
	private FunnyPictureThumbnailFacade funnyPictureThumbnailFacade;
	
	/**
	 * Controller method for getting paged result of funny pictures thumbnails. Requested
	 * method GET.
	 * 
	 * @param offset
	 *            starting position of paged result
	 * @param limit
	 *            count of entities in result
	 * @param thumbnailType
	 * 			  type of thumbnail.
	 * @return PagedResultResource with limit count of entities.
	 */
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method = RequestMethod.GET)
	public PagedResultResource<FunnyPictureThumbnailResource> getFunnyPicturesThumbnails(
			@RequestParam(value = "offset", defaultValue = "0") final Integer offset,
			@RequestParam(value = "limit", defaultValue = "20") final Integer limit,
			@RequestParam(value = "thumbnailType", defaultValue = "MEDIUM") final String thumbnailType) {
		LOG.info("Get pictures thumbnails: offset [{}] limit [{}] ", offset, limit);
		FunnyPictureThumbnailResource resource = new FunnyPictureThumbnailResource();
		resource.setThumbnailType(ThumbnailType.valueOf(thumbnailType));
		PagedRequest pagedRequest = new PagedRequest(resource, offset, limit);
		return funnyPictureThumbnailFacade.getFunnyPicturesThumbnails(pagedRequest);
	}
	
	/**
	 * API for getting information about FunnyPictureThumbnail by it's identifier.
	 * 
	 * @param id FunnyPictureThumbnail Identifier.
	 * @return Serialized Funny Picture Resource with all required fields.
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public FunnyPictureThumbnailResource getThumbnail(@PathVariable("id") final Long id) {
		LOG.info("Getting Funny Picture Thumbnail resource with id: {}", id);
		return funnyPictureThumbnailFacade.getFunnyPictureThumbnail(id);
	}
}
