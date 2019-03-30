package converters;

import java.net.URLEncoder;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Picture;
@Component
@Transactional
public class PictureToStringConverter implements Converter<Picture, String> {

	@Override
	public String convert(Picture source) {
		String retorno;
		StringBuilder builder;

		if (source == null)
			retorno = null;
		else {
			try {
				builder = new StringBuilder();
				builder.append(URLEncoder.encode(source.getUrl(), "UTF-8"));
				retorno = builder.toString();

			} catch (Throwable t) {

				throw new IllegalArgumentException();

			}
		}

		return retorno;
	}

}