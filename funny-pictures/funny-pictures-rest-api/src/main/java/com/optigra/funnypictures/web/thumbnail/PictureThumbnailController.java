package com.optigra.funnypictures.web.thumbnail;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.optigra.funnypictures.facade.facade.thumbnail.PictureThumbnailFacade;
import com.optigra.funnypictures.facade.resources.search.PagedRequest;
import com.optigra.funnypictures.facade.resources.search.PagedResultResource;
import com.optigra.funnypictures.facade.resources.thumbnail.PictureThumbnailResource;
import com.optigra.funnypictures.model.thumbnail.ThumbnailType;
import com.optigra.funnypictures.web.BaseController;

/**
 * Controller for picture thumbnails.
 * 
 * @author oleh.zasadnyy
 *
 */
@RestController
@RequestMapping("/picturesthumb")
public class PictureThumbnailController extends BaseController {
	private static final Logger LOG = LoggerFactory.getLogger(PictureThumbnailController.class);
	
	@Resource(name = "pictureThumbnailFacade")
	private PictureThumbnailFacade pictureThumbnailFacade;
	
	/**
	 * Controller method for getting paged result of pictures thumbnails. Requested
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
	public PagedResultResource<PictureThumbnailResource> getPicturesThumbnails(@RequestParam(value = "offset", defaultValue = "0") final Integer offset,
			@RequestParam(value = "limit", defaultValue = "20") final Integer limit,
			@RequestParam(value = "thumbnailType", defaultValue = "RANDOM") final String thumbnailType) {
		if("RANDOM".equals(thumbnailType)) {
			return getRandomPicturesThumbnails(offset, limit);
		} else {
			LOG.info("Get pictures thumbnails: offset [{}] limit [{}] thumbnailType [{}]", offset, limit, thumbnailType);
			PictureThumbnailResource resource = new PictureThumbnailResource();
			resource.setThumbnailType(ThumbnailType.valueOf(thumbnailType));
			PagedRequest pagedRequest = new PagedRequest(resource, offset, limit);
			return pictureThumbnailFacade.getPicturesThumbnails(pagedRequest);
		}
	}
	
	/**
	 * Controller method for getting paged result of pictures thumbnails of random type. Requested
	 * method GET.
	 * 
	 * @param offset
	 *            starting position of paged result
	 * @param limit
	 *            count of entities in result
	 * @return PagedResultResource with limit count of entities.
	 */
	public PagedResultResource<PictureThumbnailResource> getRandomPicturesThumbnails(@RequestParam(value = "offset", defaultValue = "0") final Integer offset,
			@RequestParam(value = "limit", defaultValue = "20") final Integer limit) {
		LOG.info("Get pictures thumbnails: offset [{}] limit [{}] ", offset, limit);
		PagedRequest pagedRequest = new PagedRequest(offset, limit);
		return pictureThumbnailFacade.getRandomPicturesThumbnails(pagedRequest);
	}
	
	/**
	 * API for getting information about PictureThumbnail by it's identifier.
	 * 
	 * @param id PictureThumbnail Identifier.
	 * @return Serialized Picture Resource with all required fields.
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public PictureThumbnailResource getThumbnail(@PathVariable("id") final Long id) {
		LOG.info("Getting Picture Thumbnail resource with id: {}", id);
		return pictureThumbnailFacade.getPictureThumbnail(id);
	}
}
