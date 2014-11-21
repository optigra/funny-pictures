package com.optigra.funnypictures.facade.converter.base;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.optigra.funnypictures.facade.converter.AbstractConverter;
import com.optigra.funnypictures.facade.resources.ApiResource;
import com.optigra.funnypictures.model.Model;

/**
 * Converter, that is used, when Update(PUT) method is called.
 * @author ivanursul
 *
 * @param <S> resource.
 * @param <T> entity.
 */
@Component("updateConverter")
public class UpdateConverter<S extends ApiResource, T extends Model> extends AbstractConverter<S, T> {

	@Override
	public T convert(final S source, final T target) {
		target.setUpdateDate(new Date());
		return target;
	}

	@Override
	public T convert(final S source) {
		throw new UnsupportedOperationException("Method not allowed");
	}

}
