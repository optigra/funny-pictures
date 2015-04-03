package com.optigra.funnypictures.facade.facade.tag;

import com.optigra.funnypictures.facade.converter.Converter;
import com.optigra.funnypictures.facade.resources.tag.TagResource;
import com.optigra.funnypictures.model.tag.Tag;
import com.optigra.funnypictures.service.tag.TagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by oleh on 10.03.15.
 */
@Transactional
@Component("tagFacade")
public class DefaultTagFacade implements TagFacade {
    private static final Logger LOG = LoggerFactory.getLogger(TagFacade.class);

    @Resource(name = "tagService")
    private TagService tagService;

    @Resource(name = "tagConverter")
    private Converter<Tag, TagResource> tagConverter;


    @Resource(name = "tagResourceConverter")
    private Converter<TagResource, Tag> tagResourceConverter;

    @Override
    public List<TagResource> getTags() {
        List<TagResource> resources = tagConverter.convertAll(tagService.getTags());
        return resources;
    }

    @Override
    public TagResource createTag(final TagResource tagResource) {
        LOG.info("Creating tag: {}", tagResource);
        Tag tag = tagResourceConverter.convert(tagResource);
        tag.setCreateDate(new Date());
        tag.setUpdateDate(new Date());
        tagService.createTag(tag);

        return tagConverter.convert(tag);
    }

    @Override
    public void addMissedTags(final List<TagResource> tagResources) {

    }
}
