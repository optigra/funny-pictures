package com.optigra.funnypictures.facade.facade.tag;

import com.optigra.funnypictures.facade.converter.Converter;
import com.optigra.funnypictures.facade.resources.tag.TagResource;
import com.optigra.funnypictures.model.tag.Tag;
import com.optigra.funnypictures.service.tag.TagService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by oleh on 03.04.15.
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultTagFacadeTest {
    @Mock
    private Converter<TagResource, Tag> tagResourceConverter;

    @Mock
    private Converter<Tag, TagResource> tagConverter;

    @Mock
    private TagService tagService;

    @InjectMocks
    private DefaultTagFacade unit;

    @Test
    public void testGetTags() throws Exception {
        // Given
        List<Tag> source = Collections.singletonList(new Tag());
        List<TagResource> expected = Collections.singletonList(new TagResource());

        // When
        when(tagService.getTags()).thenReturn(source);
        when(tagConverter.convertAll(anyListOf(Tag.class))).thenReturn(expected);
        List<TagResource> actual = unit.getTags();

        // Then
        verify(tagService).getTags();
        verify(tagConverter).convertAll(source);
        assertEquals(expected, actual);
    }

    @Test
    public void testCreateTag() throws Exception {
        // Given
        Long id = 1L;
        String name = "Name";

        Tag source = new Tag();
        source.setId(id);
        source.setName(name);

        TagResource expected = new TagResource();
        expected.setId(id);
        expected.setName(name);

        // When
        when(tagResourceConverter.convert(any(TagResource.class))).thenReturn(source);
        when(tagConverter.convert(any(Tag.class))).thenReturn(expected);

        TagResource actual = unit.createTag(expected);

        // Then
        verify(tagResourceConverter).convert(expected);
        verify(tagService).createTag(source);
        assertEquals(expected, actual);
    }
}
