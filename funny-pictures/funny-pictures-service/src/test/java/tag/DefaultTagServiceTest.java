package tag;

import com.optigra.funnypictures.dao.tag.TagDao;
import com.optigra.funnypictures.model.tag.Tag;
import com.optigra.funnypictures.service.tag.DefaultTagService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by oleh on 06.04.15.
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultTagServiceTest {

    @Mock
    private TagDao tagDao;

    @InjectMocks
    private DefaultTagService unit;

    @Test
    public void testGetTags() {
        // Given
        Long id = 1L;
        String name = "Name";

        Tag tag = new Tag();
        tag.setId(id);
        tag.setName(name);

        List<Tag> expected = Arrays.asList(tag);

        // When
        when(tagDao.getTags()).thenReturn(expected);
        List<Tag> actual = unit.getTags();

        // Then
        verify(tagDao).getTags();
        assertEquals(expected, actual);
    }

    @Test
    public void testCreateTag() {
        // Given
        Long id = 1L;
        String name = "Name";

        Tag tag = new Tag();
        tag.setId(id);
        tag.setName(name);

        // When
        unit.createTag(tag);

        // Then
        verify(tagDao).save(tag);
    }
}
