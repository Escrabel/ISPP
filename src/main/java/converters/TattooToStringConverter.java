
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Tattoo;

@Component
@Transactional
public class TattooToStringConverter implements Converter<Tattoo, String> {

	@Override
	public String convert(Tattoo tattoo) {
		String res;

		if (tattoo == null) {
			res = null;
		} else {
			res = String.valueOf(tattoo.getId());
		}
		return res;
		
}

}
