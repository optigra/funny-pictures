package com.optigra.funnypictures.web.thumbnail.funny;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.optigra.funnypictures.facade.facade.thumbnail.funny.FunnyPictureThumbnailFacade;
import com.optigra.funnypictures.facade.resources.search.PagedRequest;
import com.optigra.funnypictures.facade.resources.search.PagedResultResource;
import com.optigra.funnypictures.facade.resources.thumbnail.funny.FunnyPictureThumbnailResource;
import com.optigra.funnypictures.model.thumbnail.ThumbnailType;
import com.optigra.funnypictures.web.AbstractControllerTest;
import com.optigra.funnypictures.web.thumbnail.FunnyPictureThumbnailController;

@RunWith(MockitoJUnitRunner.class)
public class FunnyPictureThumbnailControllerTest extends AbstractControllerTest {
	@InjectMocks
	private FunnyPictureThumbnailController unit;
	
	@Mock
	private FunnyPictureThumbnailFacade funnyPictureThumbnailFacade;
	
	private MockMvc mockMvc;
	
    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(unit).build();
    }
    
    @Test
   	public void testGetPictures() throws Exception {
       	long count = 100;
       	int limit = 25;
       	Integer offset = 10;
       	String uri = "/funniesthumb";
    	ThumbnailType thumbnailType = ThumbnailType.MEDIUM;
    	
   		// Given
       	FunnyPictureThumbnailResource funnyPictureThumbnailResource = new FunnyPictureThumbnailResource();
       	funnyPictureThumbnailResource.setThumbnailType(thumbnailType);

   		List<FunnyPictureThumbnailResource> entities = Arrays.asList(funnyPictureThumbnailResource);
       	PagedResultResource<FunnyPictureThumbnailResource> expectedResource = new PagedResultResource<>();
   		expectedResource.setCount(count);
   		expectedResource.setLimit(limit);
   		expectedResource.setOffset(offset);
   		expectedResource.setUri(uri);
   		expectedResource.setEntities(entities);
       	
   		PagedRequest pagedRequest = new PagedRequest(funnyPictureThumbnailResource, offset, limit);
   		
   		// When
       	String expectedResponse = objectMapper.writeValueAsString(expectedResource);
       	when(funnyPictureThumbnailFacade.getFunnyPicturesThumbnails(any(PagedRequest.class))).thenReturn(expectedResource);
       	
   		// Then
       	mockMvc.perform(get("/funniesthumb")
       			.param("offset", String.valueOf(offset))
       			.param("limit", String.valueOf(limit))
       			.param("type", thumbnailType.name()))
       		.andExpect(status().isOk())
       		.andExpect(content().string(expectedResponse));
       	
       	verify(funnyPictureThumbnailFacade).getFunnyPicturesThumbnails(pagedRequest);
   	}
    
    @Test
	public void testGetThumbnail() throws Exception {
    	ThumbnailType thumbnailType = ThumbnailType.MEDIUM;
    	Long id = 1L;
    	Long pictureId = 4L;
    	
    	FunnyPictureThumbnailResource funnyPictureThumbnailResource = new FunnyPictureThumbnailResource();
    	funnyPictureThumbnailResource.setId(id);
		funnyPictureThumbnailResource.setFunnyPictureId(pictureId);
    	funnyPictureThumbnailResource.setUrl("url");
    	funnyPictureThumbnailResource.setThumbnailType(thumbnailType);
    	
    	String expectedResponse = getJson(funnyPictureThumbnailResource, false);
		
		// When
		when(funnyPictureThumbnailFacade.getFunnyPictureThumbnail(anyLong())).thenReturn(funnyPictureThumbnailResource);
    	
		// Then
    	mockMvc.perform(get("/funniesthumb/{id}", id))
    		.andExpect(status().isOk())
    		.andExpect(content().string(expectedResponse));
    	
    	verify(funnyPictureThumbnailFacade).getFunnyPictureThumbnail(id);
	}
}
