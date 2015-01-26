package com.optigra.funnypictures.web.thumbnail;

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

import com.optigra.funnypictures.facade.facade.thumbnail.PictureThumbnailFacade;
import com.optigra.funnypictures.facade.resources.search.PagedRequest;
import com.optigra.funnypictures.facade.resources.search.PagedResultResource;
import com.optigra.funnypictures.facade.resources.thumbnail.PictureThumbnailResource;
import com.optigra.funnypictures.model.thumbnail.ThumbnailType;
import com.optigra.funnypictures.web.AbstractControllerTest;

@RunWith(MockitoJUnitRunner.class)
public class PictureThumbnailControllerTest extends AbstractControllerTest {
	
	@InjectMocks
	private PictureThumbnailController unit;
	
	@Mock
	private PictureThumbnailFacade pictureThumbnailFacade;
	
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
       	String uri = "/picturesthumb";
    	ThumbnailType thumbnailType = ThumbnailType.MEDIUM;
    	
   		// Given
       	PictureThumbnailResource pictureThumbnailResource = new PictureThumbnailResource();
       	pictureThumbnailResource.setThumbnailType(thumbnailType);
       	
   		List<PictureThumbnailResource> entities = Arrays.asList(pictureThumbnailResource);
       	PagedResultResource<PictureThumbnailResource> expectedResource = new PagedResultResource<>();
   		expectedResource.setCount(count);
   		expectedResource.setLimit(limit);
   		expectedResource.setOffset(offset);
   		expectedResource.setUri(uri);
   		expectedResource.setEntities(entities);
       	
   		PagedRequest pagedRequest = new PagedRequest(pictureThumbnailResource, offset, limit);
   		
   		// When
       	String expectedResponse = objectMapper.writeValueAsString(expectedResource);
       	when(pictureThumbnailFacade.getPicturesThumbnails(any(PagedRequest.class))).thenReturn(expectedResource);
       	
   		// Then
       	mockMvc.perform(get("/picturesthumb")
       			.param("offset", String.valueOf(offset))
       			.param("limit", String.valueOf(limit))
       			.param("type", thumbnailType.name()))
       		.andExpect(status().isOk())
       		.andExpect(content().string(expectedResponse));
       	
       	verify(pictureThumbnailFacade).getPicturesThumbnails(pagedRequest);
   	}
    
    @Test
	public void testGetThumbnail() throws Exception {
    	ThumbnailType thumbnailType = ThumbnailType.MEDIUM;
    	Long id = 1L;
    	Long pictureId = 4L;
    	
    	PictureThumbnailResource pictureThumbnailResource = new PictureThumbnailResource();
    	pictureThumbnailResource.setId(id);
		pictureThumbnailResource.setPictureId(pictureId);
    	pictureThumbnailResource.setUrl("url");
    	pictureThumbnailResource.setThumbnailType(thumbnailType);
    	
    	String expectedResponse = getJson(pictureThumbnailResource, false);
		
		// When
		when(pictureThumbnailFacade.getPictureThumbnail(anyLong())).thenReturn(pictureThumbnailResource);
    	
		// Then
    	mockMvc.perform(get("/picturesthumb/{id}", id))
    		.andExpect(status().isOk())
    		.andExpect(content().string(expectedResponse));
    	
    	verify(pictureThumbnailFacade).getPictureThumbnail(id);
	}
}
