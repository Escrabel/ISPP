
package services;

import java.util.ArrayList;
import java.io.ByteArrayOutputStream;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.itextpdf.text.DocumentException;

import repositories.TattooistRepository;
import security.Authority;
import domain.Message;
import domain.MessageFolder;
import utilities.PdfUtilities;
import domain.Customer;
import domain.Tattoo;
import domain.Tattooist;

@Service
@Transactional
public class TattooistService {

	// Managed repository
	// -------------------------------------------------------------------------

	@Autowired
	private TattooistRepository tattooistRepository;

	@Autowired
	private MessageFolderService messageFolderService;
	@Autowired
	private ActorService actorService;
	@Autowired
	private MessageFolderService folderService;
	
	@Autowired
	private MessageService messageService;

	
	@Autowired
	private TattooService tattooService;
	// Suporting service
	// --------------------------------------------------------------------------

	// ------------------------------------------------------------------------

	public Tattooist create() {
		final Tattooist tattooist = new Tattooist();
		// tattooist = (Tattooist) this.folderService.addMessageFolders(tattooist);
		this.actorService.newActorDefault(tattooist, Authority.TATTOOIST);
		return tattooist;
	}

	public Collection<Tattooist> findAll() {

		final Collection<Tattooist> tattooists;
		tattooists = this.tattooistRepository.findAll();
		Assert.notNull(tattooists);

		return tattooists;
	}

	public Tattooist findOne(final int tattooistId) {

		Assert.isTrue(tattooistId > 0);
		final Tattooist tattooist;
		tattooist = this.tattooistRepository.findOne(tattooistId);
		Assert.notNull(tattooist);

		return tattooist;
	}

	public Tattooist save(Tattooist t) {
		Assert.notNull(t);

		this.actorService.setHashPassword(t);
		if (t.getId() == 0)
			t = (Tattooist) this.messageFolderService.addMessageFolders(t);
		final Tattooist tsave;
		if (t.getId() == 0)
			t = (Tattooist) this.folderService.addMessageFolders(t);

		tsave = this.tattooistRepository.save(t);
		Assert.notNull(tsave);
		Assert.isTrue(this.tattooistRepository.exists(tsave.getId()));
		return tsave;

	}
	
	public void delete(final Tattooist tattooist) {
		Assert.notNull(tattooist);
	Assert.notNull(this.tattooistRepository.findOne(tattooist.getId()));

	Collection<Message> messages = this.messageService.findAllByPrincipal();
	for (Message ms : messages) {
		Collection<MessageFolder> folders = this.folderService.getMessageFoldersByUser();

		ms.getMessageFolder().setName("papelera");

		this.messageService.delete(ms);

		for (MessageFolder b : folders) {
			b.setMessages(new ArrayList<Message>());
			this.folderService.saveDirect(b);
		}

	}

	Collection<MessageFolder> boxs = this.folderService.findAllByActorPrincipal();
	for (MessageFolder bs : boxs) {
		bs.setIsModificable(true);
		this.folderService.delete(bs);

	}

	Collection<Tattoo> tattoos = this.tattooService.listTattooByTattoo(tattooist.getId());
	for (Tattoo t : tattoos) {
		this.tattooService.delete(t);

	}

	this.tattooistRepository.delete(tattooist);

	Assert.isNull(this.tattooistRepository.findOne(tattooist.getId()));
}

	// Other business methods
	// ---------------------------------------------------------------------
	// Other business methods
	public Tattooist findByPrincipal() {
		final Tattooist brotherhood = this.actorService.findPrincipal();

		return brotherhood;
	}

	public Tattooist tattoistByTattoo(final int tattooId) {
		final Tattooist res = this.tattooistRepository.tattoistByTattoo(tattooId);
		Assert.notNull(res);
		return res;

	}

	public ByteArrayOutputStream generatePdf() throws DocumentException {
		ByteArrayOutputStream retorno = new ByteArrayOutputStream();
		final Tattooist t = this.findByPrincipal();

		Collection<Tattoo> tattoos = this.tattooService.listTattooByTattoo(t.getId());

		retorno = PdfUtilities.generatePdfTattooist(t, tattoos);

		return retorno;
	}

}
