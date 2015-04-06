package com.optigra.funnypictures.facade.converter.tag;

import com.optigra.funnypictures.facade.resources.tag.TagResource;
import com.optigra.funnypictures.model.tag.Tag;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

/**
 * Created by oleh on 03.04.15.
 */
@RunWith(MockitoJUnitRunner.class)
public class TagResourceConverterTest {

    private TagResourceConverter unit = new TagResourceConverter();

    @Test
    public void testConvert() throws Exception {
        // Given
        Long id = 1L;
        String name = "Name";

        TagResource source = new TagResource();
        source.setId(id);
        source.setName(name);

        Tag expected = new Tag();
        expected.setId(id);
        expected.setName(name);

        // When
        Tag actual = unit.convert(source);

        // Then
        assertEquals(expected, actual);
    }

    @Test
    public void testConvertToResource() {

        // Given
        Long id = 1L;
        String name = "Name";

        TagResource source = new TagResource();
        source.setId(id);
        source.setName(name);

        Tag expected = new Tag();
        expected.setId(id);
        expected.setName(name);

        // When
        Tag actual = new Tag();
        actual = unit.convert(source, actual);

        // Then
        assertEquals(expected, actual);

    }
}
