package com.optigra.funnypictures.web.comment;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.optigra.funnypictures.facade.facade.comment.FunnyPictureCommentFacade;
import com.optigra.funnypictures.facade.resources.comment.FunnyPictureCommentResource;
import com.optigra.funnypictures.facade.resources.search.PagedRequest;
import com.optigra.funnypictures.facade.resources.search.PagedResultResource;
import com.optigra.funnypictures.web.AbstractControllerTest;

@RunWith(MockitoJUnitRunner.class)
public class FunnyPictureCommentControllerTest extends AbstractControllerTest {

	@InjectMocks
	private FunnyPictureCommentController unit;
	
	@Mock
	private FunnyPictureCommentFacade funnyPictureCommentFacade;
	
	private MockMvc mockMvc;
	
    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(unit).build();
    }
    
    @Test
	public void testGetFunnies() throws Exception {
		// Given
    	FunnyPictureCommentResource entity1 = new FunnyPictureCommentResource();
    	entity1.setId(1L);

    	long count = 100;
    	int limit = 25;
    	Integer offset = 10;
    	String uri = "/funnies";
		List<FunnyPictureCommentResource> entities = Arrays.asList(entity1);
    	PagedResultResource<FunnyPictureCommentResource> expectedResource = new PagedResultResource<>();
		expectedResource.setCount(count);
		expectedResource.setLimit(limit);
		expectedResource.setOffset(offset);
		expectedResource.setUri(uri);
		expectedResource.setEntities(entities);
    	
		PagedRequest<FunnyPictureCommentResource> pagedRequest = new PagedRequest<FunnyPictureCommentResource>(new FunnyPictureCommentResource(), offset, limit);
		
		// When
    	String expectedResponse = getJson(expectedResource, false);
    	when(funnyPictureCommentFacade.getComments(Matchers.<PagedRequest<FunnyPictureCommentResource>>any())).thenReturn(expectedResource);
    	
		// Then
    	mockMvc.perform(get("/comments/funnies")
    			.param("offset", String.valueOf(offset))
    			.param("limit", String.valueOf(limit)))
    		.andExpect(status().isOk())
    		.andExpect(content().string(expectedResponse));
    	
    	verify(funnyPictureCommentFacade).getComments(pagedRequest);
	}
    
    @Test
   	public void testCreateFunnyPictureComment() throws Exception {
   		// Given
       	FunnyPictureCommentResource funnyPictureComment = new FunnyPictureCommentResource();

       	String requestBody = getJson(funnyPictureComment, true);
       	String expectedResponse = getJson(funnyPictureComment, false);
       	
       	// When
       	when(funnyPictureCommentFacade.createComment(any(FunnyPictureCommentResource.class))).thenReturn(funnyPictureComment);
       	
   		// Then

       	mockMvc.perform(post("/comments/funnies")
       			.contentType(MediaType.APPLICATION_JSON)
       			.content(requestBody))
       		.andExpect(status().isCreated())
       		.andExpect(content().string(expectedResponse));
       	
       	verify(funnyPictureCommentFacade).createComment(funnyPictureComment);
   	}
}
