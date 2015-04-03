package com.optigra.funnypictures.dao.tag;

import com.optigra.funnypictures.dao.AbstractDao;
import com.optigra.funnypictures.model.tag.Tag;
import com.optigra.funnypictures.queries.Queries;
import com.optigra.funnypictures.queries.Query;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * Created by oleh on 10.03.15.
 */
@Repository("tagDao")
public class DefaultTagDao extends AbstractDao<Tag, Long> implements TagDao {
    @Override
    public Class<Tag> getEntityClass() {
        return Tag.class;
    }

    @Override
    public List<Tag> getTags() {
        Queries query = Queries.GET_TAGS;

        Query<Tag> finalQuery = new Query<>(getEntityClass(), query.getQuery(), new HashMap<>());

        return getPersistenceManager().executeMultipleResultQuery(finalQuery);
    }
}
