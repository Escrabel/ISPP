
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.Authority;
import domain.Administrator;

@Service
@Transactional
public class AdministratorService {

	// Managed repository
	// -------------------------------------------------------------------------

	@Autowired
	private AdministratorRepository	administratorRepository;

	// Suporting service
	// --------------------------------------------------------------------------

	//	@Autowired
	//	private MessageFolderService messageFolderService;

	@Autowired
	private ActorService			actorService;
	@Autowired
	private MessageFolderService	folderService;


	// Simple crud methods
	// ------------------------------------------------------------------------

	public Administrator create() {
		final Administrator administrator = new Administrator();
		//		administrator = (Administrator) this.folderService.addMessageFolders(administrator);
		this.actorService.newActorDefault(administrator, Authority.ADMIN);
		return administrator;
	}

	public Collection<Administrator> findAll() {

		final Collection<Administrator> administrators = this.administratorRepository.findAll();

		Assert.notNull(administrators);

		return administrators;
	}

	public Administrator findOne(final int administratorId) {

		Assert.notNull(administratorId);

		final Administrator administrator = this.administratorRepository.findOne(administratorId);

		Assert.notNull(administrator);
		return administrator;
	}

	public Administrator save(Administrator administrator) {
		Assert.notNull(administrator);
		this.actorService.setHashPassword(administrator);
		if (administrator.getId() == 0)
			administrator = (Administrator) this.folderService.addMessageFolders(administrator);
		final Administrator asave = this.administratorRepository.save(administrator);
		Assert.notNull(asave);
		Assert.isTrue(this.administratorRepository.exists(asave.getId()));
		return asave;
	}

	// Other business methods
	// ---------------------------------------------------------------------

	public Administrator findByPrincipal() {

		final Administrator administrator = this.actorService.findPrincipal();

		return administrator;
	}

}
