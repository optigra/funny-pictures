package com.optigra.funnypictures.facade.converter;

import java.util.List;

/**
 * Interface for all converters.
 * 
 * @author rostyslav
 *
 * @param <Source>
 *            Source to convert.
 * @param <Target>
 *            Result of converting.
 */
public interface Converter<Source, Target> {
	/**
	 * Method convert source to target with initialized target fields.
	 * 
	 * @param source
	 *            Source to convert.
	 * @param target
	 *            Result of converting.
	 * @return Converted source.
	 */
	Target convert(Source source, Target target);

	/**
	 * Method convert source to target.
	 * 
	 * @param source
	 *            Source to convert.
	 * @return Converted source.
	 */
	Target convert(Source source);

	/**
	 * Method convert list of sources to list targets.
	 * 
	 * @param sources
	 *            list of sources.
	 * @return list of converted sources.
	 */
	List<Target> convertAll(List<Source> sources);

	/**
	 * Method convert list of sources to list of targets with initialized
	 * targets fields.
	 * 
	 * @param sources
	 *            list of sources.
	 * @param targets
	 *            list with results of converting.
	 * @return list of converted sources.
	 */
	List<Target> convertAll(List<Source> sources, List<Target> targets);

}
