package com.optigra.funnypictures.web.picture;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.optigra.funnypictures.facade.resources.picture.PictureResource;
import com.optigra.funnypictures.facade.resources.search.PagedResultResource;
import com.optigra.funnypictures.web.BaseController;

@RestController("/pictures")
public class PictureController extends BaseController {

	@RequestMapping
	public PagedResultResource<PictureResource> getPictures(@RequestParam(value = "offset", defaultValue = "0") Long offset,
			@RequestParam(value = "limit", defaultValue = "20") Integer limit) {
    	PictureResource entity1 = new PictureResource();
    	entity1.setId(1L);
    	entity1.setName("Picture1");
    	entity1.setUrl("url");

    	long count = 100;
    	String uri = "/pictures";
		List<PictureResource> entities = Arrays.asList(entity1);
    	PagedResultResource<PictureResource> expectedResource = new PagedResultResource<>();
		expectedResource.setCount(count);
		expectedResource.setLimit(limit);
		expectedResource.setOffset(offset);
		expectedResource.setUri(uri);
		expectedResource.setEntities(entities);
		
		return expectedResource;
	}
	
}
