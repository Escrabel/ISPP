package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import security.UserAccount;
import security.UserAccountRepository;

@Component
@Transactional
public class StringToUserAccountConverter implements Converter<String,  UserAccount> {

	@Autowired
	UserAccountRepository userAccountRepository;

	@Override
	public UserAccount convert(String source) {
		UserAccount userAccount;
		int id;

		try {
			id = Integer.valueOf(source);
			userAccount = this.userAccountRepository.findOne(id);
		} catch (Throwable t) {

			throw new IllegalArgumentException(t);

		}
		return userAccount;
	}
}