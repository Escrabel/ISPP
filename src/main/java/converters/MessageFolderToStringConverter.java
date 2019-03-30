/*
 * MessageFolderToStringConverter.java
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

import domain.MessageFolder;

@Component
@Transactional
public class MessageFolderToStringConverter implements Converter<MessageFolder, String> {

	@Override
	public String convert(final MessageFolder messageFolder) {
		String result;

		if (messageFolder == null)
			result = null;
		else
			result = String.valueOf(messageFolder.getId());

		return result;
	}

}
