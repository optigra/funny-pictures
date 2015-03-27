package com.optigra.funnypictures.facade.resources.content;

import java.util.UUID;

import org.springframework.stereotype.Component;

/**
 * Class for randomly resource naming.
 * 
 * @author rostyslav
 *
 */
@Component("namingStrategy")
public class RandomUuidNamingStrategy implements ContentResourceNamingStrategy {

	@Override
	public String createIdentifier(final ContentResource resource) {
		UUID uuid = UUID.randomUUID();
		return "/" + uuid.toString() + resource.getMimeType().getExtension();
	}

	@Override
	public String createIdentifier(final String prefix, final ContentResource resource) {
		UUID uuid = UUID.randomUUID();
		return "/" + prefix + "/" + uuid.toString() + resource.getMimeType().getExtension();
	}

}
