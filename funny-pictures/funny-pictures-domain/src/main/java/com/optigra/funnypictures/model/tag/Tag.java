package com.optigra.funnypictures.model.tag;

import com.optigra.funnypictures.model.FunnyPicture;
import com.optigra.funnypictures.model.Model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by oleh on 10.03.15.
 */
@Entity
@Table(name = "tag")
public class Tag extends Model {
    private static final long serialVersionUID = 1L;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "tags", cascade = CascadeType.ALL)
    private List<FunnyPicture> funnyPictures;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public List<FunnyPicture> getFunnyPictures() {
        return funnyPictures;
    }

    public void setFunnyPictures(List<FunnyPicture> funnyPictures) {
        this.funnyPictures = funnyPictures;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        Tag tag = (Tag) o;

        if (name != null ? !name.equals(tag.name) : tag.name != null) {
            return false;
        }
        return !(funnyPictures != null ? !funnyPictures.equals(tag.funnyPictures) : tag.funnyPictures != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (funnyPictures != null ? funnyPictures.hashCode() : 0);
        return result;
    }
}
