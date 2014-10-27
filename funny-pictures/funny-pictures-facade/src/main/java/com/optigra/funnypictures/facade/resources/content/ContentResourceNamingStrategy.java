package com.optigra.funnypictures.facade.resources.content;

/**
 * ContentResourceNamingStrategy interface for resource naming strategy.
 * 
 * @author rostyslav
 *
 */
public interface ContentResourceNamingStrategy {
	/**
	 * 
	 * @param resource
	 *            ContentResource
	 * @return Identifier for ContentResource
	 */
	String createIdentifier(ContentResource resource);

}
