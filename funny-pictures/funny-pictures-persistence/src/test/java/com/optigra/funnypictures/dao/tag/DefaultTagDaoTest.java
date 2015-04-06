package com.optigra.funnypictures.dao.tag;

import com.optigra.funnypictures.dao.persistence.PersistenceManager;
import com.optigra.funnypictures.model.tag.Tag;
import com.optigra.funnypictures.queries.Queries;
import com.optigra.funnypictures.queries.Query;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by oleh on 03.04.15.
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultTagDaoTest {
    @Mock
    private PersistenceManager<Tag, Long> persistenceManager;

    @InjectMocks
    private DefaultTagDao unit;

    @Test
    public void testGetTags() {

        // Given
        List<Tag> expected = Arrays.asList(new Tag());

        Queries query = Queries.GET_TAGS;
        Query<Tag> finalQuery = new Query<>(Tag.class, query.getQuery(), new HashMap<>());

        // When
        when(persistenceManager.executeMultipleResultQuery(Matchers.<Query<Tag>>any())).thenReturn(expected);
        List<Tag> actual = unit.getTags();

        // Then
        verify(persistenceManager).executeMultipleResultQuery(finalQuery);
        assertEquals(expected, actual);
    }
}
