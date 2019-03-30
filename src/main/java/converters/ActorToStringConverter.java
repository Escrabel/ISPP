/*
 * ActorToStringConverter.java
 * 
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Actor;

@Component
@Transactional
public class ActorToStringConverter implements Converter<Actor, String> {

	@Override
	public String convert(final Actor actor) {
		String res;

		if (actor == null)
			res = null;
		else
			res = String.valueOf(actor.getId());
		return res;

	}

}
