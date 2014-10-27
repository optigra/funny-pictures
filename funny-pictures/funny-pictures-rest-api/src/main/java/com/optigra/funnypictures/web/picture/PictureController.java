package com.optigra.funnypictures.web.picture;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.optigra.funnypictures.facade.facade.funny.FunnyPictureFacade;
import com.optigra.funnypictures.facade.facade.picture.PictureFacade;
import com.optigra.funnypictures.facade.resources.message.MessageResource;
import com.optigra.funnypictures.facade.resources.message.MessageType;
import com.optigra.funnypictures.facade.resources.picture.FunnyPictureResource;
import com.optigra.funnypictures.facade.resources.picture.PictureResource;
import com.optigra.funnypictures.facade.resources.search.PagedRequest;
import com.optigra.funnypictures.facade.resources.search.PagedResultResource;
import com.optigra.funnypictures.web.BaseController;

/**
 * Controller for all pictures API's.
 * 
 * @author ivanursul
 *
 */
@RestController
@RequestMapping(value = "/pictures")
public class PictureController extends BaseController {
	private static final Logger LOG = LoggerFactory.getLogger(PictureController.class);
	
	@Resource(name = "pictureFacade")
	private PictureFacade pictureFacade;
	
	@Resource(name = "funnyPictureFacade")
	private FunnyPictureFacade funnyPictureFacade;

	/** 
	 * API for Retrieving paged result for all pictures @see com.optigra.funnypictures.facade.resource.PictureResource.
	 * 
	 * @param offset Start position of PictureResource. There is some default value for
	 * this param, but you are free to set this param in your url to desired one.
	 * For example, you can set wwww.HOST:port/contextPath/api/pictures/offset = 40.
	 * That's mean, that you will retrieve paged result, that starts from 40 position.
	 * 
	 * @param limit Maximum limit of results.There is some default value for
	 * this param, but you are free to set this param in your url to desired one.
	 * For example, you can set wwww.HOST:port/contextPath/api/pictures/limit = 100.
	 * That's mean, that you will retrieve maximum 100 elements by one paged result.
	 * 
	 * @return PagedResultResource, that will contain a list of entities
	 */
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping
	public PagedResultResource<PictureResource> getPictures(@RequestParam(value = "offset", defaultValue = "0") final Integer offset,
			@RequestParam(value = "limit", defaultValue = "20") final Integer limit) {
		LOG.info("Retrieving PagedResultResource for Picture Resources with offset: {}, limit: {}", offset, limit);
		PagedRequest pagedRequest = new PagedRequest(offset, limit);
		return pictureFacade.getPictures(pagedRequest);
	}

	/**
	 * API for storing PictureResource in our service.
	 * 
	 * @param picture Serialized picture {@link PictureResource}
	 * 
	 * @return Serialized PictureResource with generated Identifier from our storage.
	 */
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(method = RequestMethod.POST)
	public PictureResource createPicture(@RequestBody final PictureResource picture) {
		LOG.info("Creating Picture resource with: {}", picture);
		return pictureFacade.createPicture(picture);
	}
	
	/**
	 * API for getting information about Picture by it's identifier.
	 * 
	 * @param id Picture Identifier.
	 * @return Serialized Picture Resource with all required fields.
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public PictureResource getPicture(@PathVariable("id") final Long id) {
		LOG.info("Getting Picture resource with id: {}", id);
		return pictureFacade.getPicture(id);
	}
	
	/**
	 * API for getting funnies for picture.
	 * 
	 * @param id Picture identifier.
	 * @return PagedResultResource with funnies for picture.
	 */
	@RequestMapping(value = "/{id}/funnies", method = RequestMethod.GET)
	public PagedResultResource<FunnyPictureResource> getFunniesForPicture(@PathVariable("id") final Long id, 
			@RequestParam(value = "offset", defaultValue = "0") final Integer offset,
			@RequestParam(value = "limit", defaultValue = "20") final Integer limit) {
		LOG.info("Getting Funnies for Picture resource with id: {}", id);
		
		PagedRequest pagedRequest = new PagedRequest(offset, limit);
		
		return funnyPictureFacade.getFunniesForPicture(id , pagedRequest);
		
	}
	
	/**
	 * API for deleting PictureResource by it's identifier.
	 * 
	 * @param id Picture Identifier.
	 * 
	 * @return Message with information about the result of operation.
	 */
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public MessageResource deletePicture(@PathVariable("id") final Long id) {
		LOG.info("Getting Picture resource with id: {}", id);
		pictureFacade.deletePicture(id);
		return new MessageResource(MessageType.INFO, "Picture Resource was deleted");
	}
	
	/**
	 * API for updating Picture Resource.
	 * 
	 * @param id Picture Identifier.
	 * 
	 * @param pictureResource Picture Resource with all fields, that need to be updated.
	 * Note, that if you will pass null value for some field, that currently has some non-null value,
	 * then you will have null value after this operation.
	 * 
	 * @return Message about the result of operation.
	 */
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public MessageResource updatePicture(@PathVariable("id") final Long id, @RequestBody final PictureResource pictureResource) {
		LOG.info("Updating Picture resource with id: {}, resource: {}", id, pictureResource);
		pictureFacade.updatePicture(id, pictureResource);
		return new MessageResource(MessageType.INFO, "Picture Resource updated");
	}
}
