
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
/*
 * AdministratorToStringConverter.java
 * 
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */


import domain.Administrator;

@Component
@Transactional
public class AdministratorToStringConverter implements Converter<Administrator, String> {

	@Override
	public String convert(final Administrator admin) {

		String res;

		if (admin == null)
			res = null;
		else
			res = String.valueOf(admin.getId());
		return res;

	}

}
