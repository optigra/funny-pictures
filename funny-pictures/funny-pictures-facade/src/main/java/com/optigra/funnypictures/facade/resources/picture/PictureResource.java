package com.optigra.funnypictures.facade.resources.picture;

import java.text.MessageFormat;

import com.optigra.funnypictures.facade.resources.ApiResource;

public class PictureResource extends ApiResource {

	private Long id;
	private String name;
	private String url;
	
	@Override
	public String getUri() {
		return MessageFormat.format("/pictures/{0}", id);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "PictureResource [id=" + id + ", name=" + name + ", url=" + url
				+ "]";
	}

}
