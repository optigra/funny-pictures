package com.optigra.funnypictures.web.tag;

import com.optigra.funnypictures.facade.facade.tag.TagFacade;
import com.optigra.funnypictures.facade.resources.tag.TagResource;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by oleh on 06.04.15.
 */
@RunWith(MockitoJUnitRunner.class)
public class TagControllerTest extends AbstractControllerTest {

    @Mock
    private TagFacade tagFacade;

    @InjectMocks
    private TagController unit;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(unit).build();
    }

    @Test
    public void testGetTags() throws Exception {
        // Given
        Long id = 1L;
        String name = "Name";

        TagResource entity = new TagResource();
        entity.setId(id);
        entity.setName(name);

        List<TagResource> expectedResource = Arrays.asList(entity);
        String expectedResponse = objectMapper.writeValueAsString(expectedResource);

        // When
        when(tagFacade.getTags()).thenReturn(expectedResource);

        // Then
        mockMvc.perform(get("/tags")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponse));

        verify(tagFacade).getTags();
    }

    @Test
    public void testCreateTag() throws Exception {
        // Given
        Long id = 1L;
        String name = "Name";

        TagResource expectedResource = new TagResource();
        expectedResource.setId(id);
        expectedResource.setName(name);

        String request = getJson(expectedResource, true);
        String response = getJson(expectedResource, false);

        // When
        when(tagFacade.createTag(any(TagResource.class))).thenReturn(expectedResource);

        // Then
        mockMvc.perform(post("/tags")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isCreated())
                .andExpect(content().string(response));

        verify(tagFacade).createTag(expectedResource);
    }
}
