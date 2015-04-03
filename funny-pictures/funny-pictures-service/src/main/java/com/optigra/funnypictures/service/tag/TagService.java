package com.optigra.funnypictures.service.tag;

import com.optigra.funnypictures.model.tag.Tag;

import java.util.List;

/**
 * Created by oleh on 10.03.15.
 */
public interface TagService {

    /**
     * Method returns list of tags.
     * @return List of tags.
     */
    List<Tag> getTags();


    /**
     * Method to create tag.
     * @param tag
     */
    void createTag(Tag tag);
}
