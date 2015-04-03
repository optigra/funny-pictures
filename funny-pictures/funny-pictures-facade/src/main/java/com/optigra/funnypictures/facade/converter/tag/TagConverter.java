package com.optigra.funnypictures.facade.converter.tag;

import com.optigra.funnypictures.facade.converter.AbstractConverter;
import com.optigra.funnypictures.facade.resources.tag.TagResource;
import com.optigra.funnypictures.model.tag.Tag;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oleh on 10.03.15.
 */
@Component("tagConverter")
public class TagConverter extends AbstractConverter<Tag, TagResource> {
    @Override
    public TagResource convert(final Tag source, final TagResource target) {
        target.setId(source.getId());
        target.setName(source.getName());

        return target;
    }

    @Override
    public TagResource convert(final Tag source) {
        return convert(source, new TagResource());
    }

    @Override
    public List<TagResource> convertAll(final List<Tag> source, final List<TagResource> target) {
        if (source != null) {
            for (Tag tag : source) {
                target.add(convert(tag));
            }
        }
        return target;
    }

    @Override
    public List<TagResource> convertAll(final List<Tag> source) {
        return convertAll(source, new ArrayList<>());
    }
}
