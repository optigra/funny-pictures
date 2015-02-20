package com.optigra.funnypictures.web.picture;

import com.optigra.funnypictures.facade.facade.funny.FunnyPictureFacade;
import com.optigra.funnypictures.facade.facade.thumbnail.funny.FunnyPictureThumbnailFacade;
import com.optigra.funnypictures.facade.resources.message.MessageResource;
import com.optigra.funnypictures.facade.resources.message.MessageType;
import com.optigra.funnypictures.facade.resources.picture.FunnyPictureResource;
import com.optigra.funnypictures.facade.resources.search.PagedRequest;
import com.optigra.funnypictures.facade.resources.search.PagedResultResource;
import com.optigra.funnypictures.facade.resources.thumbnail.funny.FunnyPictureThumbnailResource;
import com.optigra.funnypictures.model.thumbnail.ThumbnailType;
import com.optigra.funnypictures.web.AbstractControllerTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class FunnyPictureControllerTest extends AbstractControllerTest {

	@InjectMocks
	private FunnyPictureController unit;
	
	@Mock
	private FunnyPictureFacade funnyPictureFacade;

    @Mock
    private FunnyPictureThumbnailFacade funnyPictureThumbnailFacade;
	
	private MockMvc mockMvc;
	
    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(unit).build();
    }
    
    @Test
	public void testGetFunnies() throws Exception {
		// Given
    	FunnyPictureResource entity1 = new FunnyPictureResource();
    	entity1.setId(1L);
    	entity1.setName("Picture1");
    	entity1.setUrl("url");

    	long count = 100;
    	int limit = 25;
    	Integer offset = 10;
    	String uri = "/funnies";
		List<FunnyPictureResource> entities = Arrays.asList(entity1);
    	PagedResultResource<FunnyPictureResource> expectedResource = new PagedResultResource<>();
		expectedResource.setCount(count);
		expectedResource.setLimit(limit);
		expectedResource.setOffset(offset);
		expectedResource.setUri(uri);
		expectedResource.setEntities(entities);
    	
		FunnyPictureResource resource = new FunnyPictureResource();
		PagedRequest<FunnyPictureResource> pagedRequest = new PagedRequest<FunnyPictureResource>(resource, offset, limit);
		
		// When
    	String expectedResponse = getJson(expectedResource, false);
    	when(funnyPictureFacade.getFunnies(any(PagedRequest.class))).thenReturn(expectedResource);
    	
		// Then
    	mockMvc.perform(get("/funnies")
    			.param("offset", String.valueOf(offset))
    			.param("limit", String.valueOf(limit)))
    		.andExpect(status().isOk())
    		.andExpect(content().string(expectedResponse));
    	
    	verify(funnyPictureFacade).getFunnies(pagedRequest);
	}
    
    @Test
	public void testGetFunnyPicture() throws Exception {
    	Long id = 1L;
    	
    	FunnyPictureResource funnyPictureResource = new FunnyPictureResource();
    	funnyPictureResource.setId(id);
    	funnyPictureResource.setUrl("url");
    	
    	String expectedResponse = getJson(funnyPictureResource, false);
		
		// When
		when(funnyPictureFacade.getFunnyPicture(anyLong())).thenReturn(funnyPictureResource);
    	
		// Then
    	mockMvc.perform(get("/funnies/{id}", id))
    		.andExpect(status().isOk())
    		.andExpect(content().string(expectedResponse));
    	
    	verify(funnyPictureFacade).getFunnyPicture(id);
	}
    
    @Test
   	public void testCreateFunnyPicture() throws Exception {
   		// Given
       	FunnyPictureResource funnyPicture = new FunnyPictureResource();
       	funnyPicture.setName("Funny Picture1");
       	funnyPicture.setUrl("url");
       	funnyPicture.setHeader("Header");
       	funnyPicture.setFooter("Footer");

       	String requestBody = getJson(funnyPicture, true);
       	String expectedResponse = getJson(funnyPicture, false);
       	
       	// When
       	when(funnyPictureFacade.createFunnyPicture(any(FunnyPictureResource.class))).thenReturn(funnyPicture);
       	
   		// Then

       	mockMvc.perform(post("/funnies")
       			.contentType(MediaType.APPLICATION_JSON)
       			.content(requestBody))
       		.andExpect(status().isCreated())
       		.andExpect(content().string(expectedResponse));
       	
       	verify(funnyPictureFacade).createFunnyPicture(funnyPicture);
   	}
    
    @Test
    public void testDeleteFunnyPicture() throws Exception {
    	
    	// Given
    	Long id = 2L;
    	
    	// When
    	MessageResource expectedResource = new MessageResource(MessageType.INFO, "Funny Picture Resource was deleted");
    	String expectedResponse = objectMapper.writeValueAsString(expectedResource);
    	
    	// Then
    	mockMvc.perform(delete("/funnies/{id}", id)
    			.contentType(MediaType.APPLICATION_JSON))
    			.andExpect(status().is(204))
    			.andExpect(content().string(expectedResponse));
    	verify(funnyPictureFacade).deleteFunnyPicture(id);
    }

    @Test
    public void testGetFunnyPicturesByPicture() throws Exception {
        // Given
        Long id = 1L;
        long count = 100;
        int limit = 25;
        Integer offset = 10;
        String uri = "/funnies";

        FunnyPictureResource resource = new FunnyPictureResource();
        resource.setId(id);
        resource.setName("Picture1");
        resource.setUrl("url");

        List<FunnyPictureResource> entities = Arrays.asList(resource);
        PagedResultResource<FunnyPictureResource> expectedResource = new PagedResultResource<>();
        expectedResource.setCount(count);
        expectedResource.setLimit(limit);
        expectedResource.setOffset(offset);
        expectedResource.setUri(uri);
        expectedResource.setEntities(entities);

        PagedRequest pagedRequest = new PagedRequest(offset, limit);

        // When
        String expectedResponse = objectMapper.writeValueAsString(expectedResource);
        when(funnyPictureFacade.getFunniesForPicture(anyLong(), any(PagedRequest.class))).thenReturn(expectedResource);

        // Then
        mockMvc.perform(get("/funnies/{id}/funnies", id)
                .param("offset", String.valueOf(offset))
                .param("limit", String.valueOf(limit)))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponse));

        verify(funnyPictureFacade).getFunniesForPicture(id, pagedRequest);
    }

    @Test
    public void testGetFunnyPictureThumbnailsByPicture() throws Exception {
        // Given
        Long id = 1L;
        long count = 100;
        int limit = 25;
        Integer offset = 10;
        String uri = "/funnies";
        ThumbnailType thumbnailType = ThumbnailType.MEDIUM;

        FunnyPictureThumbnailResource resource = new FunnyPictureThumbnailResource();
        resource.setThumbnailType(thumbnailType);;

        List<FunnyPictureThumbnailResource> entities = Arrays.asList(resource);
        PagedResultResource<FunnyPictureThumbnailResource> expectedResource = new PagedResultResource<>();
        expectedResource.setCount(count);
        expectedResource.setLimit(limit);
        expectedResource.setOffset(offset);
        expectedResource.setUri(uri);
        expectedResource.setEntities(entities);

        PagedRequest pagedRequest = new PagedRequest(resource, offset, limit);

        // When
        String expectedResponse = objectMapper.writeValueAsString(expectedResource);
        when(funnyPictureThumbnailFacade.getFunniesThumbnailForPicture(anyLong(), any(PagedRequest.class))).thenReturn(expectedResource);

        // Then
        mockMvc.perform(get("/funnies/{id}/funniesThumb", id)
                .param("offset", String.valueOf(offset))
                .param("limit", String.valueOf(limit))
                .param("type", thumbnailType.name()))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponse));

        verify(funnyPictureThumbnailFacade).getFunniesThumbnailForPicture(id, pagedRequest);
    }
}
