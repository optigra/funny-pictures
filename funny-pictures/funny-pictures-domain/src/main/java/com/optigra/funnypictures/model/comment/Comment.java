package com.optigra.funnypictures.model.comment;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.optigra.funnypictures.model.Model;

/**
 * Common comment table.
 * @author ivanursul
 *
 */
@MappedSuperclass
public class Comment extends Model {
	private static final long serialVersionUID = 1L;

	@Column(name = "comment")
	private String comment;
	
	@Column(name = "author")
	private String author;

	public String getComment() {
		return comment;
	}

	public void setComment(final String comment) {
		this.comment = comment;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(final String author) {
		this.author = author;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Comment other = (Comment) obj;
		if (author == null) {
			if (other.author != null) {
				return false;
			}
		} else if (!author.equals(other.author)) {
			return false;
		}
		if (comment == null) {
			if (other.comment != null) {
				return false;
			}
		} else if (!comment.equals(other.comment)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Comment [comment=" + comment + ", author=" + author + "]";
	}
	
}
