
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.MessageFolderRepository;
import security.LoginService;
import domain.Actor;
import domain.Message;
import domain.MessageFolder;

@Service
@Transactional
public class MessageFolderService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private MessageFolderRepository	messageFolderRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private LoginService			loginService;

	@Autowired
	private ActorService			actorService;


	// Constructors -----------------------------------------------------------
	public MessageFolderService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public MessageFolder create() {
		final MessageFolder r = new MessageFolder();
		r.setIsModificable(true);
		final List<Message> messages = new ArrayList<>();
		r.setMessages(messages);
		return r;
	}

	public Collection<MessageFolder> findAll() {
		final Collection<MessageFolder> res = this.messageFolderRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public MessageFolder findOne(final int messageFolderId) {
		return this.messageFolderRepository.findOne(messageFolderId);
	}

	public boolean existsMessageFolder(final MessageFolder fo) {
		boolean b = false;
		final String s = fo.getName();
		final Actor a = this.loginService.getPrincipalActor();
		for (final MessageFolder f : a.getMessageFolders())
			if (f.getName().equals(s) && f.getId() != fo.getId()) {
				b = true;
				break;
			}
		return b;
	}

	public MessageFolder save(final MessageFolder messageFolder) {
		Assert.notNull(messageFolder);
		Assert.isTrue(this.checkPrincipal(messageFolder));
		if (messageFolder.getId() > 0) {
			final MessageFolder original = this.findOne(messageFolder.getId());
			if (!original.getIsModificable())
				Assert.isTrue(original.getName().equals(messageFolder.getName()));
		}
		final Actor actor = this.loginService.getPrincipalActor();
		if (messageFolder.getId() == 0) {
			final List<MessageFolder> messageFolders = actor.getMessageFolders();
			messageFolders.add(messageFolder);
			actor.setMessageFolders(messageFolders);
		} else
			this.messageFolderRepository.save(messageFolder);
		return this.getMessageFolderByName(actor, messageFolder.getName());
	}

	public MessageFolder save2(final MessageFolder messageFolder) {
		Assert.notNull(messageFolder);
		if (messageFolder.getId() > 0) {
			final MessageFolder original = this.findOne(messageFolder.getId());
			if (!original.getIsModificable())
				Assert.isTrue(original.getName().equals(messageFolder.getName()));
		}
		// final Actor actor = this.loginService.getPrincipalActor();
		// MessageFolder.setActor(actor);
		// final List<MessageFolder> MessageFolders = actor.getMessageFolders();
		// MessageFolders.add(MessageFolder);
		// actor.setMessageFolders(MessageFolders);
		return this.messageFolderRepository.save(messageFolder);
	}

	public void delete(final MessageFolder messageFolder) {
		final MessageFolder mf = this.findOne(messageFolder.getId());
		Assert.isTrue(this.checkPrincipal(mf));
		Assert.isTrue(mf.getIsModificable());
		final Actor a = this.loginService.getPrincipalActor();
		final List<MessageFolder> ms = a.getMessageFolders();
		ms.remove(messageFolder);
		a.setMessageFolders(ms);
		this.messageFolderRepository.delete(messageFolder);
	}

	public void flush() {
		this.messageFolderRepository.flush();
	}

	public MessageFolder saveDirect(final MessageFolder messageFolder) {
		Assert.notNull(messageFolder);
		return this.messageFolderRepository.save(messageFolder);

	}

	public Collection<MessageFolder> findAllByActorPrincipal() {
		final Actor a = this.actorService.findPrincipal();
		return new HashSet<>(this.messageFolderRepository.findAllByActorId(a.getId()));

	}

	// Other business methods -------------------------------------------------

	public Boolean checkPrincipal(final MessageFolder MessageFolder) {
		Assert.isTrue(LoginService.getPrincipal().equals(MessageFolder.getActor().getUserAccount()));
		return true;
	}

	public Collection<MessageFolder> getMessageFoldersByUser() {
		final Actor a = this.loginService.getPrincipalActor();
		return this.messageFolderRepository.getFoldersByUser(a.getId());
	}

	// Reclamaciones
	// Bandeja de entrada
	// Enviados
	// Papelera

	public Actor addMessageFolders(final Actor actor) {
		final MessageFolder inbox = this.create();
		inbox.setName("Reclamaciones");
		inbox.setActor(actor);
		inbox.setIsModificable(false);
		final MessageFolder outbox = this.create();
		outbox.setName("Bandeja de entrada");
		outbox.setActor(actor);
		outbox.setIsModificable(false);
		final MessageFolder trashbox = this.create();
		trashbox.setName("Enviados");
		trashbox.setActor(actor);
		trashbox.setIsModificable(false);
		final MessageFolder spambox = this.create();
		spambox.setName("Papelera");
		spambox.setActor(actor);
		spambox.setIsModificable(false);
		final List<MessageFolder> MessageFolders = new ArrayList<MessageFolder>();
		MessageFolders.add(inbox);
		MessageFolders.add(outbox);
		MessageFolders.add(trashbox);
		MessageFolders.add(spambox);
		actor.setMessageFolders(MessageFolders);
		return actor;
	}

	public MessageFolder changeMessageFolderName(final MessageFolder MessageFolder, final String s) {
		Assert.isTrue(MessageFolder.getIsModificable() == (true));
		MessageFolder.setName(s);
		return this.save(MessageFolder);
	}

	public MessageFolder getMessageFolderByName(final Actor a, final String s) {
		return this.messageFolderRepository.getFolderbyName(a, s);
	}

	public MessageFolder getReclamaciones(final Actor a) {
		return this.getMessageFolderByName(a, "Reclamaciones");
	}

	public MessageFolder getCarpetaEntrada(final Actor a) {
		return this.getMessageFolderByName(a, "Bandeja de entrada");
	}

	public MessageFolder getPapelera(final Actor a) {
		return this.getMessageFolderByName(a, "Papelera");
	}

	public MessageFolder getEnviados(final Actor a) {
		return this.getMessageFolderByName(a, "Enviados");
	}

	public boolean isReservedName(final MessageFolder f) {
		boolean res = false;
		final String s = f.getName().toLowerCase();
		if (s.equals("reclamaciones") || s.equals("carpeta de entrada") || s.equals("papelera") || s.equals("enviados") || s.equals("reclamations") || s.equals("outbox") || s.equals("inbox") || s.equals("trash"))
			res = true;
		return res;
	}

}
