package com.optigra.funnypictures.facade.resources.tag;

import com.optigra.funnypictures.facade.resources.ApiResource;

import java.text.MessageFormat;

/**
 * Created by oleh on 10.03.15.
 */
public class TagResource extends ApiResource {

    private String name;

    @Override
    public String getUri() {
        return MessageFormat.format("/pictures/thumbnails/{0}", getId());
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TagResource that = (TagResource) o;

        return !(name != null ? !name.equals(that.name) : that.name != null);

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "TagResource{"
                + "name='" + name + '\''
                + "} " + super.toString();
    }
}
