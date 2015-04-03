package com.optigra.funnypictures.web.tag;

import com.optigra.funnypictures.facade.facade.tag.TagFacade;
import com.optigra.funnypictures.facade.resources.tag.TagResource;
import com.optigra.funnypictures.web.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by oleh on 10.03.15.
 */
@RestController
@RequestMapping(value = "/tags")
public class TagController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(TagController.class);

    @Resource(name = "tagFacade")
    private TagFacade tagFacade;

    /**
     * Method to get all tags.
     * @return List of TagResource.
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping
    public List<TagResource> getTags() {
        LOG.info("Retrieving tags");
        return tagFacade.getTags();
    }

    /**
     * Method to create tag. Requested method POST.
     * @param tagResource
     * @return TagResource.
     */
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    public TagResource createTag(@RequestBody final TagResource tagResource) {
        LOG.info("Create tag {}", tagResource);

        return tagFacade.createTag(tagResource);
    }
}
