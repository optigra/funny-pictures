package com.optigra.funnypictures.facade.converter.tag;

import com.optigra.funnypictures.facade.converter.AbstractConverter;
import com.optigra.funnypictures.facade.resources.tag.TagResource;
import com.optigra.funnypictures.model.tag.Tag;
import org.springframework.stereotype.Component;

/**
 * Created by oleh on 10.03.15.
 */
@Component("tagResourceConverter")
public class TagResourceConverter extends AbstractConverter<TagResource, Tag> {

    @Override
    public Tag convert(final TagResource source, final Tag target) {
        target.setId(source.getId());
        target.setName(source.getName());

        return target;
    }

    @Override
    public Tag convert(final TagResource source) {
        return convert(source, new Tag());
    }

}
