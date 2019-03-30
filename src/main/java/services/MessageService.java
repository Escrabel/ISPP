
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import security.LoginService;
import domain.Actor;
import domain.Message;
import domain.MessageFolder;

@Service
@Transactional
public class MessageService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private MessageRepository		messageRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private LoginService			loginService;

	@Autowired
	private MessageFolderService	folderService;

	@Autowired
	private ActorService			actorService;


	// @Autowired
	// private ConfigurationService configurationService;
	//
	// @Autowired
	// private ActorService actorService;
	//
	// @Autowired
	// private AdministratorService adminService;

	// Constructors -----------------------------------------------------------
	public MessageService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Message create() {
		final Message r = new Message();
		r.setDate(new Date(System.currentTimeMillis() - 1000));
		r.setActorSender(this.loginService.getPrincipalActor());
		//		r.setPriority("NEUTRAL");
		return r;
	}

	public Collection<Message> findAll() {
		final Collection<Message> res = this.messageRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Message findOne(final int messageId) {
		return this.messageRepository.findOne(messageId);
	}

	public Message save(final Message message) {
		message.setActorSender(this.loginService.getPrincipalActor());
		this.checkPrincipalIsSender(message);
		Assert.notNull(message);
		message.setDate(new Date(System.currentTimeMillis() - 1000));
		return this.messageRepository.save(message);
	}

	public Message saveBySystem(final Message message) {
		Assert.notNull(message);
		message.setDate(new Date(System.currentTimeMillis() - 1000));
		return this.messageRepository.save(message);
	}

	public void delete(final Message message) {
		this.checkPrincipal(message);
		if (message.getMessageFolder().getName().toLowerCase().equals("papelera"))
			this.messageRepository.delete(message);
		else {
			final MessageFolder trashbox = this.folderService.getPapelera(this.loginService.getPrincipalActor());
			message.setMessageFolder(trashbox);
			final MessageFolder actual = message.getMessageFolder();
			actual.getMessages().remove(message);
			trashbox.getMessages().add(message);
			this.folderService.save(actual);
			this.folderService.save(trashbox);
			this.messageRepository.save(message);
		}
	}

	public Collection<Message> findAllByPrincipal() {
		final Actor principal = this.actorService.findPrincipal();
		final int principalId = principal.getId();

		return this.messageRepository.findAllByActorId(principalId);
	}

	public void flush() {
		this.messageRepository.flush();
	}

	// Other business methods -------------------------------------------------

	public Message moveTo(final Message message, final MessageFolder folder) {

		final List<Message> mns = folder.getMessages();
		mns.add(message);
		folder.setMessages(mns);
		this.folderService.save(folder);

		final MessageFolder actual = message.getMessageFolder();
		actual.getMessages().remove(message);
		this.folderService.save(actual);

		message.setMessageFolder(folder);
		return this.save(message);

	}

	public Message sendMessage(final Message message) {
		final Actor sender = this.loginService.getPrincipalActor();
		final Actor recipient = message.getActorReceiver();

		// Mensaje enviado
		message.setDate(new Date(System.currentTimeMillis() - 1000));
		message.setActorSender(sender);
		message.setMessageFolder(this.folderService.getEnviados(sender));

		// Mensaje recibido
		final Message message2 = this.create();
		message2.setDate(new Date(System.currentTimeMillis() - 1000));
		message2.setActorReceiver(recipient);
		message2.setActorSender(message.getActorSender());
		//		message2.setPriority(message.getPriority());
		message2.setBody(message.getBody());
		message2.setSubject(message.getSubject());
		message2.setMessageFolder(this.folderService.getCarpetaEntrada(recipient));

		final Message res = this.messageRepository.save(message);

		// if (this.detectSpam(message))
		// message2.setMessageFolder(this.folderService.getSpambox(message2.getRecipient()));
		this.messageRepository.save(message2);
		return res;
	}

	public Message sendMessageClaim(final Message message) {
		final Actor sender = this.loginService.getPrincipalActor();
		final Actor recipient = message.getActorReceiver();

		// Mensaje enviado
		message.setDate(new Date(System.currentTimeMillis() - 1000));
		message.setActorSender(sender);
		message.setMessageFolder(this.folderService.getEnviados(sender));

		// Mensaje recibido
		final Message message2 = this.create();
		message2.setDate(new Date(System.currentTimeMillis() - 1000));
		message2.setActorReceiver(recipient);
		message2.setActorSender(message.getActorSender());
		//		message2.setPriority(message.getPriority());
		message2.setBody(message.getBody());
		message2.setSubject(message.getSubject());
		message2.setMessageFolder(this.folderService.getReclamaciones(recipient));

		final Message res = this.messageRepository.save(message);

		// if (this.detectSpam(message))
		// message2.setMessageFolder(this.folderService.getSpambox(message2.getRecipient()));
		this.messageRepository.save(message2);
		return res;
	}

	// public Boolean detectSpam(final Message m) {
	// Boolean res = false;
	// for (final String s : this.configurationService.find().getSpamWords())
	// if (m.getSubject().contains(s) || m.getBody().contains(s)) {
	// res = true;
	// final Actor sender = m.getActorSender();
	// sender.setSuspicious(res);
	// this.actorService.save(sender);
	// break;
	// }
	// return res;
	// }

	public void checkPrincipal(final Message obj) {
		boolean res = false;
		if (LoginService.getPrincipal().equals(obj.getMessageFolder().getActor().getUserAccount()))
			res = true;
		Assert.isTrue(res);
	}

	public void checkPrincipalIsSender(final Message obj) {
		boolean res = false;
		if (LoginService.getPrincipal().equals(obj.getActorSender().getUserAccount()))
			res = true;
		Assert.isTrue(res);
	}

	public List<String> getPriorities() {
		final List<String> l = new ArrayList<String>();
		l.add("LOW");
		l.add("NEUTRAL");
		l.add("HIGH");
		return l;
	}

	public void send(final Message m1) {
		Assert.notNull(m1);
		final Message m2 = new Message();
		m2.setBody(m1.getBody());
		// if (this.isSpam(m1))
		// m2.setMessageFolder(this.folderService.getSpambox(m1.getRecipient()));
		// else
		m2.setMessageFolder(this.folderService.getCarpetaEntrada(m1.getActorReceiver()));
		m2.setId(0);
		m2.setVersion(0);
		m2.setActorReceiver(m1.getActorReceiver());
		m2.setActorSender(m1.getActorSender());
		m2.setSubject(m1.getSubject());
		//		m2.setPriority(m1.getPriority());
		m2.setDate(m1.getDate());

		final MessageFolder f1 = m1.getMessageFolder();
		final MessageFolder f2 = m2.getMessageFolder();

		final List<Message> ms1 = f1.getMessages();
		final List<Message> ms2 = f2.getMessages();

		ms1.add(m1);
		ms2.add(m2);

		f1.setMessages(ms1);
		f2.setMessages(ms2);

		this.save(m2);
		this.save(m1);
		// folderService.save(f1);
		// folderService.save(f2);
	}

	// public void sendNotificationBox(final Actor a) {
	// final MessageFolder notification = this.folderService.getNotificationmbox(a);
	// final Message msn = this.create();
	//
	// msn.setActorReceiver(a);
	// msn.setActorSender(this.adminService.findAll().iterator().next());
	// msn.setSubject("Querido usuario...." + "\n" + "Dear user....");
	// msn.setBody("Cambio de estado en alguna de tus aplicaciones." + "\n" +
	// "Change in one of your applications.");
	// msn.setDate(new Date(System.currentTimeMillis() - 1000));
	// msn.setMessageFolder(notification);
	// final Message msn2 = this.saveBySystem(msn);
	//
	// final List<Message> msns = notification.getMessages();
	// msns.add(msn2);
	// notification.setMessages(msns);
	//
	// this.folderService.save2(notification);
	//
	// }

	// private boolean isSpam(final Message m1) {
	// boolean res = false;
	// for (final String sw : this.configurationService.find().getSpamWords())
	// if (m1.getBody().toLowerCase().contains(sw.toLowerCase()) ||
	// m1.getSubject().toLowerCase().contains(sw.toLowerCase())) {
	// res = true;
	// break;
	// }
	// return res;
	// }

	// public boolean sendBroadcast(final Message Message) {
	// boolean res = false;
	// for (final Actor a : this.actorService.findAll()) {
	// //Mensaje recibido
	// final Message message2 = this.create();
	// message2.setDate(new Date(System.currentTimeMillis() - 1000));
	// message2.setActorReceiver(a);
	// message2.setActorSender(Message.getActorSender());
	// message2.setPriority(Message.getPriority());
	// message2.setBody(Message.getBody());
	// message2.setSubject(Message.getSubject());
	// message2.setMessageFolder(this.folderService.getNotificationmbox(a));
	// this.messageRepository.save(message2);
	// }
	// res = true;
	// return res;
	// }

}
