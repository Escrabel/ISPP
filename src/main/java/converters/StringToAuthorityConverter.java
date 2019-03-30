package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import security.Authority;
@Component
@Transactional
public class StringToAuthorityConverter implements Converter<String, Authority> {

	

	@Override
	public Authority convert(String source) {
		Authority authority;
	

		try {
			authority = new Authority();
			authority.setAuthority(source);
		} catch (Throwable t) {

			throw new IllegalArgumentException(t);

		}
		return authority;
	}

}
