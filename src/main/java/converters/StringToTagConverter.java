/*
 * StringToReclamationConverter.java
 * 
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.TagRepository;
import domain.Tag;

@Component
@Transactional
public class StringToTagConverter implements Converter<String, Tag> {

	@Autowired
	TagRepository	tagRepository;


	@Override
	public Tag convert(final String text) {
		Tag result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.tagRepository.findOne(id);
		} catch (final Exception oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}