
package controllers.actor;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ActorService;
import services.MessageFolderService;
import services.MessageService;
import services.TattooistService;
import controllers.AbstractController;
import domain.Actor;
import domain.Message;
import domain.MessageFolder;
import domain.Tattooist;

@Controller
@RequestMapping("/message/actor")
public class ActorMessageController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private MessageService			messageService;

	@Autowired
	private MessageFolderService	folderService;

	@Autowired
	private LoginService			loginService;

	@Autowired
	private TattooistService		tattooistService;

	@Autowired
	private ActorService			actorService;


	//	@Autowired
	//	private BuyService				buyService;

	// Constructors -----------------------------------------------------------

	public ActorMessageController() {
		super();
	}

	//ListRest

	@RequestMapping(value = "/listRest", method = RequestMethod.GET)
	public @ResponseBody
	Collection<Message> listRest(@RequestParam final int messageFolderId) {
		Collection<Message> result;
		final MessageFolder folder = this.folderService.findOne(messageFolderId);
		Assert.isTrue(this.loginService.getPrincipalActor().getId() == folder.getActor().getId());
		result = folder.getMessages();
		return result;
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int messageFolderId) {
		ModelAndView result;
		Collection<Message> ms;
		final MessageFolder folder = this.folderService.findOne(messageFolderId);
		Assert.isTrue(this.loginService.getPrincipalActor().getId() == folder.getActor().getId());
		ms = folder.getMessages();
		result = new ModelAndView("actor/message/list");
		result.addObject("mensajes", ms);
		result.addObject("requestURI", "message/actor/list.do");
		final Actor a = this.loginService.getPrincipalActor();
		result.addObject("actor", a);
		result.addObject("box", folder.getName());
		//le paso el id del messageFolder para llamar a los mensajes de ese messageFolder por angular
		result.addObject("messageFolderId", messageFolderId);
		return result;
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create-t", method = RequestMethod.GET)
	public ModelAndView createByTattooist(@RequestParam final int tattooistId) {
		ModelAndView result;
		Message message;
		final Actor logueado = this.loginService.getPrincipalActor();
		Assert.isTrue(tattooistId > 0);
		final Tattooist t = this.tattooistService.findOne(tattooistId);
		Assert.notNull(t);
		message = this.messageService.create();
		message.setActorSender(logueado);
		message.setBuyTicker("");
		message.setActorReceiver(t);
		message.setMessageFolder(this.folderService.getEnviados(logueado));
		result = this.createEditModelAndView(message);
		result.addObject("tipo", "t");

		return result;
	}

	@RequestMapping(value = "/create-c", method = RequestMethod.GET)
	public ModelAndView createByClaim(@RequestParam final int claimId, @RequestParam final int actorId) {
		ModelAndView result;
		Message message;
		final Actor logueado = this.loginService.getPrincipalActor();
		Assert.isTrue(actorId > 0);
		Assert.isTrue(LoginService.isPrincipalAdmin());
		final Actor t = this.actorService.findOne(actorId);
		Assert.notNull(t);
		//	final Reclamation claim = this.reclamationService.findOne(claimId);
		//	Assert.notNull(claim);
		//	Assert.isTrue(claim.getBuy().getCustomer().getId() == actorId || claim.getBuy().getTattoo().getTattooist().getId() == actorId);
		message = this.messageService.create();
		message.setActorSender(logueado);
		//	message.setBuyTicker(claim.getId() + "");
		message.setActorReceiver(t);
		message.setMessageFolder(this.folderService.getEnviados(logueado));
		result = this.createEditModelAndView(message);
		result.addObject("tipo", "c");

		return result;
	}
	@RequestMapping(value = "/create-b", method = RequestMethod.GET)
	public ModelAndView createByBuy(@RequestParam final int buyId) {
		ModelAndView result;
		Message message;
		final Actor logueado = this.loginService.getPrincipalActor();
		Assert.isTrue(buyId > 0);
		//		final Buy t = this.buyService.findOne(buyId);
		//	Assert.notNull(t);
		//	Assert.isTrue(t.getTattoo().getTattooist().getId() == logueado.getId() || t.getCustomer().getId() == logueado.getId());
		message = this.messageService.create();
		message.setActorSender(logueado);
		message.setSubject("");
		//	message.setBuyTicker(t.getId() + "");
		//	if (LoginService.isPrincipalTattooist())
		//		message.setActorReceiver(t.getCustomer());
		//	else
		//		message.setActorReceiver(t.getTattoo().getTattooist());
		//	message.setActorReceiver(t.getTattoo().getTattooist());
		message.setMessageFolder(this.folderService.getEnviados(logueado));
		result = this.createEditModelAndView(message);
		result.addObject("tipo", "b");

		return result;
	}

	@RequestMapping(value = "/create-r", method = RequestMethod.GET)
	public ModelAndView createByReply(@RequestParam final int messageId) {
		ModelAndView result;
		Message message;
		final Actor logueado = this.loginService.getPrincipalActor();
		Assert.isTrue(messageId > 0);
		final Message t = this.messageService.findOne(messageId);
		Assert.notNull(t);
		Assert.isTrue(t.getActorReceiver().getId() == this.loginService.getPrincipalActor().getId() || t.getActorSender().getId() == this.loginService.getPrincipalActor().getId());
		message = this.messageService.create();
		message.setBuyTicker("");
		message.setActorSender(logueado);
		message.setActorReceiver(t.getActorSender());
		message.setBody("\r\n\r\n_________________________________________\r\nSubject: " + t.getSubject() + "\r\nDate:" + t.getDate() + "\r\nTo:" + t.getActorSender().getUserAccount().getUsername() + "\r\nFrom:"
			+ t.getActorReceiver().getUserAccount().getUsername() + "\r\n\r\n" + t.getBody());
		if (t.getSubject().contains("RE:"))
			message.setSubject(t.getSubject());
		else
			message.setSubject("RE: " + t.getSubject());
		message.setMessageFolder(this.folderService.getEnviados(logueado));
		result = this.createEditModelAndView(message);
		result.addObject("tipo", "r");

		return result;
	}
	@RequestMapping(value = "/edit-t", method = RequestMethod.POST, params = "send-t")
	public ModelAndView sendByTattooist(@Valid final Message message, final BindingResult binding) {
		ModelAndView result;
		Assert.isTrue(message.getId() == 0);
		LoginService.isPrincipalCustomer();
		final Tattooist t = this.tattooistService.findOne(message.getActorReceiver().getId());
		Assert.notNull(t);
		Assert.isTrue(this.loginService.getPrincipalActor().getId() == message.getMessageFolder().getActor().getId());
		message.setBuyTicker("");
		if (binding.hasErrors()) {
			result = new ModelAndView("actor/message/edit");
			message.setBuyTicker("");
			result.addObject("message", message);
			result.addObject("vistaMensaje", true);
			result.addObject("priorities", this.messageService.getPriorities());
			result.addObject("tipo", "t");
		} else
			try {
				this.messageService.sendMessage(message);
				result = new ModelAndView("redirect:../../message/actor/list.do?messageFolderId=" + message.getMessageFolder().getId());
			} catch (final Throwable oops) {
				result = new ModelAndView("actor/message/edit");
				result.addObject("message", message);
				result.addObject("vistaMensaje", true);
				result.addObject("tipo", "t");
				result.addObject("priorities", this.messageService.getPriorities());
				result.addObject("messageError", "message.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit-r", method = RequestMethod.POST, params = "send-r")
	public ModelAndView sendByReply(@Valid final Message message, final BindingResult binding) {
		ModelAndView result;
		message.setBuyTicker("");
		Assert.isTrue(this.loginService.getPrincipalActor().getId() == message.getMessageFolder().getActor().getId());
		if (binding.hasErrors()) {
			result = new ModelAndView("actor/message/edit");
			result.addObject("message", message);
			result.addObject("vistaMensaje", true);
			result.addObject("priorities", this.messageService.getPriorities());
			result.addObject("tipo", "r");
		} else
			try {
				this.messageService.sendMessage(message);
				result = new ModelAndView("redirect:../../message/actor/list.do?messageFolderId=" + message.getMessageFolder().getId());
			} catch (final Throwable oops) {
				result = new ModelAndView("actor/message/edit");
				result.addObject("message", message);
				result.addObject("vistaMensaje", true);
				result.addObject("tipo", "r");
				result.addObject("priorities", this.messageService.getPriorities());
				result.addObject("messageError", "message.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit-c", method = RequestMethod.POST, params = "send-c")
	public ModelAndView sendByClaim(@Valid Message message, final BindingResult binding) {
		ModelAndView result;
		Assert.isTrue(LoginService.isPrincipalAdmin());
		Assert.notNull(message.getBuyTicker());
		Assert.isTrue(new Integer(message.getBuyTicker()) > 0);
		//	final Reclamation claim = this.reclamationService.findOne(new Integer(message.getBuyTicker()));
		//	Assert.notNull(claim);
		//	Assert.isTrue(claim.getBuy().getCustomer().getId() == message.getActorReceiver().getId() || claim.getBuy().getTattoo().getTattooist().getId() == message.getActorReceiver().getId());
		message = this.messageService.create();
		Assert.isTrue(this.loginService.getPrincipalActor().getId() == message.getMessageFolder().getActor().getId());
		if (binding.hasErrors()) {
			result = new ModelAndView("actor/message/edit");
			result.addObject("message", message);
			result.addObject("vistaMensaje", true);
			result.addObject("priorities", this.messageService.getPriorities());
			result.addObject("tipo", "c");
		} else
			try {
				this.messageService.sendMessageClaim(message);
				result = new ModelAndView("redirect:../../message/actor/list.do?messageFolderId=" + message.getMessageFolder().getId());
			} catch (final Throwable oops) {
				result = new ModelAndView("actor/message/edit");
				result.addObject("message", message);
				result.addObject("vistaMensaje", true);
				result.addObject("tipo", "c");
				result.addObject("priorities", this.messageService.getPriorities());
				result.addObject("messageError", "message.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit-b", method = RequestMethod.POST, params = "send-b")
	public ModelAndView sendByBuy(@Valid final Message message, final BindingResult binding) {
		ModelAndView result;
		//		final Buy b = this.buyService.findOne(new Integer(message.getBuyTicker()));
		Assert.isTrue(this.loginService.getPrincipalActor().getId() == message.getMessageFolder().getActor().getId());
		if (binding.hasErrors()) {
			result = new ModelAndView("actor/message/edit");
			result.addObject("message", message);
			result.addObject("vistaMensaje", true);
			result.addObject("priorities", this.messageService.getPriorities());
			result.addObject("tipo", "r");
		} else
			try {
				//			message.setSubject(message.getSubject() + " REF:" + b.getTicker());
				this.messageService.sendMessage(message);
				result = new ModelAndView("redirect:../../message/actor/list.do?messageFolderId=" + message.getMessageFolder().getId());
			} catch (final Throwable oops) {
				result = new ModelAndView("actor/message/edit");
				//			message.setSubject(message.getSubject().replace(" REF:" + b.getTicker(), ""));
				result.addObject("message", message);
				result.addObject("vistaMensaje", true);
				result.addObject("tipo", "r");
				result.addObject("priorities", this.messageService.getPriorities());
				result.addObject("messageError", "message.commit.error");
			}

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public ModelAndView details(@RequestParam final int messageId) {
		ModelAndView result;
		final Actor log = this.loginService.getPrincipalActor();
		final Message message = this.messageService.findOne(messageId);
		Assert.notNull(message);
		Assert.isTrue(log.getId() == message.getMessageFolder().getActor().getId());
		result = new ModelAndView("actor/message/details");
		result.addObject("vistaMensaje", true);
		result.addObject("message", message);

		return result;
	}

	@RequestMapping(value = "/move", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int messageId) {
		ModelAndView result;
		final Actor log = this.loginService.getPrincipalActor();
		final Message message = this.messageService.findOne(messageId);
		Assert.notNull(message);
		Assert.isTrue(log.getId() == message.getMessageFolder().getActor().getId());
		result = this.createMoveModelAndView(message);

		return result;
	}

	@RequestMapping(value = "/move", method = RequestMethod.POST, params = "save")
	public ModelAndView move(@Valid final Message message, final BindingResult binding) {
		ModelAndView result;
		Assert.notNull(message);
		final Message mbd = this.messageService.findOne(message.getId());
		Assert.notNull(mbd);
		Assert.isTrue(this.loginService.getPrincipalActor().getId() == mbd.getMessageFolder().getActor().getId());
		if (binding.hasErrors())
			result = this.createMoveModelAndView(message);
		else
			try {
				this.messageService.save(message);
				result = new ModelAndView("redirect:../../message/actor/list.do?messageFolderId=" + message.getMessageFolder().getId());
				// result.addObject("message", "message.commit.ok");
				result.addObject("box", message.getMessageFolder().getName());
			} catch (final Throwable oops) {
				result = this.createMoveModelAndView(message, "message.commit.error");
				// result.addObject("mensaje", message);
				// result.addObject("actors", messageService.findAllActors());
				// result.addObject("priorities", messageService.getPriorities());
				// result.addObject("message", "message.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView save(@RequestParam final int messageId) {
		ModelAndView result = null;
		final Message message = this.messageService.findOne(messageId);
		Assert.notNull(message);
		final MessageFolder folder = message.getMessageFolder();
		Assert.isTrue(this.loginService.getPrincipalActor().getId() == folder.getActor().getId());
		try {
			if (message.getMessageFolder().getName().toLowerCase().equals("papelera"))
				this.messageService.delete(message);
			else {
				final MessageFolder f = this.folderService.getPapelera(this.loginService.getPrincipalActor());
				message.setMessageFolder(f);
				final List<Message> lm = f.getMessages();
				lm.add(message);
				f.setMessages(lm);
				this.folderService.save(f);
				this.messageService.save(message);
			}
			result = new ModelAndView("redirect:../../message/actor/list.do?messageFolderId=" + folder.getId());
			// result.addObject("message", "message.commit.ok");
			//			result.addObject("box", folder.getName());
		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			result = this.createEditModelAndView(message, "message.commit.error");
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Message message) {
		ModelAndView result;

		result = this.createEditModelAndView(message, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Message mensaje, final String message) {
		ModelAndView result;

		result = new ModelAndView("actor/message/edit");
		result.addObject("message", mensaje);
		result.addObject("vistaMensaje", true);
		result.addObject("messageError", message);
		result.addObject("priorities", this.messageService.getPriorities());

		return result;
	}

	protected ModelAndView createMoveModelAndView(final Message message) {
		ModelAndView result;

		result = this.createMoveModelAndView(message, null);

		return result;
	}

	protected ModelAndView createMoveModelAndView(final Message mensaje, final String message) {
		ModelAndView result;

		result = new ModelAndView("actor/message/move");
		result.addObject("message", mensaje);
		result.addObject("vistaMensaje", true);
		result.addObject("messageError", message);
		final List<MessageFolder> folders = mensaje.getMessageFolder().getActor().getMessageFolders();
		folders.remove(mensaje.getMessageFolder());
		result.addObject("folders", folders);

		return result;
	}

}
