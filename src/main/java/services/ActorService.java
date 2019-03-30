
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.MessageFolder;

@Service
@Transactional
public class ActorService {

	// Managed repository
	@Autowired
	private ActorRepository	actorRepository;


	// -------------------------------------------------------------------------

	// Supporting services

	public Collection<Actor> findAll() {
		final Collection<Actor> res = this.actorRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Actor findOne(final int actorId) {
		return this.actorRepository.findOne(actorId);
	}

	public void flush() {
		this.actorRepository.flush();
	}

	public Actor save(final Actor a) {
		Assert.notNull(a);
		return this.actorRepository.save(a);
	}

	// -------------------------------------------------------------------------

	// Other business methods
	public <T> T findPrincipal() {

		final UserAccount userAccount = LoginService.getPrincipal();
		final int userId = userAccount.getId();
		final T principal = this.findByUserId(userId);

		return principal;
	}

	public boolean checkAuthority(final Actor actor, final String stringAuth) {
		final UserAccount userAccount = actor.getUserAccount();

		final Collection<Authority> auths = userAccount.getAuthorities();
		final Authority auth = new Authority();

		auth.setAuthority(stringAuth);

		return auths.contains(auth);
	}

	public void newActorDefault(final Actor actor, final String authority) {

		final Authority auth = new Authority();
		final UserAccount userAccount = new UserAccount();

		if (actor.getUserAccount() != null) {
			userAccount.setUsername(actor.getUserAccount().getUsername());
			userAccount.setPassword(actor.getUserAccount().getPassword());
		}
		actor.setMessageFolders(new ArrayList<MessageFolder>());
		auth.setAuthority(authority);
		userAccount.addAuthority(auth);
		userAccount.setBan(false);
		actor.setUserAccount(userAccount);

	}

	public void setHashPassword(final Actor actor) {

		if (actor.getId() == 0) {
			final UserAccount userAccount = actor.getUserAccount();

			final String passwordMd5 = new Md5PasswordEncoder().encodePassword(userAccount.getPassword(), null);

			actor.getUserAccount().setPassword(passwordMd5);
		}

	}
	// -------------------------------------------------------------------------
	// Auxiliary methods
	private <T> T findByUserId(final int userId) {

		final T actor = this.actorRepository.findByUserId(userId);

		return actor;
	}

	public Actor findByUserAccount(final UserAccount u) {
		return this.actorRepository.findByUsername(u.getUsername());
	}

	// -------------------------------------------------------------------------

}
