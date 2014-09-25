package com.optigra.funnypictures.facade.facade.funny;

import java.util.List;

import javax.annotation.Resource;

import org.funny.pictures.generator.api.AdviceMemeContext;
import org.funny.pictures.generator.api.AdviceMemeGenerator;
import org.funny.pictures.generator.api.ImageHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.optigra.funnypictures.content.model.Content;
import com.optigra.funnypictures.content.service.ContentService;
import com.optigra.funnypictures.facade.converter.Converter;
import com.optigra.funnypictures.facade.resources.ApiResource;
import com.optigra.funnypictures.facade.resources.content.ContentResource;
import com.optigra.funnypictures.facade.resources.content.ContentResourceNamingStrategy;
import com.optigra.funnypictures.facade.resources.picture.FunnyPictureResource;
import com.optigra.funnypictures.facade.resources.picture.PictureResource;
import com.optigra.funnypictures.facade.resources.search.PagedRequest;
import com.optigra.funnypictures.facade.resources.search.PagedResultResource;
import com.optigra.funnypictures.model.FunnyPicture;
import com.optigra.funnypictures.model.Picture;
import com.optigra.funnypictures.pagination.PagedResult;
import com.optigra.funnypictures.pagination.PagedSearch;
import com.optigra.funnypictures.service.funnypicture.FunnyPictureService;
import com.optigra.funnypictures.service.picture.PictureService;

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

	@Resource(name = "pagedRequestConverter")
	private Converter<PagedRequest, PagedSearch<FunnyPicture>> pagedRequestConverter;

	@Resource(name = "pagedSearchConverter")
	private Converter<PagedResult<?>, PagedResultResource<? extends ApiResource>> pagedResultConverter;

	@Resource(name = "funnyPictureConverter")
	private Converter<FunnyPicture, FunnyPictureResource> funnyPictureConverter;

	@Resource(name = "namingStrategy")
	private ContentResourceNamingStrategy namingStrategy;

	@Override
	public PagedResultResource<FunnyPictureResource> getFunnies(PagedRequest pagedRequest) {

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
	public FunnyPictureResource createFunnyPicture(FunnyPictureResource funny) {
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
		
		return funnyPictureConverter.convert(funnyPicture);
		
	}

	private FunnyPicture saveFunnyPicture(FunnyPictureResource funny, Picture template, Content content) {
		// Save generated meme
		FunnyPicture funnyPicture = new FunnyPicture();
		funnyPicture.setPicture(template);
		funnyPicture.setName(funny.getName());
		funnyPicture.setHeader(funny.getHeader());
		funnyPicture.setFooter(funny.getFooter());
		funnyPicture.setUrl(content.getPath());

		return funnyPictureService.createFunnyPicture(funnyPicture);
	}

	private Content generateMeme(FunnyPictureResource funny, Content templateConntent) {
		AdviceMemeContext context = new AdviceMemeContext(templateConntent.getContentStream(), templateConntent.getMimeType(), funny.getHeader(),
				funny.getFooter());
		ImageHandle generatedMeme = memeGenerator.generate(context);

		ContentResource memeResource = new ContentResource();
		memeResource.setMimeType(generatedMeme.getImageFormat());
		String memePath = namingStrategy.createIdentifier(memeResource);

		Content content = new Content();
		content.setPath(memePath);
		content.setContentStream(generatedMeme.getImageInputStream());

		return content;
	}
}