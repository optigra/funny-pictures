package com.optigra.funnypictures.dao.tag;

import com.optigra.funnypictures.dao.Dao;
import com.optigra.funnypictures.model.tag.Tag;

import java.util.List;

/**
 * Created by oleh on 10.03.15.
 */
public interface TagDao extends Dao<Tag, Long> {

    /**
     * Get all tags.
     * @return List of tags.
     */
    List<Tag> getTags();
}
