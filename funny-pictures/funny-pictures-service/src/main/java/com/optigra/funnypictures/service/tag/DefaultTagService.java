package com.optigra.funnypictures.service.tag;

import com.optigra.funnypictures.dao.tag.TagDao;
import com.optigra.funnypictures.model.tag.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by oleh on 10.03.15.
 */
@Service("tagService")
public class DefaultTagService implements TagService {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultTagService.class);

    @Resource(name = "tagDao")
    private TagDao tagDao;

    @Override
    public List<Tag> getTags() {
        return tagDao.getTags();
    }

    @Override
    public void createTag(final Tag tag) {
        LOG.info("Creating tag entity: {}", tag);
        tagDao.save(tag);
    }
}
