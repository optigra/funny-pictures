package com.optigra.funnypictures.facade.resources.comment;

import java.text.MessageFormat;

/**
 * Funny picture comment resource.
 * @author ivanursul
 *
 */
public class FunnyPictureCommentResource extends CommentResource {

	private Long funnyPictureId;
	
	@Override
	public String getUri() {
		return MessageFormat.format("/comments/funnies/{0}", getId());
	}

	public Long getFunnyPictureId() {
		return funnyPictureId;
	}

	public void setFunnyPictureId(final Long funnyPictureId) {
		this.funnyPictureId = funnyPictureId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((funnyPictureId == null) ? 0 : funnyPictureId.hashCode());
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
		FunnyPictureCommentResource other = (FunnyPictureCommentResource) obj;
		if (funnyPictureId == null) {
			if (other.funnyPictureId != null) {
				return false;
			}
		} else if (!funnyPictureId.equals(other.funnyPictureId)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "FunnyPictureCommentResource [funnyPictureId=" + funnyPictureId
				+ "]";
	}

}
