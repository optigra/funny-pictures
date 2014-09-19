package com.optigra.funnypictures.facade.resources.content;

import java.util.UUID;

public class RandomUuidNamingStrategy implements ContentResourceNamingStrategy {

	@Override
	public UUID createIdentifier(ContentResource resource) {
		UUID uuid = UUID.randomUUID();
		return uuid;
	}

}
