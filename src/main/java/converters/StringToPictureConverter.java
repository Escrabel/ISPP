package converters;

import java.net.URLDecoder;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Picture;

@Component
@Transactional
public class StringToPictureConverter implements Converter<String, Picture> {

	@Override
	public Picture convert(String source) {
		Picture picture;
		String parts[];

		if (source == null) {
			picture = null;
		} else {

			try {
				parts = source.split("\\|");
				picture = new Picture();
				picture.setUrl(URLDecoder.decode(parts[0], "UTF-8"));

			} catch (Throwable t) {

				throw new IllegalArgumentException();

			}

		}
		return picture;

	}

}