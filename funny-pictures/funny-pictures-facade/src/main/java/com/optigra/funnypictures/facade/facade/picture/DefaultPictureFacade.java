package com.optigra.funnypictures.facade.facade.picture;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.optigra.funnypictures.content.model.Content;
import com.optigra.funnypictures.content.model.ThumbnailContent;
import com.optigra.funnypictures.content.service.ContentService;
import com.optigra.funnypictures.facade.converter.Converter;
import com.optigra.funnypictures.facade.resources.ApiResource;
import com.optigra.funnypictures.facade.resources.content.ContentResource;
import com.optigra.funnypictures.facade.resources.content.ContentResourceNamingStrategy;
import com.optigra.funnypictures.facade.resources.picture.PictureResource;
import com.optigra.funnypictures.facade.resources.search.PagedRequest;
import com.optigra.funnypictures.facade.resources.search.PagedResultResource;
import com.optigra.funnypictures.model.Picture;
import com.optigra.funnypictures.model.thumbnail.PictureThumbnail;
import com.optigra.funnypictures.pagination.PagedResult;
import com.optigra.funnypictures.pagination.PagedSearch;
import com.optigra.funnypictures.service.picture.PictureService;
import com.optigra.funnypictures.service.thumbnail.PictureThumbnailService;
import com.optigra.funnypictures.service.thumbnail.ThumbnailGeneratorService;

/**
 * Default implementation of PictureFacade.
 * 
 * @author rostyslav
 *
 */
@Component("pictureFacade")
@Transactional
public class DefaultPictureFacade implements PictureFacade {

	@Resource(name = "pagedRequestConverter")
	private Converter<PagedRequest<PictureResource>, PagedSearch<Picture>> pagedRequestConverter;

	@Resource(name = "pagedSearchConverter")
	private Converter<PagedResult<?>, PagedResultResource<? extends ApiResource>> pagedResultConverter;

	@Resource(name = "pictureConverter")
	private Converter<Picture, PictureResource> pictureConverter;

	@Resource(name = "pictureResourceConverter")
	private Converter<PictureResource, Picture> pictureResourceConverter;

	@Resource(name = "pictureService")
	private PictureService pictureService;

	@Resource(name = "pictureThumbnailService")
	private PictureThumbnailService pictureThumbnailService;
	
	@Resource(name = "namingStrategy")
	private ContentResourceNamingStrategy namingStrategy;
	
	@Resource(name = "thumbnailGeneratorService")
	private ThumbnailGeneratorService thumbnailGeneratorService;
	
	@Resource(name = "contentService")
	private ContentService contentService;

	@Value("${funnies.missing.generate.thumbnails}")
	private Boolean generateMissingFunnyThumbnails;
	
	@Override
	public PagedResultResource<PictureResource> getPictures(final PagedRequest<PictureResource> pagedRequest) {
		// Convert PagedRequest to PagedSearch
		PagedSearch<Picture> pagedSearch = pagedRequestConverter.convert(pagedRequest);

		// Retrieve result pictureService.getPictures(pagedSearch)
		PagedResult<Picture> pagedResult = pictureService.getPictures(pagedSearch);
		generateMissingFunnies(pagedResult.getEntities());
		
		// Convert List<Picture> to List<PictureResource>
		List<PictureResource> resources = pictureConverter.convertAll(pagedResult.getEntities());

		// Create pagedResultResource
		PagedResultResource<PictureResource> pagedResultResource = new PagedResultResource<>("/pictures");
		pagedResultResource.setEntities(resources);

		// Convert PagedResult to PagedResultResource
		pagedResultConverter.convert(pagedResult, pagedResultResource);

		return pagedResultResource;
	}

	/**
	 * Method for generating Missing Funnies.
	 * @param funnies
	 */
	private void generateMissingFunnies(final List<Picture> pictures) {
	
		if (generateMissingFunnyThumbnails) {
			
			for (Picture picture : pictures) {
				
				if (picture.getThumbnails().isEmpty()) {
					generateThumbnails(picture);
				}
			}
			
		}
		
	}
	
	@Override
	public PictureResource createPicture(final PictureResource pictureResource) {

		Picture picture = pictureResourceConverter.convert(pictureResource);
		pictureService.createPicture(picture);
		
		List<PictureThumbnail> pictureThumbnails = generateThumbnails(picture);
		
		picture.setThumbnails(pictureThumbnails);
		
		return pictureConverter.convert(picture);
	}

	private List<PictureThumbnail> generateThumbnails(final Picture picture) {
		
		Content content = contentService.getContentByPath(picture.getUrl());
		List<ThumbnailContent> thumbnails = thumbnailGeneratorService.generateThumbnails(content);
		
		List<PictureThumbnail> pictureThumbnails = new ArrayList<PictureThumbnail>();
		for (ThumbnailContent thumbnailContent : thumbnails) {
			String thumbnailUrl = generateThumbnail(thumbnailContent);
			
			PictureThumbnail pictureThumbnail = createPictureThumbnail(picture, thumbnailContent, thumbnailUrl);
			
			pictureThumbnailService.createPictureThumbnail(pictureThumbnail);
			pictureThumbnails.add(pictureThumbnail);
		}
		return pictureThumbnails;
	}

	/**
	 * Method for creating Picture Thumbnail.
	 * @param picture
	 * @param thumbnailContent
	 * @param thumbnailUrl
	 * @return Picture Thumbnail Entity.
	 */
	private PictureThumbnail createPictureThumbnail(final Picture picture, final ThumbnailContent thumbnailContent, final String thumbnailUrl) {
		
		PictureThumbnail pictureThumbnail = new PictureThumbnail();
		pictureThumbnail.setCreateDate(new Date());
		pictureThumbnail.setPicture(picture);
		pictureThumbnail.setThumbnailType(thumbnailContent.getThumbnailType());
		pictureThumbnail.setUpdateDate(new Date());
		pictureThumbnail.setUrl(thumbnailUrl);
		
		return pictureThumbnail;
	}

	/**
	 * Method for generating thumbnail.
	 * @param thumbnailContent
	 * @return Thumbnail url.
	 */
	private String generateThumbnail(final ThumbnailContent thumbnailContent) {
		ContentResource thumbnailResource = new ContentResource();
		thumbnailResource.setMimeType(thumbnailContent.getMimeType());
		String thumbnailUrl = namingStrategy.createIdentifier(thumbnailResource);
		thumbnailContent.setPath(thumbnailUrl);
		contentService.saveContent(thumbnailContent);
		return thumbnailUrl;
	}

	@Override
	public void updatePicture(final Long id, final PictureResource pictureResource) {
		Picture picture = pictureService.getPicture(id);
		pictureResourceConverter.convert(pictureResource, picture);
		pictureService.updatePicture(picture);
	}

	@Override
	public PictureResource getPicture(final Long id) {
		Picture picture = pictureService.getPicture(id);
		return pictureConverter.convert(picture);
	}

	@Override
	public void deletePicture(final Long id) {
		Picture picture = pictureService.getPicture(id);
		pictureService.deletePicture(picture);
	}

	public void setGenerateMissingFunnyThumbnails(final Boolean generateMissingFunnyThumbnails) {
		this.generateMissingFunnyThumbnails = generateMissingFunnyThumbnails;
	}

}
