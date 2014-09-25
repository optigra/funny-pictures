package com.optigra.funnypictures.facade.resources.content;

import java.util.UUID;

import org.springframework.stereotype.Component;


@Component("namingStrategy")
public class RandomUuidNamingStrategy implements ContentResourceNamingStrategy {

	@Override
	public String createIdentifier(ContentResource resource) {
		UUID uuid = UUID.randomUUID();
		return "/" + uuid.toString() + resource.getMimeType().getExtension();
	}

}
