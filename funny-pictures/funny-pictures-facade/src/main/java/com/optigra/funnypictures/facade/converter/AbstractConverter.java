package com.optigra.funnypictures.facade.converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract convert class.
 * 
 * @author rostyslav
 *
 * @param <SOURCE>
 *            Source to convert.
 * @param <TARGET>
 *            Result of converting.
 */
public abstract class AbstractConverter<SOURCE, TARGET> implements Converter<SOURCE, TARGET> {
	
	private static final Logger LOG = LoggerFactory.getLogger(AbstractConverter.class);

	@Override
	public List<TARGET> convertAll(final List<SOURCE> sources) {

		// TODO eliminate all cases when null is passed to this method
		if (sources == null) {
			LOG.warn("convertAll(null) called. This should never happen.");
			return Collections.<TARGET>emptyList();
		}

		return convertAll(sources, new ArrayList<TARGET>(sources.size()));
	}

	@Override
	public List<TARGET> convertAll(final List<SOURCE> sources, final List<TARGET> targets) {

		for (SOURCE source : sources) {
			targets.add(convert(source));
		}

		return targets;
	}
}
