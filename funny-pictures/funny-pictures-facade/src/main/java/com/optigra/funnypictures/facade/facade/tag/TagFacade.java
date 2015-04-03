package com.optigra.funnypictures.facade.facade.tag;

import com.optigra.funnypictures.facade.resources.tag.TagResource;

import java.util.List;

/**
 * Created by oleh on 10.03.15.
 */
public interface TagFacade {

    /**
     * Method to get all tags.
     * @return List of tags.
     */
    List<TagResource> getTags();

    /**
     * Method to create tag.
     * @param tagResource
     * @return TagResource.
     */
    TagResource createTag(TagResource tagResource);

    /**
     * Add missed tags to the database.
     * @param tagResources
     */
    void addMissedTags(List<TagResource> tagResources);
}
