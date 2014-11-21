package com.optigra.funnypictures.facade.converter.base;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.optigra.funnypictures.facade.converter.AbstractConverter;
import com.optigra.funnypictures.facade.resources.ApiResource;
import com.optigra.funnypictures.model.Model;

/**
 * Class, that is used to make some convertation
 * when insert method (POST) is called.
 * @author ivanursul
 *
 * @param <S> resource type.
 * @param <T> class type.
 */
@Component("insertConverter")
public class InsertConverter<S extends ApiResource, T extends Model> extends AbstractConverter<S, T> {

	@Override
	public T convert(final S source, final T target) {
		
		target.setCreateDate(new Date());
		target.setUpdateDate(new Date());

		return target;
	}

	@Override
	public T convert(final S source) {
		throw new UnsupportedOperationException("Method not allowed");
	}

}
