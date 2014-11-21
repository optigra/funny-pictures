package com.optigra.funnypictures.facade.resources;

/**
 * Abstract class for all api resources.
 * 
 * @author rostyslav
 *
 */
public abstract class ApiResource {
	
	private Long id;
	
	/**
	 * 
	 * @return Uri to resource
	 */
	public abstract String getUri();

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

}
