
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Tattooist;

@Component
@Transactional
public class TattooistToStringConverter implements Converter<Tattooist, String> {

	@Override
	public String convert(Tattooist tattooist) {
		String res;

		if (tattooist == null) {
			res = null;
		} else {
			res = String.valueOf(tattooist.getId());
		}
		return res;

	}

}
