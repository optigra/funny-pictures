package com.optigra.funnypictures.facade.facade.funny;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.optigra.funnypictures.content.model.Content;
import com.optigra.funnypictures.content.model.ThumbnailContent;
import com.optigra.funnypictures.content.service.ContentService;
import com.optigra.funnypictures.facade.converter.Converter;
import com.optigra.funnypictures.facade.resources.ApiResource;
import com.optigra.funnypictures.facade.resources.content.ContentResource;
import com.optigra.funnypictures.facade.resources.content.ContentResourceNamingStrategy;
import com.optigra.funnypictures.facade.resources.picture.FunnyPictureResource;
import com.optigra.funnypictures.facade.resources.picture.PictureResource;
import com.optigra.funnypictures.facade.resources.search.PagedRequest;
import com.optigra.funnypictures.facade.resources.search.PagedResultResource;
import com.optigra.funnypictures.generator.api.AdviceMemeContext;
import com.optigra.funnypictures.generator.api.AdviceMemeGenerator;
import com.optigra.funnypictures.generator.api.ImageHandle;
import com.optigra.funnypictures.generator.api.LabelledImageGenerator;
import com.optigra.funnypictures.model.FunnyPicture;
import com.optigra.funnypictures.model.Picture;
import com.optigra.funnypictures.model.thumbnail.FunnyPictureThumbnail;
import com.optigra.funnypictures.pagination.PagedResult;
import com.optigra.funnypictures.pagination.PagedSearch;
import com.optigra.funnypictures.service.funnypicture.FunnyPictureService;
import com.optigra.funnypictures.service.picture.PictureService;
import com.optigra.funnypictures.service.thumbnail.ThumbnailGeneratorService;
import com.optigra.funnypictures.service.thumbnail.funny.FunnyPictureThumbnailService;

/**
 * Default facade for funny pictures. Uses for creating funny picture.
 * 
 * @author rostyslav
 *
 */
@Transactional
@Component("funnyPictureFacade")
public class DefaultFunnyPictureFacade implements FunnyPictureFacade {

	private static final Logger LOG = LoggerFactory.getLogger(DefaultFunnyPictureFacade.class);

	@Resource(name = "funnyPictureService")
	private FunnyPictureService funnyPictureService;

	@Resource(name = "pictureService")
	private PictureService pictureService;

	@Resource(name = "contentService")
	private ContentService contentService;

	@Resource(name = "memeGenerator")
	private AdviceMemeGenerator memeGenerator;
	
	@Resource(name = "imageLabeller")
	private LabelledImageGenerator imageLabeller;
	
	@Resource(name = "funnyPictureThumbnailService")
	private FunnyPictureThumbnailService funnyPictureThumbnailService;
	
	@Resource(name = "pagedRequestConverter")
	private Converter<PagedRequest, PagedSearch<FunnyPicture>> pagedRequestConverter;

	@Resource(name = "pagedSearchConverter")
	private Converter<PagedResult<?>, PagedResultResource<? extends ApiResource>> pagedResultConverter;

	@Resource(name = "funnyPictureConverter")
	private Converter<FunnyPicture, FunnyPictureResource> funnyPictureConverter;

	@Resource(name = "namingStrategy")
	private ContentResourceNamingStrategy namingStrategy;

	@Resource(name = "thumbnailGeneratorService")
	private ThumbnailGeneratorService thumbnailGeneratorService;
	

	@Override
	public PagedResultResource<FunnyPictureResource> getFunnies(final PagedRequest pagedRequest) {

		LOG.info("Get funnies by paged request: {}", pagedRequest);

		PagedSearch<FunnyPicture> pagedSearch = pagedRequestConverter.convert(pagedRequest);

		PagedResult<FunnyPicture> pagedResult = funnyPictureService.getFunnyPictures(pagedSearch);

		List<FunnyPictureResource> resources = funnyPictureConverter.convertAll(pagedResult.getEntities());

		PagedResultResource<FunnyPictureResource> pagedResultResource = new PagedResultResource<>("/funnies");
		pagedResultResource.setEntities(resources);
		pagedResultConverter.convert(pagedResult, pagedResultResource);

		return pagedResultResource;
	}

	@Override
	public FunnyPictureResource createFunnyPicture(final FunnyPictureResource funny) {
		LOG.info("Generate funny image for : {}", funny);

		PictureResource picture = funny.getTemplate();
		Assert.notNull(picture, "Template should be provided");
		// Get template
		Picture template = pictureService.getPicture(picture.getId());
		// Load template content
		Content templateConntent = contentService.getContentByPath(template.getUrl());
		// Generate meme
		Content content = generateMeme(funny, templateConntent);
		// Save generated meme's content
		contentService.saveContent(content);
		
		// Save funny picture
		FunnyPicture funnyPicture = saveFunnyPicture(funny, template, content);

		List<ThumbnailContent> thumbnails = thumbnailGeneratorService.generateThumbnails(content);
		for (ThumbnailContent thumbnailContent : thumbnails) {
			ContentResource thumbnailResource = new ContentResource();
			thumbnailResource.setMimeType(thumbnailContent.getMimeType());
			String thumbnailUrl = namingStrategy.createIdentifier(thumbnailResource);
			thumbnailContent.setPath(thumbnailUrl);
			contentService.saveContent(thumbnailContent);
		}
		List<FunnyPictureThumbnail> funnyPictureThumbnails = createFunnyPictureThumbnails(thumbnails, funnyPicture);
		funnyPicture.setThumbnails(funnyPictureThumbnails);
		
		
		return funnyPictureConverter.convert(funnyPicture);
	}
	
	/**
	 * Method for converting and creating a list of thumbnails.
	 * @param thumbnails list of thumbnails.
	 * @param funnyPicture funny picture.
	 * @return list of funny picture thumbnails.
	 */
	private List<FunnyPictureThumbnail> createFunnyPictureThumbnails(final List<ThumbnailContent> thumbnails, final FunnyPicture funnyPicture) {
		List<FunnyPictureThumbnail> funnyPictureThumbnails = new ArrayList<FunnyPictureThumbnail>();
		
		for (ThumbnailContent content : thumbnails) {
			FunnyPictureThumbnail funnyPictureThumbnail = new FunnyPictureThumbnail();
			funnyPictureThumbnail.setCreateDate(new Date());
			funnyPictureThumbnail.setFunnyPicture(funnyPicture);
			funnyPictureThumbnail.setThumbnailType(content.getThumbnailType());
			funnyPictureThumbnail.setUpdateDate(new Date());
			funnyPictureThumbnail.setUrl(content.getPath()); ///!!!
			
			funnyPictureThumbnailService.createFunnyPictureThumbnail(funnyPictureThumbnail);
			funnyPictureThumbnails.add(funnyPictureThumbnail);
		}
		
		return funnyPictureThumbnails;
	}

	/**
	 * Method save funny picture into database.
	 * 
	 * @param funny
	 *            resource with name,header and footer text.
	 * @param template
	 *            base for creating funny picture
	 * @param content
	 *            with url to content storage
	 * @return created funny picture
	 */
	private FunnyPicture saveFunnyPicture(final FunnyPictureResource funny, final Picture template, final Content content) {
		// Save generated meme
		FunnyPicture funnyPicture = new FunnyPicture();
		funnyPicture.setPicture(template);
		funnyPicture.setName(funny.getName());
		funnyPicture.setHeader(funny.getHeader());
		funnyPicture.setFooter(funny.getFooter());
		funnyPicture.setUrl(content.getPath());

		return funnyPictureService.createFunnyPicture(funnyPicture);
	}

	/**
	 * Method generates advice.
	 * 
	 * @param funny
	 *            FunnyPictureResource for generating advice.
	 * @param templateConntent
	 *            content with stream and mimeType.
	 * @return Generated content.
	 */
	private Content generateMeme(final FunnyPictureResource funny, final Content templateConntent) {
		AdviceMemeContext context = new AdviceMemeContext(templateConntent.getContentStream(), templateConntent.getMimeType(), funny.getHeader(),
				funny.getFooter());
		ImageHandle generatedMeme = memeGenerator.generate(context);

		ContentResource memeResource = new ContentResource();
		memeResource.setMimeType(generatedMeme.getImageFormat());
		String memePath = namingStrategy.createIdentifier(memeResource);

		Content content = new Content();
		content.setPath(memePath);
		content.setMimeType(generatedMeme.getImageFormat());
		content.setContentStream(generatedMeme.getImageInputStream());

		return content;
	}

	@Override
	public PagedResultResource<FunnyPictureResource> getFunniesForPicture(final Long id, final PagedRequest pagedRequest) {
		
		PagedSearch<FunnyPicture> pagedSearch = pagedRequestConverter.convert(pagedRequest);
		
		PagedResult<FunnyPicture> pagedResult = funnyPictureService.getFunnyPicturesByPicture(pagedSearch, id);
		
		List<FunnyPictureResource> resources = funnyPictureConverter.convertAll(pagedResult.getEntities());
		
		PagedResultResource<FunnyPictureResource> pagedResultResource = new PagedResultResource<>("/funnies");
		pagedResultResource.setEntities(resources);
		pagedResultConverter.convert(pagedResult, pagedResultResource);
		
		return pagedResultResource;
	}
}
