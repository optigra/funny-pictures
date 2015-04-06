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
public class TagConverterTest {

    private TagConverter unit = new TagConverter();

    @Test
    public void testConvert() throws Exception {
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
        TagResource actual = unit.convert(source);

        // Then
        assertEquals(expected, actual);
    }

    @Test
    public void testConvertToResource() {

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
        TagResource actualResource = new TagResource();
        actualResource = unit.convert(source, actualResource);

        // Then
        assertEquals(expected, actualResource);

    }
}
