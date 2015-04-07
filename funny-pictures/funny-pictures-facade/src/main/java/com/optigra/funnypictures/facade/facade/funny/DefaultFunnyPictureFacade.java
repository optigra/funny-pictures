package com.optigra.funnypictures.facade.facade.funny;

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
import com.optigra.funnypictures.generator.api.ImageLabellingContext;
import com.optigra.funnypictures.generator.api.LabelledImageGenerator;
import com.optigra.funnypictures.model.FunnyPicture;
import com.optigra.funnypictures.model.Picture;
import com.optigra.funnypictures.model.tag.Tag;
import com.optigra.funnypictures.model.thumbnail.FunnyPictureThumbnail;
import com.optigra.funnypictures.pagination.PagedResult;
import com.optigra.funnypictures.pagination.PagedSearch;
import com.optigra.funnypictures.service.funnypicture.FunnyPictureService;
import com.optigra.funnypictures.service.picture.PictureService;
import com.optigra.funnypictures.service.thumbnail.ThumbnailGeneratorService;
import com.optigra.funnypictures.service.thumbnail.funny.FunnyPictureThumbnailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	
	private static final String THUMBNAIL_PATH_PREFIX = "thumbnail";
	
	@Value("${facade.picture.label.text}")
	private String labelText;

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
	private Converter<PagedRequest<FunnyPictureResource>, PagedSearch<FunnyPicture>> pagedRequestConverter;

	@Resource(name = "pagedSearchConverter")
	private Converter<PagedResult<?>, PagedResultResource<? extends ApiResource>> pagedResultConverter;

	@Resource(name = "funnyPictureConverter")
	private Converter<FunnyPicture, FunnyPictureResource> funnyPictureConverter;

	@Resource(name = "funnyPictureResourceConverter")
	private Converter<FunnyPictureResource, FunnyPicture> funnyPictureResourceConverter;

	@Resource(name = "namingStrategy")
	private ContentResourceNamingStrategy namingStrategy;
	
	@Resource(name = "thumbnailNamingStrategy")
	private ContentResourceNamingStrategy thumbnailNamingStrategy;

	@Resource(name = "thumbnailGeneratorService")
	private ThumbnailGeneratorService thumbnailGeneratorService;

	@Value("${funnies.missing.generate.thumbnails}")
	private Boolean generateMissingFunnyThumbnails;

	@Override
	public PagedResultResource<FunnyPictureResource> getFunnies(final PagedRequest<FunnyPictureResource> pagedRequest) {

		LOG.info("Get funnies by paged request: {}", pagedRequest);

		PagedSearch<FunnyPicture> pagedSearch = pagedRequestConverter.convert(pagedRequest);

		PagedResult<FunnyPicture> pagedResult = funnyPictureService.getFunnyPictures(pagedSearch);
		generateMissingFunnies(pagedResult.getEntities());

		List<FunnyPictureResource> resources = funnyPictureConverter.convertAll(pagedResult.getEntities());

		PagedResultResource<FunnyPictureResource> pagedResultResource = new PagedResultResource<>("/funnies");
		pagedResultResource.setEntities(resources);
		pagedResultConverter.convert(pagedResult, pagedResultResource);

		return pagedResultResource;
	}

	/**
	 * Method for generating Missing Funnies.
	 * @param funnies
	 */
	private void generateMissingFunnies(final List<FunnyPicture> funnies) {
	
		if (generateMissingFunnyThumbnails) {
			
			for (FunnyPicture picture : funnies) {
				
				if (picture.getThumbnails().isEmpty()) {
					String path = picture.getUrl();
					generateThumbnails(path, picture);
				}
			}
			
		}
		
	}

	@Override
	public FunnyPictureResource createFunnyPicture(final FunnyPictureResource funny) {
		LOG.info("Generate funny image for : {}", funny);

		PictureResource picture = funny.getTemplate();
		Assert.notNull(picture, "Template should be provided");
		// Get template
		Picture template = pictureService.getPicture(picture.getId());
		// Load template content
		Content templateContent = contentService.getContentByPath(template.getUrl());
		
		// Generate meme
		AdviceMemeContext context = new AdviceMemeContext(templateContent.getContentStream(), 
				templateContent.getMimeType(), funny.getHeader(), funny.getFooter());
		ImageHandle generatedMeme = memeGenerator.generate(context);
		// FIXME This should be another temporary file rather than content stored in the repository
		Content unlabelledPictureContent = toContent(generatedMeme);
		contentService.saveContent(unlabelledPictureContent);
		String unlabelledPicturePath = unlabelledPictureContent.getPath();
		
		// Append label		
		ImageLabellingContext labellingContext = new ImageLabellingContext(
				contentService.getContentByPath(unlabelledPicturePath).getContentStream(), 
				generatedMeme.getImageFormat(), labelText);
		ImageHandle labelledImage = imageLabeller.generate(labellingContext);
		
		Content content = toContent(labelledImage);
		// Save generated meme's content to data storage
		contentService.saveContent(content);
		
		// Save funny picture to DB
		FunnyPicture funnyPicture = saveFunnyPicture(funny, template, content);

		// Generate thumbnails from the unlabelled version of the image
		generateThumbnails(unlabelledPicturePath, funnyPicture);
		
		return funnyPictureConverter.convert(funnyPicture);
	}

	/**
	 * Method for generating Thumbnails.
	 * @param path
	 * @param funnyPicture
	 */
	private void generateThumbnails(final String path, final FunnyPicture funnyPicture) {
		
		List<ThumbnailContent> thumbnails = thumbnailGeneratorService.generateThumbnails(contentService.getContentByPath(path));
		
		for (ThumbnailContent thumbnailContent : thumbnails) {
			ContentResource thumbnailResource = new ContentResource();
			thumbnailResource.setMimeType(thumbnailContent.getMimeType());
			String thumbnailUrl = thumbnailNamingStrategy.createIdentifier(THUMBNAIL_PATH_PREFIX, thumbnailResource);
			thumbnailContent.setPath(thumbnailUrl);
			contentService.saveContent(thumbnailContent);
		}
		
		List<FunnyPictureThumbnail> funnyPictureThumbnails = createFunnyPictureThumbnails(thumbnails, funnyPicture);
		funnyPicture.setThumbnails(funnyPictureThumbnails);
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
	 * @param funnyPictureResource
	 *            resource with name,header and footer text.
	 * @param template
	 *            base for creating funny picture
	 * @param content
	 *            with url to content storage
	 * @return created funny picture
	 */
	private FunnyPicture saveFunnyPicture(final FunnyPictureResource funnyPictureResource, final Picture template, final Content content) {
		// Save generated meme
		FunnyPicture funnyPicture = funnyPictureResourceConverter.convert(funnyPictureResource);
		funnyPicture.setPicture(template);
		funnyPicture.setUrl(content.getPath());
		if (funnyPicture.getTags() != null) {
			for (Tag tag : funnyPicture.getTags()) {
				tag.setCreateDate(new Date());
				tag.setUpdateDate(new Date());
			}
		}

		return funnyPictureService.createFunnyPicture(funnyPicture);
	}


	/**
	 * Converts an ImageHandle to a Content with an assigned identifier.
	 * @param handle the handle to the data stream of an image
	 * @return a Content with the same data stream
	 */
	private Content toContent(final ImageHandle handle) {

		ContentResource memeResource = new ContentResource();
		memeResource.setMimeType(handle.getImageFormat());
		String memePath = namingStrategy.createIdentifier(memeResource);

		Content content = new Content();
		content.setPath(memePath);
		content.setMimeType(handle.getImageFormat());
		content.setContentStream(handle.getImageInputStream());

		return content;
	}

	@Override
	public PagedResultResource<FunnyPictureResource> getFunniesForPicture(final Long id, final PagedRequest<FunnyPictureResource> pagedRequest) {
		
		PagedSearch<FunnyPicture> pagedSearch = pagedRequestConverter.convert(pagedRequest);
		
		PagedResult<FunnyPicture> pagedResult = funnyPictureService.getFunnyPicturesByPicture(pagedSearch, id);
		
		List<FunnyPictureResource> resources = funnyPictureConverter.convertAll(pagedResult.getEntities());
		
		PagedResultResource<FunnyPictureResource> pagedResultResource = new PagedResultResource<>("/funnies");
		pagedResultResource.setEntities(resources);
		pagedResultConverter.convert(pagedResult, pagedResultResource);
		
		return pagedResultResource;
	}

	@Override
	public FunnyPictureResource getFunnyPicture(final Long id) {
		FunnyPicture funnyPicture = funnyPictureService
				.getFunnyPicture(id);
		return funnyPictureConverter.convert(funnyPicture);
	}

	@Override
	public void deleteFunnyPicture(final Long id) {
		FunnyPicture funnyPicture = funnyPictureService.getFunnyPicture(id);
		funnyPictureService.deleteFunnyPicture(funnyPicture);
	}

	public void setGenerateMissingFunnyThumbnails(final Boolean generateMissingFunnyThumbnails) {
		this.generateMissingFunnyThumbnails = generateMissingFunnyThumbnails;
	}

}
