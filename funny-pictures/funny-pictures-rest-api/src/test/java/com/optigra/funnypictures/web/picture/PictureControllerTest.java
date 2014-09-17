package com.optigra.funnypictures.web.picture;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.optigra.funnypictures.facade.resources.picture.PictureResource;
import com.optigra.funnypictures.facade.resources.search.PagedResultResource;

@RunWith(MockitoJUnitRunner.class)
public class PictureControllerTest {

	@InjectMocks
	private PictureController unit;
	
	private MockMvc mockMvc;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(unit).build();
    }
    
    @Test
	public void testGetPictures() throws Exception {
		// Given
    	PictureResource entity1 = new PictureResource();
    	entity1.setId(1L);
    	entity1.setName("Picture1");
    	entity1.setUrl("url");

    	long count = 100;
    	int limit = 25;
    	Long offset = 10L;
    	String uri = "/pictures";
		List<PictureResource> entities = Arrays.asList(entity1);
    	PagedResultResource<PictureResource> expectedResource = new PagedResultResource<>();
		expectedResource.setCount(count);
		expectedResource.setLimit(limit);
		expectedResource.setOffset(offset);
		expectedResource.setUri(uri);
		expectedResource.setEntities(entities);
    	
		// When
    	String expectedResponse = objectMapper.writeValueAsString(expectedResource);

		// Then
    	mockMvc.perform(get("/pictures")
    			.param("offset", String.valueOf(offset))
    			.param("limit", String.valueOf(limit)))
    		.andExpect(status().isOk())
    		.andExpect(content().string(expectedResponse));
	}
}
