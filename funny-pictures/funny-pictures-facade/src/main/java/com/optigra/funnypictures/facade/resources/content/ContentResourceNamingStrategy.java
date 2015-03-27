package com.optigra.funnypictures.facade.resources.content;

/**
 * An interface for resource naming strategy.
 * 
 * @author rostyslav
 *
 */
public interface ContentResourceNamingStrategy {
	
	/**
	 * Returns a new, unique identifier for the resource.
	 * @param resource the resource to assign a name
	 * @return the resource identifier
	 */
	String createIdentifier(ContentResource resource);
	
	/**
	 * Returns a new, unique identifier for the resource. The identifier will start with the supplied prefix.
	 * @param prefix identifier prefix
	 * @param resource the resource to assign a name
	 * @return the resource identifier
	 */
	String createIdentifier(String prefix, ContentResource resource);

}
