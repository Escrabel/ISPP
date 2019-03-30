package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import security.UserAccount;
@Component
@Transactional
public class UserAccountToStringConverter implements Converter<UserAccount, String> {

	@Override
	public String convert(UserAccount userAccount) {
		String retorno;

		if (userAccount == null)
			retorno = null;
		else
			retorno = String.valueOf(userAccount.getId());

		return retorno;
	}

}
