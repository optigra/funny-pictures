package com.optigra.funnypictures.facade.converter.funny;

import com.optigra.funnypictures.facade.converter.AbstractConverter;
import com.optigra.funnypictures.facade.converter.Converter;
import com.optigra.funnypictures.facade.resources.picture.FunnyPictureResource;
import com.optigra.funnypictures.facade.resources.picture.PictureResource;
import com.optigra.funnypictures.facade.resources.tag.TagResource;
import com.optigra.funnypictures.model.FunnyPicture;
import com.optigra.funnypictures.model.Picture;
import com.optigra.funnypictures.model.tag.Tag;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by oleh on 11.03.15.
 */
@Component("funnyPictureResourceConverter")
public class FunnyPictureResourceConverter extends AbstractConverter<FunnyPictureResource, FunnyPicture> {

    @Resource(name = "pictureResourceConverter")
    private Converter<PictureResource, Picture> pictureResourceConverter;

    @Resource(name = "tagResourceConverter")
    private Converter<TagResource, Tag> tagResourceConverter;

    @Override
    public FunnyPicture convert(final FunnyPictureResource source, final FunnyPicture target) {
        target.setId(source.getId());
        target.setUrl(source.getUrl());
        target.setName(source.getName());
        target.setHeader(source.getHeader());
        target.setFooter(source.getFooter());
        target.setPicture(pictureResourceConverter.convert(source.getTemplate()));
        target.setTags(tagResourceConverter.convertAll(source.getTags()));

        return target;
    }

    @Override
    public FunnyPicture convert(final FunnyPictureResource source) {
        return convert(source, new FunnyPicture());
    }
}
