package com.optigra.funnypictures.facade.resources.search;

/**
 * Request model for paged requests. Contains offset and limit fields for
 * pagination.
 * 
 * @author ivanursul
 *
 * @param <R> resource.
 */
public class PagedRequest<R> {

	private Integer offset;
	private Integer limit;
	private R resource;

	/**
	 * 
	 * Constructor with params.
	 * 
	 * @param offset
	 *            Number of first element in request.
	 * @param limit
	 *            count of objects in one request.
	 */
	public PagedRequest(final Integer offset, final Integer limit) {
		super();
		this.offset = offset;
		this.limit = limit;
	}

	/**
	 * Constructor for all fields.
	 * @param offset
	 * @param limit
	 * @param resource
	 */
	public PagedRequest(final R resource, final Integer offset, final Integer limit) {
		super();
		this.offset = offset;
		this.limit = limit;
		this.resource = resource;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(final Integer offset) {
		this.offset = offset;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(final Integer limit) {
		this.limit = limit;
	}

	public R getResource() {
		return resource;
	}

	public void setResource(final R resource) {
		this.resource = resource;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((limit == null) ? 0 : limit.hashCode());
		result = prime * result + ((offset == null) ? 0 : offset.hashCode());
		result = prime * result
				+ ((resource == null) ? 0 : resource.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		PagedRequest<?> other = (PagedRequest<?>) obj;
		if (limit == null) {
			if (other.limit != null) {
				return false;
			}
		} else if (!limit.equals(other.limit)) {
			return false;
		}
		if (offset == null) {
			if (other.offset != null) {
				return false;
			}
		} else if (!offset.equals(other.offset)) {
			return false;
		}
		if (resource == null) {
			if (other.resource != null) {
				return false;
			}
		} else if (!resource.equals(other.resource)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "PagedRequest [offset=" + offset + ", limit=" + limit
				+ ", resource=" + resource + "]";
	}

}
